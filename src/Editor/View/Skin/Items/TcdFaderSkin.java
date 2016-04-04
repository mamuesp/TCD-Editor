package Editor.View.Skin.Items;

import Editor.Model.Items.TcdFader;
import Editor.View.Skin.*;
import Editor.View.Skin.TcdProperties;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class TcdFaderSkin extends TcdControlSkin implements IControlSkin {

    public TcdFaderSkin(TcdFader control) {
        super(control);
    }

    public ArrayList<TcdPropertyItem> loadDefaults(TcdProperties props) {
        props.add("images", "IconOn", "resources/img/knob.png", "0", "is a property", "filename");

        props.add("colors", "TextColorOn", "#8b7c71", "0", "is a property", "number");
        props.add("colors", "TextColorOff", "#8b7c71", "1", "is a property", "number");

        props.add("texts", "Label", "A 270° knob!", "0", "is a property", "text");

        props.add("sizes", "ItemWidth", "100.0", "0", "is a property", "measure");
        props.add("sizes", "ItemHeight", "100.0", "1", "is a property", "measure");
        props.add("sizes", "TextSize", "20.0", "2", "is a property", "measure");

        return props.getProperties();
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        // draw switch area

        if (props != null) {
            ImageView ivImg = null;
            Color fontColor = Color.web(props.getValue("colors", "TextColorOn"));

            Font lblFont = new Font("Arial", Double.parseDouble(props.getValue("sizes", "TextSize")));
            Rectangle2D vwRect;
            try {
                String imgFile = props.getValue("images", "IconOn");
                Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(imgFile));
                ivImg = new ImageView(img);

                ImageView[] ivColl = {ivImg};
                for (ImageView ivCurr : ivColl) {
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

