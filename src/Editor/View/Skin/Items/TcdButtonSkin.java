package Editor.View.Skin.Items;

import Editor.Model.Items.TcdButton;
import Editor.Model.TcdControl;
import Editor.View.Skin.*;
import Editor.View.Skin.TcdProperties;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class TcdButtonSkin extends TcdControlSkin implements IControlSkin {

    public TcdButtonSkin(TcdButton control) {
        super(control);
    }

    @Override
    public ArrayList<TcdPropertyItem> loadDefaults(TcdProperties props) {
        props.add("images", "IconOn", "resources/img/button-on.png", "0", "is a property", "filename");
        props.add("images", "IconOff", "resources/img/button-off.png", "1", "is a property", "filename");

        props.add("colors", "TextColorOn", "#8b7c71", "0", "is a property", "number");
        props.add("colors", "TextColorOff", "#FFFFFF", "1", "is a property", "number");

        props.add("texts", "Label", "A button!", "0", "is a property", "text");

        props.add("sizes", "ItemWidth", "150.0", "0", "is a property", "measure");
        props.add("sizes", "ItemHeight", "40.0", "1", "is a property", "measure");
        props.add("sizes", "TextSize", "20.0", "2", "is a property", "measure");

        return props.getProperties();
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        ImageView ivImg = null;
        Color fontColor = Color.web(props.getValue("colors", (control.getValue() != 0.0) ? "TextColorOn" : "TextColorOff"));
        Text label = new Text(props.getValue("texts", "Label"));
        Font lblFont = new Font("Arial", Double.parseDouble(props.getValue("sizes", "TextSize")));
        Rectangle2D vwRect;

        tcdSkinBase.getChildren().clear();
        try {
            String imgFile = props.getValue("images", (control.getValue() != 0.0) ? "IconOn" : "IconOff");
            Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(imgFile));
            ivImg = new ImageView(img);

            ImageView[] ivColl = { ivImg };
            for (ImageView ivCurr: ivColl) {
                ivCurr.setPreserveRatio(true);
                ivCurr.setSmooth(true);
                ivCurr.setCache(true);
                ivCurr.setPickOnBounds(false);
                ivCurr.setMouseTransparent(true);
            }
            tcdSkinBase.getChildren().addAll(ivColl);
        } catch (Exception ex) {
            ivImg = null;
            tcdSkinBase.getChildren().add(new Text("No Image!"));
        }

        label.setPickOnBounds(false);
        label.prefHeight(Double.parseDouble(props.getValue("sizes", "TextSize")));
        label.setTextOrigin(VPos.CENTER);
        label.setFont(lblFont);
        label.setFill(fontColor);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFontSmoothingType(FontSmoothingType.LCD);
        label.setMouseTransparent(true);

        tcdSkinBase.setCenter(label);
    }
}

