/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Items;

import Editor.Items.Skins.TcdButtonSkin;
import Editor.Items.Skins.TcdSwitchSkin;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.logging.Logger;

import static java.awt.SystemColor.control;

@XmlAccessorType(XmlAccessType.NONE)
public class TcdButton extends TcdControl {

    Logger logger = Logger.getLogger("MainLogger");

    public TcdButton(Pane parent, String id) {
        super(parent, id);
        this.setPrefSize(150, 40);
        type = TcdItemType.BUTTON;
        TcdButtonSkin mySkin = new TcdButtonSkin(this);
        setSkin(mySkin);
    }

    @Override
    public void refreshSkin() {
        extractDeco();
        ((TcdButtonSkin) getSkin()).initializeGraphics();
        if (deco != null) {
            getChildren().add(deco);
        }
        restoreDeco();
    }

    @Override
    public String getLabelText() {
        return ((TcdButtonSkin) getSkin()).getLabelText();
    }

    @Override
    public void setLabelText(String value) {
        ((TcdButtonSkin) getSkin()).setLabelText(value);
    }

    private TcdButtonSkin getSkin(Skin skin) {
        return (TcdButtonSkin) skin;
    }

}
