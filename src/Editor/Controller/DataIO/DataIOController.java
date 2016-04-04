package Editor.Controller.DataIO;

import Editor.Model.TcdInfoCtrl;
import Editor.Utils;
import Editor.View.Skin.TcdPropertyItem;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 28.03.16.
 *
 * @version ${Version}
 */
public class DataIOController {

    private Pane root;
    private static DataIOController instance;
    private List<TcdInfoCtrl> controlData = FXCollections.observableArrayList();
    private List<TcdPropertyItem> propertyData = FXCollections.observableArrayList();

    // Konstruktor ist privat, Klasse darf nicht von außen instanziiert werden.
    private DataIOController() {
    }

    //
    // Statische Methode 'getInstance()' liefert die einzige Instanz der Klasse zurück.
    // Ist synchronisiert und somit thread-sicher.
    //
    public synchronized static DataIOController getInstance() {
        if (instance == null) {
            instance = new DataIOController();
        }
        return instance;
    }

    public static Pane getRoot() {
        return DataIOController.getInstance().root;
    }

    public static void setRoot(Pane root) {
        DataIOController.getInstance().root = root;
    }

    public void loadControlDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ItemListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            controlData.clear();
            root.getChildren().clear();

            // Reading XML from the file and unmarshalling.
            ItemListWrapper wrapper = (ItemListWrapper) um.unmarshal(file);
            for (TcdInfoCtrl info : wrapper.getControls()) {
                try {
                    info.generateControl(this.root);
                } catch (Exception e) { // catches ANY exception
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not initialize control");
                    alert.setContentText("Could not initialize control!\n\nException: " + e.getCause() + "\n\n" + e.getMessage() + "\n\n" + e.getStackTrace());

                    alert.showAndWait();
                }
            }

            controlData.addAll(wrapper.getControls());

            // Save the file path to the registry.
            Utils.setControlsFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath() + "\n\nException: " + e.getCause() + "\n\n" + e.getMessage() + "\n\n" + e.getStackTrace());

            alert.showAndWait();
        }
    }

    public void saveControlDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ItemListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            ItemListWrapper wrapper = new ItemListWrapper();
            wrapper.setControls(controlData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            Utils.setControlsFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath() + "\n\nException: " + e.getCause() + "\n\n" + e.getMessage() + "\n\n" + e.getStackTrace());

            alert.showAndWait();
        }
    }

    public boolean loadPropertyDataFromFile(File file, List<TcdPropertyItem> props)     {
        try {
            JAXBContext context = JAXBContext.newInstance(PropListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            props.clear();

            // Reading XML from the file and unmarshalling.
            PropListWrapper wrapper = (PropListWrapper) um.unmarshal(file);
            List<TcdPropertyItem> newProps = wrapper.getProperties();
            if (newProps != null) {
                props.addAll(newProps);
                return true;
            }

            return false;

        } catch (Exception e) { // catches ANY exception
/*
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load properties");
            alert.setContentText("Could not load properties from file:\n" + file.getPath() + "\n\nException: " + e.getCause() + "\n\n" + e.getMessage() + "\n\n" + e.getStackTrace());

            alert.showAndWait();
*/
            e.printStackTrace();
            return false;
        }
    }

    public void savePropertyDataToFile(File file, List<TcdPropertyItem> props) {
        try {
            JAXBContext context = JAXBContext.newInstance(PropListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our data.
            PropListWrapper wrapper = new PropListWrapper();
            wrapper.setProperties(props);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
/*
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save properties");
            alert.setContentText("Could not save properties to file:\n" + file.getPath() + "\n\nException: " + e.getCause() + "\n\n" + e.getMessage() + "\n\n" + e.getStackTrace());

            alert.showAndWait();
*/
        }
    }

    public List<TcdInfoCtrl> getControlData() {
        return controlData;
    }
}
