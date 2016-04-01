package Editor.Model;

import Editor.View.Skin.IControlSkin;
import Editor.Controller.ItemSelection;
import Editor.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 20.03.16.
 *
 * @version ${Version}
 */
@XmlRootElement(name = "control")
public class TcdInfoCtrl {

    private String type;
    private Map<String, String> texts;
    private Map<String, String> images;
    private String ctrlClassname;
    private Rectangle2D ctrlBounds;
    private Double value;

    private TcdControl ctrl;

    public TcdInfoCtrl() {
    }

    public TcdInfoCtrl(TcdControl control) {
        this.setControl(control);
    }

    public void setControl(TcdControl value) {
        this.ctrl = value;
        this.refreshValues();
    }

    private void refreshValues() {
        if (this.ctrl != null) {
            this.ctrlClassname = this.ctrl.getClass().getName();
            this.type = this.ctrl.getType().intern();
        }
    }


    public Map<String, String> getTexts() {
        return ((IControlSkin) ctrl.getSkin()).getTexts();
    }

    public void setTexts(Map<String, String> value) {
        ((IControlSkin) ctrl.getSkin()).setTexts(value);
    }

    @XmlElement(name = "ctrl_type")
    public String getType() {
        return ctrl.getType();
    }

    public void setType(String value) {
        this.type = value;
    }

    @XmlElement(name = "ctrl_images")
    public Map<String, String> getImages() {
        Map<String, String> storeList = new HashMap<String, String>();
        Map<String, String> imageList = ((IControlSkin) ctrl.getSkin()).getImages();

        Iterator it = imageList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String imgName = (String) pair.getKey();
            String imgValue = (String) pair.getValue();

            String result = "";
            File fPath = Utils.getControlsFilePath();
            String path = fPath.getPath();
            String dirName = fPath.getPath().replace(".xml", "/images/");
            File test = new File(dirName);
            boolean success = (test.exists() && test.isDirectory()) || test.mkdirs();

            if (success) {
                result = dirName + this.ctrlClassname + "-" + imgName + ".png";
                File imgFile = new File(result);
                try {
                    Image imgSrc = new Image(this.getClass().getClassLoader().getResourceAsStream(imgValue));
                    ImageIO.write(SwingFXUtils.fromFXImage(imgSrc, null), "png", imgFile);
                } catch (Exception s) {
                    result = "Image: " + result + " not saved.";
                }
                storeList.put(imgName, result);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

        return storeList;
    }

    public void setImages(Map<String, String> value) {
        if (this.ctrl != null) {
            ((IControlSkin) ctrl.getSkin()).setImages(value);
        }
        this.images = value;
    }

    @XmlElement(name = "ctrl_classname")
    public String getCtrlClassname() {
        return ctrl.getClass().getName();
    }

    public void setCtrlClassname(String value) {
        this.ctrlClassname = value;
    }

    @XmlElement(name = "ctrl_bounds")
    public String getCtrlBounds() {
        Rectangle2D result = new Rectangle2D(
                (int) Math.round(ctrl.getBoundsInParent().getMinX()),
                (int) Math.round(ctrl.getBoundsInParent().getMinY()),
                (int) Math.round(ctrl.getBoundsInParent().getWidth()),
                (int) Math.round(ctrl.getBoundsInParent().getHeight())
        );
        return result.toString();
    }

    public void setCtrlBounds(String value) {
        double x = 0.0, y = 0.0, w = 0.0, h = 0.0;
        value = value.replace("Rectangle2D ", "").replace("[", "").replace("]", "");
        String[] parts = value.split(",");
        switch (parts.length) {
            case 6:
                x = Double.parseDouble(parts[0].trim().split("=")[1]);
                y = Double.parseDouble(parts[1].trim().split("=")[1]);
                w = Double.parseDouble(parts[4].trim().split("=")[1]);
                h = Double.parseDouble(parts[5].trim().split("=")[1]);
                break;
            case 4:
                x = Double.parseDouble(parts[0].trim().split("=")[1]);
                y = Double.parseDouble(parts[1].trim().split("=")[1]);
                w = Double.parseDouble(parts[2].trim().split("=")[1]);
                h = Double.parseDouble(parts[3].trim().split("=")[1]);
                break;
            default:
                throw new IllegalArgumentException(value + "Class: " + Rectangle2D.class.getName());
        }

        this.ctrlBounds = new Rectangle2D(x, y, w, h);
    }

    @XmlElement(name = "ctrl_value")
    public Double getValue() {
        return ctrl.getValue();
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void generateControl(Pane root) {
        ItemSelection SelectCtrl = ItemSelection.getInstance();
        TcdControl newItem = (TcdControl) SelectCtrl.generateNewItem(this.ctrlClassname, root, true);
        newItem.setValue(this.value);
        newItem.setBounds(this.ctrlBounds);
        ((IControlSkin) newItem.getSkin()).setImages(this.images);
        ((IControlSkin) newItem.getSkin()).setTexts(this.texts);
    }
}
