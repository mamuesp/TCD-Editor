package Editor.Controller;

import Editor.View.Skin.IControlSkin;
import Editor.Model.TcdControl;
import Editor.Model.TcdInfoCtrl;
import Editor.Controller.DataIO.DataIOController;
import Editor.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author mmspaeth
 */

public class ItemSelection {

    private static boolean isDragged = false;
    private static ItemSelection instance;
    private static List<Node> nodeSelection = new ArrayList<Node>();
    private static ObservableList<Node> selectedNodes = FXCollections.observableList(nodeSelection);

    // Konstruktor ist privat, Klasse darf nicht von außen instanziiert werden.
    private ItemSelection() {
    }

    //
    // Statische Methode 'getInstance()' liefert die einzige Instanz der Klasse zurück.
    // Ist synchronisiert und somit thread-sicher.
    //
    public synchronized static ItemSelection getInstance() {
        if (instance == null) {
            instance = new ItemSelection();
        }
        return instance;
    }

    public void addListener(ListChangeListener<Node> newListener) {
        selectedNodes.addListener(newListener);
    }

    public void removeListener(ListChangeListener<Node> oldListener) {
        selectedNodes.removeListener(oldListener);
    }

    public void addAll(final ObservableList<Node> items) {
        for (Node ctrl : items) {
            if (ctrl instanceof TcdControl) {
                this.add((TcdControl) ctrl);
            }
        }
    }

    public boolean add(TcdControl node) {
        ((IControlSkin) node.getSkin()).addDecoration();
        node.setSelected(true);
        selectedNodes.add(node);
        return false;
    }

    public void remove(TcdControl node) {
        node.setSelected(false);
        ((IControlSkin) node.getSkin()).removeDecoration();
        selectedNodes.remove(node);
    }

    public void deleteAll() {
        while (! selectedNodes.isEmpty()) {
            TcdControl ctrl = (TcdControl) selectedNodes.iterator().next();
            ctrl.setSelected(false);
            ((IControlSkin) ctrl.getSkin()).removeDecoration();
            ctrl.delete();
            remove(ctrl);
        }
    }

    public boolean isSingle() {
        return (selectedNodes.size() == 1);
    }

    public boolean isEmpty() {
        return selectedNodes.isEmpty();
    }

    public boolean isSelected(Node node) {
        return selectedNodes.contains(node);
    }

    public void toggle(TcdControl node) {
        if (isSelected(node)) {
            remove(node);
        } else {
            add(node);
        }
    }

    public void log() {
        System.out.println("Items in model: " + Arrays.asList(selectedNodes.toArray()));
    }

    public void clearSelection() {
        while (! isDragged && ! selectedNodes.isEmpty()) {
            remove((TcdControl) selectedNodes.iterator().next());
        }
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

