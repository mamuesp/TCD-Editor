package Editor.View.Skin;

import Editor.ResourceLoader;
import Editor.Utils;
import Editor.View.Skin.TcdPropertyItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.*;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 31.03.16.
 *
 * @version ${Version}
 */
@XmlRootElement(name = "properties")
public class TcdProperties {

    private TcdControlSkin skin;
    private String fileName = "";

    protected Map<String, String> colors = new HashMap<>();
    protected Map<String, String> images = new HashMap<>();
    protected Map<String, String> texts = new HashMap<>();
    protected Map<String, String> sizes = new HashMap<>();

    public TcdProperties() {
    }

    public TcdProperties(final TcdControlSkin skin) {
        this.skin = skin;
        this.fileName = "resources/defaults/" + this.skin.getSkinnable().getClass().getName() + "-defaults.xml";
        this.load();
    }

    public void load() {

        try {
            String pathName = ResourceLoader.getResource(this.fileName).getPath();
            JAXBContext context = JAXBContext.newInstance(TcdProperties.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            File propFile = new File(pathName.replaceAll("%20", " ").trim());
            if (Utils.checkExists(propFile)) {
                TcdProperties tmpProp = (TcdProperties) unmarshaller.unmarshal(propFile);
                this.getColors().putAll(tmpProp.getColors());
                this.getImages().putAll(tmpProp.getImages());
                this.getSizes().putAll(tmpProp.getSizes());
                this.getTexts().putAll(tmpProp.getTexts());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void store() {

        try {
            String pathName = this.getClass().getClassLoader().getResource(this.fileName).getPath();
            JAXBContext context = JAXBContext.newInstance(TcdProperties.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File propFile = new File(pathName);
            marshaller.marshal(this, propFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PropertySheet.Item> getItems(String category) {

        ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
        //java.util.List<TcdPropertyItem> items = new ArrayList<TcdPropertyItem>();
        Map<String, String> currProps = null;
        final String catAll[] = { "colors", "images", "texts", "sizes" };
        final String catSingle[] = { category };

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

            Iterator it = currProps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                TcdPropertyItem propItem = new TcdPropertyItem(currCat, pair.getKey().toString(), pair.getValue().toString(), "", skin.getSkinnable());
                items.add(propItem);
                it.remove(); // avoids a ConcurrentModificationException
            }
        }

        return items;
    }

    @XmlElement(name = "prop_colors")
    public Map<String, String> getColors() {
        return colors;
    }

    @XmlElement(name = "prop_images")
    public Map<String, String> getImages() {
        return images;
    }

    @XmlElement(name = "prop_texts")
    public Map<String, String> getTexts() {
        return texts;
    }

    @XmlElement(name = "prop_sizes")
    public Map<String, String> getSizes() {
        return sizes;
    }
}
