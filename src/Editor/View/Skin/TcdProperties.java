package Editor.View.Skin;

import Editor.Controller.DataIO.DataIOController;
import Editor.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.*;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.util.*;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;

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

        // add some specific properties ...

        String id = this.skin.getSkinnable().getInnerID();
        this.add("info", "Control ID", id, "0", "is a property", "system");

        String currCat = "";
        HashSet<String> categories = new HashSet<String>();
        for (TcdPropertyItem item : this.properties) {
            categories.add(item.getCategory());
        }
        for (String cat : categories) {
            this.add(cat, "", WordUtils.capitalize(cat), "-1", "", "group");
        }

        // Sorting
        Collections.sort(this.properties, new Comparator<TcdPropertyItem>() {
            @Override
            public int compare(TcdPropertyItem item1, TcdPropertyItem item2) {
                long comp = item1.getCategory().compareTo(item2.getCategory());
                if (comp == 0) {
                    comp = Long.parseLong(item1.getIdx()) - Long.parseLong(item2.getIdx());
                }
                return (int) comp;
            }
        });
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

    public TableView<TcdPropertyItem> getEditor(TableView<TcdPropertyItem> tableIn) {

        TableView<TcdPropertyItem> table = (tableIn == null) ? new TableView<TcdPropertyItem>() : tableIn;

        table.setEditable(true);
        final ObservableList<TcdPropertyItem> data = FXCollections.observableArrayList(getItems());
        table.setItems(data);
        table.getColumns().clear();

        String[] tags = {"Category", "Name", "Value", "IDX", "Description", "Type"};
        List<String> show = Arrays.asList("Name", "Value");

        for (String currTag : tags) {
            TableColumn<TcdPropertyItem, String> newCol = new TableColumn<TcdPropertyItem, String>(currTag);
            switch (currTag) {
                case "Name":
                    newCol.prefWidthProperty().bind(table.widthProperty().multiply(26.0/100.0));
                    break;
                case "Value":
                    newCol.prefWidthProperty().bind(table.widthProperty().multiply(70.0/100.0));
                    break;
            }

            newCol.setCellFactory(new Callback<TableColumn<TcdPropertyItem, String>, TableCell<TcdPropertyItem, String>>() {
                @Override
                public TableCell<TcdPropertyItem, String> call(TableColumn<TcdPropertyItem, String> param) {
                    return new TableCell<TcdPropertyItem, String>() {
                        @Override
                        protected void updateItem(String celltext, boolean empty) {
                            super.updateItem(celltext, empty);
                            int currentIndex = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();
                            this.getStyleClass().clear();
                            if (!empty) {
                                String type = param.getTableView().getItems().get(currentIndex).getType();
                                if (type.equals("group")) {
                                    this.getStyleClass().add("cell-group-head");
                                } else {
                                    String cat = param.getTableView().getItems().get(currentIndex).getCategory();
                                    if (param.getText().toLowerCase().equals("value")) {
                                        switch (cat) {
                                            case "images":
                                                this.getStyleClass().add("cell-image-addr");
                                                break;
                                            case "colors":
                                                this.getStyleClass().add("cell-color-val");
                                                this.setStyle("-fx-background-color: " + celltext + ";");
                                                break;
                                            default:
                                        }
                                        this.getStyleClass().add("cell-color-val");
                                    }
                                }

                                Text text = new Text(celltext);
                                //setGraphic(text);
                                this.setWrapText(true);
                                setPrefHeight(Control.USE_COMPUTED_SIZE);
//                                setPrefHeight(text.getBoundsInLocal().getHeight());
                                text.wrappingWidthProperty().bind(newCol.widthProperty());
                                text.textProperty().bind(this.itemProperty());
                                setGraphic(text);

//                                setText(celltext);
                            }
                        }
                    };
                }
            });
            newCol.setCellValueFactory(new PropertyValueFactory<>(currTag.toLowerCase()));
            newCol.getStyleClass().add("col-" + currTag.toLowerCase());
            newCol.setVisible(show.contains(currTag));
            table.getColumns().add(newCol);
        }

        return table;
    }
}
