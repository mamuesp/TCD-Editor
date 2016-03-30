/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Items;

import Editor.Items.Skins.TcdButtonSkin;
import Editor.Items.Skins.TcdKnobSkin;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.logging.Logger;

@XmlAccessorType(XmlAccessType.NONE)
public class TcdKnob extends TcdControl {

    Logger logger = Logger.getLogger("MainLogger");

    public TcdKnob(Pane parent, String id) {
        super(parent, id);
        type = TcdItemType.KNOB;
        setPrefSize(100, 100);
        TcdKnobSkin mySkin = new TcdKnobSkin(this);
        setSkin(mySkin);
    }

    @Override
    public void refreshSkin() {
        ((TcdKnobSkin) getSkin()).initializeGraphics();
    }

    @Override
    public String getLabelText() {
        return ((TcdKnobSkin) getSkin()).getLabelText();
    }

    @Override
    public void setLabelText(String value) {
        ((TcdKnobSkin) getSkin()).setLabelText(value);
    }

    private TcdKnobSkin getSkin(Skin skin) {
        return (TcdKnobSkin) skin;
    }

}
