/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Items;

import Editor.Model.TcdControl;
import Editor.Model.TcdItemType;
import Editor.View.Skin.Items.TcdFaderSkin;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.logging.Logger;

@XmlAccessorType(XmlAccessType.NONE)
public class TcdFader extends TcdControl {

    Logger logger = Logger.getLogger("MainLogger");

    public TcdFader(Pane parent, String id) {
        super(parent, id);
        type = TcdItemType.KNOB;
        TcdFaderSkin mySkin = new TcdFaderSkin(this);
        setSkin(mySkin);
    }

    @Override
    public void refreshSkin() {
        ((TcdFaderSkin) getSkin()).initializeGraphics();
    }

    private TcdFaderSkin getSkin(Skin skin) {
        return (TcdFaderSkin) skin;
    }

}
