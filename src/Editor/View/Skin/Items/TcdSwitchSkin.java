package Editor.View.Skin.Items;

import Editor.Model.Items.TcdSwitch;
import Editor.View.Skin.IControlSkin;
import Editor.View.Skin.TcdControlSkin;
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

    public void initDefaults() {
        ArrayList<String> tmpList = new ArrayList<String>();

        tmpList.add(IMAGES.tcdIconOn.ordinal(), "resources/img/switch-on.png");
        tmpList.add(IMAGES.tcdIconOff.ordinal(), "resources/img/switch-off.png");
        props.setImages(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(COLORS.tcdTextColorOn.ordinal(), "#8b7c71");
        tmpList.add(COLORS.tcdTextColorOff.ordinal(), "#8b7c71");
        props.setColors(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TEXTS.tcdLabel.ordinal(), "A switch!");
        props.setTexts(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(SIZES.tcdItemWidth.ordinal(), "100.0");
        tmpList.add(SIZES.tcdItemHeight.ordinal(), "80.0");
        tmpList.add(SIZES.tcdTextSize.ordinal(), "20.0");
        props.setSizes(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        super.initDefaults();
    }

    public void initializeGraphics() {

        // draw switch area

        TcdSwitch control = (TcdSwitch) getSkinnable();
        getChildren().clear();
        getChildren().add(tcdSkinBase);

        double w = Double.parseDouble(props.getSizes(SIZES.tcdItemWidth.ordinal()));
        double h = Double.parseDouble(props.getSizes(SIZES.tcdItemHeight.ordinal()));
        control.setPrefSize(w, h);

        ImageView ivImg = null;
        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes(SIZES.tcdTextSize.ordinal())));
        Color fontColor = Color.web(props.getColors((control.getValue() != 0.0) ? COLORS.tcdTextColorOn.ordinal() : COLORS.tcdTextColorOff.ordinal()));
        Rectangle2D vwRect;
        try {
            String imgFile = props.getImages((control.getValue() != 0.0) ? IMAGES.tcdIconOn.ordinal() : IMAGES.tcdIconOff.ordinal());
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

        Label label = new Label(props.getTexts(TEXTS.tcdLabel.ordinal()));
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

