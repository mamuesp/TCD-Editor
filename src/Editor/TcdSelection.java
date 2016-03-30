package Editor;

import Editor.Items.TcdControl;
import Editor.Items.TcdInfoCtrl;
import Editor.Items.TcdItemType;
import Editor.DataIO.DataIOController;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sun.tools.doclets.internal.toolkit.util.DocPath.parent;
import static com.sun.tools.doclint.Entity.Delta;
import static com.sun.tools.doclint.Entity.delta;

/**
 * @author mmspaeth
 */

public class TcdSelection  {

    private static boolean isDragged = false;
    private static TcdSelection instance;
    Set<Node> selectedNodes = new HashSet<>();

    // Konstruktor ist privat, Klasse darf nicht von außen instanziiert werden.
    private TcdSelection() {
    }

    //
    // Statische Methode 'getInstance()' liefert die einzige Instanz der Klasse zurück.
    // Ist synchronisiert und somit thread-sicher.
    //
    public synchronized static TcdSelection getInstance() {
        if (instance == null) {
            instance = new TcdSelection();
        }
        return instance;
    }

    public void addAll(final ObservableList<Node> items) {
        for (Node ctrl : items) {
            if (ctrl instanceof TcdControl) {
                this.add((TcdControl) ctrl);
            }
        }
    }

    public void add(TcdControl node) {
        addDecoration(node);
        node.setSelected(true);
        selectedNodes.add(node);
    }

    public void remove(TcdControl node) {
        node.setSelected(false);
        removeDecoration(node);
        selectedNodes.remove(node);
    }

    public void deleteAll() {
        while (! selectedNodes.isEmpty()) {
            TcdControl ctrl = (TcdControl) selectedNodes.iterator().next();
            ctrl.setSelected(false);
            removeDecoration(ctrl);
            ctrl.delete();
            remove(ctrl);
        }
    }

    public void toggle(TcdControl node) {
        if (isSelected(node)) {
            remove(node);
        } else {
            add(node);
        }
    }

    public boolean isSelected(Node node) {
        return selectedNodes.contains(node);
    }

    public void log() {
        System.out.println("Items in model: " + Arrays.asList(selectedNodes.toArray()));
    }

    public void clearSelection() {
        while (! isDragged && ! selectedNodes.isEmpty()) {
            remove((TcdControl) selectedNodes.iterator().next());
        }
    }

    public boolean isEmpty() {
        return selectedNodes.isEmpty();
    }

    public boolean addDecoration(TcdControl control) {

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

        return control.getSelected();
    }

    public boolean removeDecoration(TcdControl control) {
        String id = "Decoration-" + control.getId();
        ArrayList<Node> removable = new ArrayList<Node>();

        for (Node item : control.getChildren()) {
            if (item != null && item.getId() != null && item.getId().equals(id)) {
                removable.add(item);
            }
        }

        control.getChildren().removeAll(removable);

        return ! removable.isEmpty();
    }

    public TcdControl generateNewItem(TcdInfoCtrl wrapper, Pane parentPane) {
        TcdControl newItem = generateNewItem(wrapper.getCtrlClassname(), parentPane, false);
        wrapper.setControl(newItem);
        return newItem;
    }

    public TcdControl generateNewItem(String className, Pane parentPane, boolean addToIoController) {
        Class<?> clazz = null;
        Constructor<?> constructor = null;
        Object args[] = new Object[2];
        TcdControl instance = null;

        args[0] = parentPane;
        args[1] = Utils.generateId();
        try {
            clazz = Class.forName(className);
            constructor = clazz.getConstructor(Pane.class, String.class);
            instance = (TcdControl) constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((TcdControl) instance).setHandlers(instance, parentPane.getChildren());
        if (addToIoController) {
            DataIOController.getInstance().getControlData().add(new TcdInfoCtrl(((TcdControl) instance)));
        }

        return instance;
    }

    public void moveSelected(Point2D delta) {
        if (!isEmpty()) {
            for (Node ctrl : selectedNodes) {
                if (ctrl instanceof TcdControl) {
                    TcdControl tc = ((TcdControl) ctrl);
                    tc.relocate(tc.getLayoutX() + delta.getX(), tc.getLayoutY() + delta.getY());
                }
            }
        }
    }
}

