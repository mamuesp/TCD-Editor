/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Items;

import Editor.Model.TcdItemType;
import Editor.View.Items.Skins.TcdButtonSkin;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.logging.Logger;

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

    private TcdButtonSkin getSkin(Skin skin) {
        return (TcdButtonSkin) skin;
    }

}
