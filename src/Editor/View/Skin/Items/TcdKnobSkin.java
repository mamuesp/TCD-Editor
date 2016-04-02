package Editor.View.Skin.Items;

import Editor.Model.Items.TcdFader;
import Editor.View.Skin.IControlSkin;
import Editor.View.Skin.TcdControlSkin;
import Editor.View.Skin.TcdPropertiesBean;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class TcdKnobSkin extends TcdControlSkin implements IControlSkin {

    public TcdKnobSkin(TcdFader control) {
        super(control);
    }

    @Override
    public void loadDefaults(TcdPropertiesBean props) {
        this.props = (this.props == null) ? props : this.props;
        ArrayList<String> tmpList = new ArrayList<String>();

        tmpList.add(IMAGES.tcdIconOn.ordinal(), "resources/img/knob.png");
        props.setImages(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(COLORS.tcdTextColorOn.ordinal(), "#8b7c71");
        props.setColors(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TEXTS.tcdLabel.ordinal(), "A 270° knob!");
        props.setTexts(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(SIZES.tcdItemWidth.ordinal(), "100.0");
        tmpList.add(SIZES.tcdItemHeight.ordinal(), "100.0");
        tmpList.add(SIZES.tcdTextSize.ordinal(), "20.0");
        props.setSizes(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        // draw switch area

        ImageView ivImg = null;
        Color fontColor = Color.web(props.getColors(COLORS.tcdTextColorOn.ordinal()));

        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes(SIZES.tcdTextSize.ordinal())));
        Rectangle2D vwRect;
        try {
            String imgFile = props.getImages(IMAGES.tcdIconOn.ordinal());
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

