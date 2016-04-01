package Editor.View.Items.Skins;

import Editor.Model.Items.TcdControl;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Map;

public class TcdControlSkin extends SkinBase<TcdControl> implements IControlSkin {

    TcdProperties props = null;
    protected TcdControl control = null;

    protected BorderPane tcdSkinBase = new BorderPane();
    protected double value = 0;

    public TcdControlSkin(final TcdControl control) {
        super(control);
        this.props = new TcdProperties(this);
        this.control = control;
        initDefaults();
        initializeGraphics();
    }

    public void initDefaults() {
        //props.store();
    }

    public void initializeGraphics() {

        // draw Button area
        //control = (TcdControl) getSkinnable();
        getChildren().clear();
        getChildren().add(tcdSkinBase);

        double w = Double.parseDouble(props.getSizes().get("tcdItemWidth"));
        double h = Double.parseDouble(props.getSizes().get("tcdItemHeight"));

        control.setPrefSize(w, h);
    }

    public void addDecoration() {

        double w = control.getSelRect().getWidth();
        double h = control.getSelRect().getHeight();
        int lineW = 1;
        int sqrS = 9;
        int sqrSh = 5;

        ObservableList<Node> items = control.getContainer().getChildren();
        // selection frame
        BorderPane selFrame = new BorderPane();
        selFrame.setMouseTransparent(true);
        selFrame.setId("Decoration-" + control.getId());
        selFrame.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(lineW))));
        control.setHandlers(selFrame, items);
        control.getChildren().add(selFrame);

        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

        rects.add(new Rectangle(0.5 - sqrSh + lineW, 0.5 - sqrSh + lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 + w - sqrSh, 0.5 - sqrSh + lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 + w / 2 - sqrSh, 0.5 - sqrSh + lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 + w / 2 - sqrSh, 0.5 + h - sqrSh - lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 - sqrSh + lineW, 0.5 + h / 2 - sqrSh - lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 + w - sqrSh, 0.5 + h / 2 - sqrSh - lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 - sqrSh + lineW, 0.5 + h - sqrSh - lineW, sqrS, sqrS));
        rects.add(new Rectangle(0.5 + w - sqrSh, 0.5 + h - sqrSh - lineW, sqrS, sqrS));

        for (Rectangle rect : rects) {
            rect.setStroke(Color.BLACK);
            rect.setSmooth(false);
            rect.setOpacity(0.5);
            rect.setStrokeWidth(lineW);
            rect.setFill(Color.TRANSPARENT);
            selFrame.getChildren().add(rect);
            rect.setMouseTransparent(true);
            control.setHandlers(rect, items);
        }
    }

    public void removeDecoration() {
        String id = "Decoration-" + control.getId();
        ArrayList<Node> removable = new ArrayList<Node>();

        for (Node item : control.getChildren()) {
            if (item != null && item.getId() != null && item.getId().equals(id)) {
                removable.add(item);
            }
        }

        control.getChildren().removeAll(removable);
    }

    @Override
    public TcdProperties getProperties() {
        return props;
    }

    @Override
    public Map<String, String> getColors() {
        return this.props.getColors();
    }

    @Override
    public void setColors(Map<String, String> colors) {
        this.props.getColors().clear();
        this.props.getColors().putAll(colors);
    }

    @Override
    public Map<String, String> getImages() {
        return this.props.getImages();
    }

    @Override
    public void setImages(Map<String, String> images) {
        this.props.getImages().clear();
        this.props.getImages().putAll(images);
    }

    @Override
    public Map<String, String> getTexts() {
        return this.props.getTexts();
    }

    @Override
    public void setTexts(Map<String, String> texts) {
        this.props.getTexts().clear();
        this.props.getTexts().putAll(texts);
    }

    @Override
    public Map<String, String> getSizes() {
        return this.props.getSizes();
    }

    @Override
    public void setSizes(Map<String, String> sizes) {
        this.props.getSizes().clear();
        this.props.getSizes().putAll(sizes);
    }

}

