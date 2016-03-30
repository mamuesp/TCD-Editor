package Editor.Items;

import Editor.TcdSelection;
import Editor.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 20.03.16.
 *
 * @version ${Version}
 */
@XmlRootElement(name = "control")
public class TcdInfoCtrl {

    private WritableImage tmpImage;

    private String label;
    private String type;
    private String image;
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
            if (this.tmpImage != null) {
                this.ctrl.setImage(tmpImage);
                tmpImage = null;
            }
        }
    }

    @XmlElement(name = "ctrl_label")
    public String getLabelText() {
        return ctrl.getLabelText();
    }

    public void setLabelText(String value) {
        this.label = value;
    }

    @XmlElement(name = "ctrl_type")
    public String getType() {
        return ctrl.getType();
    }

    public void setType(String value) {
        this.type = value;
    }

    @XmlElement(name = "ctrl_image")
    public String getImage() {
        String result = "";
        File fPath = Utils.getControlsFilePath();
        String path = fPath.getPath();
        String dirName = fPath.getPath().replace(".xml", "/images/");
        File test = new File(dirName);
        boolean success = (test.exists() && test.isDirectory()) || test.mkdirs();

        if (success) {
            result = dirName + this.ctrlClassname + ".png";
            File imgFile = new File(result);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(ctrl.getImage(), null), "png", imgFile);
            } catch (Exception s) {
                result = "Image: " + result + " not saved.";
            }
        }
        return result;
    }

    public void setImage(String value) {
        File imgFile = new File(value);
        if (imgFile.exists()) {
            BufferedImage imgLoaded = SwingFXUtils.fromFXImage(new Image(imgFile.toURI().toString()), null);
            this.tmpImage = SwingFXUtils.toFXImage(imgLoaded, null);
            if (this.ctrl != null) {
                this.ctrl.setImage(tmpImage);
            }
        }
        this.image = value;
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
        TcdSelection SelectCtrl = TcdSelection.getInstance();
        TcdControl newItem = (TcdControl) SelectCtrl.generateNewItem(this.ctrlClassname, root, true);
        newItem.setValue(this.value);
        newItem.setBounds(this.ctrlBounds);
        newItem.setImageAsStr(this.image);
        newItem.setLabelText(this.label);
    }
}
