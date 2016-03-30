/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Items;

import Editor.Items.Skins.TcdKnobSkin;
import Editor.Items.Skins.TcdSwitchSkin;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.logging.Logger;

@XmlAccessorType(XmlAccessType.NONE)
public class TcdSwitch extends TcdControl {

    Logger logger = Logger.getLogger("MainLogger");

    public TcdSwitch(Pane parent, String id) {
        super(parent, id);
        setPrefSize(100, 80);
        type = TcdItemType.SWITCH;
        TcdSwitchSkin mySkin = new TcdSwitchSkin(this);
        setSkin(mySkin);
    }

    private TcdSwitchSkin getSkin(Skin skin) {
        return (TcdSwitchSkin)skin;
    }

    @Override
    public void refreshSkin() {
        ((TcdSwitchSkin) getSkin()).initializeGraphics();
    }

    @Override
    public String getLabelText() {
        return ((TcdSwitchSkin) getSkin()).getLabelText();
    }

    @Override
    public void setLabelText(String value) {
        ((TcdSwitchSkin) getSkin()).setLabelText(value);
    }

}
