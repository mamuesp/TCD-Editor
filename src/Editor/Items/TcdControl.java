package Editor.Items;

import Editor.TcdSelection;
import Editor.Utils;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.xml.bind.annotation.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import static java.lang.Math.abs;

public abstract class TcdControl extends Control {

    protected Pane container = null;
    protected WritableImage image = null;
    protected String imageName;
    protected double value = 0f;
    protected TcdItemType type = TcdItemType.NONE;

    protected Boolean selected = false;
    protected Boolean dirty = false;
    protected Boolean willSelect = false;
    protected Rectangle2D tcdSelRect = null;
    protected Node deco = null;

    public TcdControl() {
        super();
    }

    public TcdControl(Pane parent, String id) {

        this.type = TcdItemType.NONE;
        this.setPrefSize(200, 40);
        //this.setSelRect(new Rectangle2D(0, 0, 200, 40));
        this.container = parent;

        int newX = (int) 0;
        int newY = (int) 0;
        this.relocate(newX, newY);
        this.toFront();

        if (id != null) {
            this.setId(id);
        }

        if (parent != null) {
            parent.getChildren().add(this);
        }

        this.setCache(true);
    }

    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    public void move(MouseEvent event, Point2D delta) {
        this.move(this.getLayoutX() + delta.getX(), this.getLayoutY() + delta.getY());
    }

    public void move(DragEvent event) {
        this.move(event.getSceneX(), event.getSceneY());
    }

    public void move(Double x, Double y) {
        Point2D localPoint = container.sceneToLocal(new Point2D(x, y));
        int newX = (int) (localPoint.getX() - this.getBoundsInLocal().getWidth() / 2);
        int newY = (int) (localPoint.getY() - this.getBoundsInLocal().getHeight() / 2);
        this.relocate(newX, newY);
    }

    public Point2D calcDelta(DragEvent event) {
        Double x = event.getSceneX();
        Double y = event.getSceneY();
        Point2D localPoint = container.sceneToLocal(new Point2D(x, y));
        int newX = (int) (localPoint.getX() - this.getBoundsInLocal().getWidth() / 2);
        int newY = (int) (localPoint.getY() - this.getBoundsInLocal().getHeight() / 2);
        return new Point2D(newX - getLayoutX(), newY - getLayoutY());
    }

    public void move(Point2D delta) {
        int newX = (int) (delta.getX());
        int newY = (int) (delta.getY());
        this.relocate(newX, newY);
    }

    public abstract void refreshSkin();

    public abstract String getLabelText();

    public abstract void setLabelText(String value);

    public String getImageAsStr() {
        /*
        BufferedImage img = SwingFXUtils.fromFXImage(getImage(), null);
        String data = Utils.compressAndEncode(img, "png");
        */
        return this.imageName;
    }

    public void setImageAsStr(String value) {
        /*
            BufferedImage imgDec = Utils.decompressAndDecode(value);
            this.image = SwingFXUtils.toFXImage(imgDec, null);
        */
        this.imageName = value;
    }

    public WritableImage getImage() {
        if (image == null || dirty) {
            boolean visible = this.isVisible();
            this.setVisible(true);
            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            this.setImage(this.snapshot(sp, null));
            this.setVisible(visible);
            dirty = false;
        }
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
        dirty = false;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean value) {
        if (this.selected != value) {
            this.selected = value;
            dirty = true;
        }
    }

    public void setBounds(Rectangle2D value) {
        setLayoutX(value.getMinX());
        setLayoutY(value.getMinY());
        setMaxSize(value.getWidth(), value.getHeight());
    }

    public Pane getContainer() {
        return container;
    }

    public void setContainer(Pane container) {
        this.container = container;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        refreshSkin();
    }

    public String getType() {
        return type.name();
    }

    public void setType(String value) {
        this.type = TcdItemType.valueOf(value);
    }

    public Rectangle2D getSelRect() {
        Bounds value = getLayoutBounds();
        tcdSelRect = new Rectangle2D(value.getMinX(), value.getMinY(), value.getWidth(), value.getHeight());
        return tcdSelRect;
    }

    public void setHandlers(final Node child, final ObservableList<Node> items) {

        TcdControl control = this;

        container.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().name() == "PRIMARY") {
                TcdSelection.getInstance().clearSelection();
            }
            control.setCursor(Cursor.DEFAULT);
            logActualEvent("clicked ... ", mouseEvent);
            mouseEvent.consume();
        });

        child.setOnDragDetected(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                logActualEvent("drag detected ... ", mouseEvent);
                Node source = (Node) mouseEvent.getSource();
                if (source instanceof TcdControl) {
                    TcdControl srcItem = (TcdControl) source;
                    srcItem.willSelect = false;
                    if (TcdSelection.getInstance().isSelected(srcItem)) {
                        Dragboard db = srcItem.startDragAndDrop(TransferMode.MOVE);
                        logActualEvent("ID: " + srcItem.getId() + " ... ", mouseEvent);
                        db.setDragView(srcItem.getImage());
                        srcItem.setVisible(false);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(srcItem.getId());
                        db.setContent(content);
                    }
                }
            }
            mouseEvent.consume();
        });

        child.setOnMousePressed(mouseEvent -> {
            switch (mouseEvent.getButton().name()) {
                case "PRIMARY":
                    willSelect = true;
                    break;
                case "SECONDARY":
                    if (getType() == "BUTTON") {
                        setValue(1);
                    }
                    if (getType() == "SWITCH") {
                        setValue(abs(getValue() - 1.0));
                    }
                    break;
            }
            child.setCursor(Cursor.OPEN_HAND);
            logActualEvent("pressed ... ", mouseEvent);
            mouseEvent.consume();
        });

        child.setOnMouseReleased(mouseEvent -> {
            switch (mouseEvent.getButton().name()) {
                case "PRIMARY":
                    if (willSelect) {
                        if (mouseEvent.isShiftDown()) {
                            TcdSelection.getInstance().toggle(control);
                        } else {
                            TcdSelection.getInstance().clearSelection();
                            TcdSelection.getInstance().add(control);
                        }
                        willSelect = false;
                    }
                    break;
                case "SECONDARY":
                    if (getType() == "BUTTON") {
                        setValue(0);
                    }
                    break;
            }
            child.setCursor(javafx.scene.Cursor.HAND);
            logActualEvent("released ... ", mouseEvent);
            mouseEvent.consume();
        });

        child.setOnMouseEntered(mouseEvent -> {
            child.setCursor(javafx.scene.Cursor.HAND);
            logActualEvent("entered", mouseEvent);
            mouseEvent.consume();
        });
    }

    public void extractDeco() {
        this.deco = (Node) lookup("#Decoration-" + getId());
        if (this.deco != null) {
            getChildren().remove(this.deco);
        }
    }

    public void restoreDeco() {
        if (this.deco != null) {
            getChildren().add(this.deco);
            this.deco = null;
        }
    }

    private void logActualEvent(String msg, Event event) {
        Logger logger = Logger.getLogger("MainLogger");
        logger.setLevel(Level.ALL);
        logger.log(Level.INFO, "Mouse " + msg + " ... Item type: " + event.getSource().getClass().getName());
    }

    public void delete() {
        container.getChildren().remove(this);
    }
}
