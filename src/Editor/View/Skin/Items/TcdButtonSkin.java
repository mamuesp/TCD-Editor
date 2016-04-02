package Editor.View.Skin.Items;

import Editor.Model.Items.TcdButton;
import Editor.View.Skin.IControlSkin;
import Editor.View.Skin.TcdControlSkin;
import Editor.View.Skin.TcdPropertiesBean;
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

    public void loadDefaults(TcdPropertiesBean props) {
        this.props = (this.props == null) ? props : this.props;

        ArrayList<String> tmpList = new ArrayList<String>();

        tmpList.add(IMAGES.tcdIconOn.ordinal(), "resources/img/button-on.png");
        tmpList.add(IMAGES.tcdIconOff.ordinal(), "resources/img/button-off.png");
        props.setImages(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(COLORS.tcdTextColorOn.ordinal(), "#8b7c71");
        tmpList.add(COLORS.tcdTextColorOff.ordinal(), "#FFFFFF");
        props.setColors(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TEXTS.tcdLabel.ordinal(), "A button!");
        props.setTexts(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(SIZES.tcdItemWidth.ordinal(), "150.0");
        tmpList.add(SIZES.tcdItemHeight.ordinal(), "40.0");
        tmpList.add(SIZES.tcdTextSize.ordinal(), "20.0");
        props.setSizes(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        ImageView ivImg = null;
        int colIdx = (control.getValue() != 0.0) ? COLORS.tcdTextColorOn.ordinal() : COLORS.tcdTextColorOff.ordinal();
        Color fontColor = Color.web(props.getColors(colIdx));
        Text label = new Text(props.getTexts(TEXTS.tcdLabel.ordinal()));
        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes(SIZES.tcdTextSize.ordinal())));
        Rectangle2D vwRect;

        tcdSkinBase.getChildren().clear();
        try {
            int imgIdx = (control.getValue() != 0.0) ? IMAGES.tcdIconOn.ordinal() : IMAGES.tcdIconOff.ordinal();
            String imgFile = props.getImages(imgIdx);
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
        label.prefHeight(Double.parseDouble(props.getSizes(SIZES.tcdTextSize.ordinal())));
        label.setTextOrigin(VPos.CENTER);
        label.setFont(lblFont);
        label.setFill(fontColor);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFontSmoothingType(FontSmoothingType.LCD);
        label.setMouseTransparent(true);

        tcdSkinBase.setCenter(label);
    }
}

