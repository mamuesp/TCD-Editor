package Editor.View.Items.Skins;

import Editor.Model.Items.TcdButton;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TcdButtonSkin extends TcdControlSkin implements IControlSkin {

    public TcdButtonSkin(TcdButton control) {
        super(control);
    }

    public void initDefaults() {
/*
        props.getImages().put("tcdIconOff", "resources/img/button-off.png");
        props.getImages().put("tcdIconOn", "resources/img/button-on.png");
        props.getColors().put("tcdTextColorOff", "#FFFFFF");
        props.getColors().put("tcdTextColorOn", "#8b7c71");
        props.getTexts().put("tcdLabel", "A button!");
        props.getSizes().put("tcdItemWidth", "150.0");
        props.getSizes().put("tcdItemHeight", "40.0");
        props.getSizes().put("tcdTextSize", "20.0");
*/
        super.initDefaults();
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        ImageView ivImg = null;
        Color fontColor = Color.web(props.getColors().get((control.getValue() != 0.0) ? "tcdTextColorOn" : "tcdTextColorOff"));
        Text label = new Text(props.getTexts().get("tcdLabel"));
        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes().get("tcdTextSize")));
        Rectangle2D vwRect;

        tcdSkinBase.getChildren().clear();
        try {
            String imgFile = props.getImages().get((control.getValue() != 0.0) ? "tcdIconOn" : "tcdIconOff");
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
        label.prefHeight(Double.parseDouble(props.getSizes().get("tcdTextSize")));
        label.setTextOrigin(VPos.CENTER);
        label.setFont(lblFont);
        label.setFill(fontColor);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFontSmoothingType(FontSmoothingType.LCD);
        label.setMouseTransparent(true);

        tcdSkinBase.setCenter(label);
    }
}

