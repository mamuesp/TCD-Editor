package Editor.View.Skin.Items;

import Editor.Model.Items.TcdSwitch;
import Editor.View.Skin.*;
import Editor.View.Skin.TcdPropertyItem;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class TcdSwitchSkin extends TcdControlSkin implements IControlSkin {

    public TcdSwitchSkin(TcdSwitch control) {
        super(control);
    }

    @Override
    public ArrayList<TcdPropertyItem> loadDefaults(TcdProperties props) {
        props.add("images", "IconOn", "resources/img/switch-on.png", "0", "is a property", "filename");
        props.add("images", "IconOff", "resources/img/switch-off.png", "1", "is a property", "filename");

        props.add("colors", "TextColorOn", "#8b7c71", "0", "is a property", "number");
        props.add("colors", "TextColorOff", "#8b7c71", "1", "is a property", "number");

        props.add("texts", "Label", "A switch!", "0", "is a property", "text");

        props.add("sizes", "ItemWidth", "100.0", "0", "is a property", "measure");
        props.add("sizes", "ItemHeight", "80.0", "1", "is a property", "measure");
        props.add("sizes", "TextSize", "20.0", "2", "is a property", "measure");

        return props.getProperties();
    }

    public void initializeGraphics() {

        // draw switch area
        super.initializeGraphics();

        if (props != null) {
            ImageView ivImg = null;
            Font lblFont = new Font("Arial", Double.parseDouble(props.getValue("sizes", "TextSize")));
            Color fontColor = Color.web(props.getValue("colors", ((control.getValue() != 0.0) ? "TextColorOn" : "TextColorOff")));
            Rectangle2D vwRect;
            try {
                String imgFile = props.getValue("images", ((control.getValue() != 0.0) ? "IconOn" : "IconOff"));
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
            } catch (Exception ex) {
                ivImg = null;
            }

            Label label = new Label(props.getValue("texts", "Label"));
            label.setGraphic(ivImg);
            label.setMaxWidth(Double.POSITIVE_INFINITY);
            label.setMaxHeight(Double.POSITIVE_INFINITY);
            //label.setStyle("-fx-border-color: blue;");
            label.setContentDisplay(ContentDisplay.TOP);
            label.setPickOnBounds(false);
            label.setFont(lblFont);
            label.setTextFill(fontColor);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setMouseTransparent(true);

            tcdSkinBase.getChildren().clear();
            tcdSkinBase.setCenter(label);
        }
    }
}

