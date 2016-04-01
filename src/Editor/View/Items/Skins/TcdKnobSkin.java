package Editor.View.Items.Skins;

import Editor.Model.Items.TcdKnob;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TcdKnobSkin extends TcdControlSkin implements IControlSkin {

    public TcdKnobSkin(TcdKnob control) {
        super(control);
    }

    public void initDefaults() {
/*
        props.getImages().put("tcdIconOn", "resources/img/knob.png");
        props.getColors().put("tcdTextColorOn", "#8b7c71");
        props.getTexts().put("tcdLabel", "A 270° knob!");
        props.getSizes().put("tcdItemWidth", "100.0");
        props.getSizes().put("tcdItemHeight", "100.0");
        props.getSizes().put("tcdTextSize", "20.0");

        super.initDefaults();
*/
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        // draw switch area

        ImageView ivImg = null;
        Color fontColor = Color.web(props.getColors().get("tcdTextColorOn"));

        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes().get("tcdTextSize")));
        Rectangle2D vwRect;
        try {
            String imgFile = props.getImages().get("tcdIconOn");
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

        Label label = new Label(props.getTexts().get("tcdLabel"));
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

