package Editor.View.Skin;

import Editor.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 31.03.16.
 *
 * @version ${Version}
 */
@XmlRootElement(name = "properties")
public class TcdPropertiesBean implements java.io.Serializable {

    private String defaultFile = "";
    private TcdControlSkin skin;
    private String fileName = "";

    private String[] colors = new String[1];
    private String[] images = new String[1];
    private String[] texts = new String[1];
    private String[] sizes = new String[1];

    public TcdPropertiesBean() {
        defaultFile = this.getClass().getClassLoader().getResource(this.fileName).getPath().replaceAll("%20", " ").trim();
    }

    public TcdPropertiesBean(final TcdControlSkin skin) {
        this.skin = skin;
        String[] parts = this.skin.getSkinnable().getClass().getName().split("\\.");
        String name = parts[parts.length - 1];
        this.fileName = "resources/defaults/" + name + "-defaults.xml";
        try {
            defaultFile = this.getClass().getClassLoader().getResource(this.fileName).getPath().replaceAll("%20", " ").trim();
        } catch (Exception ex) {
            defaultFile = this.fileName;
        }

        this.load();
        //this.store();
    }

    public void load() {
        try {
            JAXBContext context = JAXBContext.newInstance(TcdPropertiesBean.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File propFile = new File(defaultFile);
            if (Utils.checkExists(propFile)) {
                TcdPropertiesBean tmpProp = (TcdPropertiesBean) unmarshaller.unmarshal(propFile);
                this.setColors(tmpProp.getColors());
                this.setImages(tmpProp.getImages());
                this.setSizes(tmpProp.getSizes());
                this.setTexts(tmpProp.getTexts());
            } else {
                // generate a ne defaults file
                this.skin.loadDefaults(this);
                this.store(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void store() {
        store(false);
    }

    public void store(boolean force) {

        try {
            File propFile = new File(defaultFile);

            // save only if no file is there or force write is true
            if (force || !Utils.checkExists(propFile)) {
                JAXBContext context = JAXBContext.newInstance(TcdPropertiesBean.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                marshaller.marshal(this, propFile);
            }
        } catch (Exception e) {
            this.skin.loadDefaults(this);
            e.printStackTrace();
        }
    }

    public ObservableList<PropertySheet.Item> getItems(String category) {

        ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
        //java.util.List<TcdPropertyItem> items = new ArrayList<TcdPropertyItem>();
        String[] currProps = null;
        final String catAll[] = {"colors", "images", "texts", "sizes"};
        final String catSingle[] = {category};

        for (String currCat : (category == "all") ? catAll : catSingle) {
            switch (currCat) {
                case "colors":
                    currProps = colors;
                    break;
                case "images":
                    currProps = images;
                    break;
                case "texts":
                    currProps = texts;
                    break;
                case "sizes":
                    currProps = sizes;
                    break;
                default:
                    return items; // empty list
            }

            //    Iterator it = currProps.entrySet().iterator();
            //    while (it.hasNext()) {
            int index = 0;
            for (String value : currProps) {
                //Map.Entry pair = (Map.Entry) it.next();
                TcdPropertyItem propItem = new TcdPropertyItem(currCat, "idx-" + index, value, "Standard", skin.getSkinnable());
                items.add(propItem);
                //it.remove(); // avoids a ConcurrentModificationException
                index++;
            }
        }

        return items;
    }

    //Methods to access the entire indexed property array

    @XmlElement(name = "prop_colors")
    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] value) {
        colors = value;
    }

    @XmlElement(name = "prop_images")
    public String[] getImages() {
        return images;
    }

    public void setImages(String[] value) {
        images = value;
    }

    @XmlElement(name = "prop_texts")
    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] value) {
        texts = value;
    }

    @XmlElement(name = "prop_sizes")
    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] value) {
        sizes = value;
    }

    //Methods to access individual values

    public String getColors(int index) {
        return colors[index];
    }

    public void setColors(int index, String value) {
        colors[index] = value;
    }

    public String getImages(int index) {
        return images[index];
    }

    public void setImages(int index, String value) {
        images[index] = value;
    }

    public String getTexts(int index) {
        return texts[index];
    }

    public void setTexts(int index, String value) {
        texts[index] = value;
    }

    public String getSizes(int index) {
        return sizes[index];
    }

    public void setSizes(int index, String value) {
        sizes[index] = value;
    }

}
