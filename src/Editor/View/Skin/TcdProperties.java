package Editor.View.Skin;

import Editor.Controller.DataIO.DataIOController;
import Editor.Model.TcdControl;
import Editor.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.ArrayList;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 31.03.16.
 *
 * @version ${Version}
 */
public class TcdProperties {

    private String defaultFile = "";
    private TcdControlSkin skin;
    private String fileName = "";

    private ArrayList<TcdPropertyItem> properties = new ArrayList();

    public TcdProperties() {
        defaultFile = this.getClass().getClassLoader().getResource(this.fileName).getPath().replaceAll("%20", " ").trim();
    }

    public TcdProperties(final TcdControlSkin skin) {
        this.skin = skin;

        String[] parts = this.skin.getSkinnable().getClass().getName().split("\\.");
        String name = parts[parts.length - 1];
        this.fileName = "resources/defaults/" + name + "-defaults.xml";
        try {
            defaultFile = this.getClass().getClassLoader().getResource(this.fileName).getPath().replaceAll("%20", " ").trim();
        } catch (Exception ex) {
            defaultFile = this.fileName;
        }

        File defRead = new File(defaultFile);
        File defWrite = new File(defaultFile);
        try {
            if (!Utils.checkExists(defRead)) {
                defRead.getParentFile().mkdirs();
                defRead.createNewFile();
            }
        } catch (Exception ex) {
            return;
        }
        this.skin.loadDefaults(this);

        DataIOController dataIO = DataIOController.getInstance();
        if (!dataIO.loadPropertyDataFromFile(defRead, properties)) {
            this.skin.loadDefaults(this);
            dataIO.getInstance().savePropertyDataToFile(defWrite, properties);
        }
    }

    public void add(String category, String name, String value, String index, String description, String type) {
        properties.add(new TcdPropertyItem(category, name, value, index, description, type));
    }

    public String getValue(String category, String name) {
        for (TcdPropertyItem curr : properties) {
            if (category.equals(curr.getCategory()) && name.equals(curr.getName())) {
                return (String) curr.getValue();
            }
        }
        return "";
    }

    public ArrayList<TcdPropertyItem> getProperties() {
        return properties;
    }

    public void addAll(ArrayList<TcdPropertyItem> props) {
        this.properties.clear();
        if (props != null) {
            this.properties.addAll(props);
        }
    }

    public ObservableList<TcdPropertyItem> getItems() {
        ObservableList<TcdPropertyItem> items = FXCollections.observableArrayList();
        items.addAll(this.properties);
        return items;
    }

    public TableView getEditor() {

        TableView<TcdPropertyItem> table = new TableView();

        table.setEditable(true);

        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<TcdPropertyItem, String>("category"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<TcdPropertyItem, String>("name"));

        TableColumn valueCol = new TableColumn("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<TcdPropertyItem, String>("value"));

        TableColumn idxCol = new TableColumn("IDX");
        idxCol.setCellValueFactory(new PropertyValueFactory<TcdPropertyItem, String>("idx"));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<TcdPropertyItem, String>("description"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<TcdPropertyItem, String>("type"));

        idxCol.setVisible(false);
        descriptionCol.setVisible(false);
        typeCol.setVisible(false);

        table.getColumns().addAll(nameCol, valueCol, typeCol, idxCol, descriptionCol, typeCol);

        return table;
    }
}
