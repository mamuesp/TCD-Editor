package Editor.View.Skin.Items;

import Editor.Model.Items.TcdButton;
import Editor.View.Skin.IControlSkin;
import Editor.View.Skin.TcdControlSkin;
import Editor.View.Skin.TcdPropertiesBean;
import Editor.View.Skin.TcdSkinEnums;
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
        ArrayList<String> tmpList = new ArrayList<String>();
        this.props = (this.props == null) ? props : this.props;

        tmpList.add(TcdSkinEnums.Images.ICONON.ordinal(), "resources/img/button-on.png");
        tmpList.add(TcdSkinEnums.Images.ICONOFF.ordinal(), "resources/img/button-off.png");
        props.setImages(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TcdSkinEnums.Colors.TEXTCOLORON.ordinal(), "#8b7c71");
        tmpList.add(TcdSkinEnums.Colors.TEXTCOLOROFF.ordinal(), "#FFFFFF");
        props.setColors(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TcdSkinEnums.Texts.LABEL.ordinal(), "A button!");
        props.setTexts(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TcdSkinEnums.Sizes.ITEMWIDTH.ordinal(), "150.0");
        tmpList.add(TcdSkinEnums.Sizes.ITEMHEIGHT.ordinal(), "40.0");
        tmpList.add(TcdSkinEnums.Sizes.TEXTSIZE.ordinal(), "20.0");
        props.setSizes(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        super.initDefaults();
    }

    public void initializeGraphics() {

        super.initializeGraphics();

        ImageView ivImg = null;
        int colIdx = (control.getValue() != 0.0) ? TcdSkinEnums.Colors.TEXTCOLORON.ordinal() : TcdSkinEnums.Colors.TEXTCOLOROFF.ordinal();
        Color fontColor = Color.web(props.getColors(colIdx));
        Text label = new Text(props.getTexts(TcdSkinEnums.Texts.LABEL.ordinal()));
        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes(TcdSkinEnums.Sizes.TEXTSIZE.ordinal())));
        Rectangle2D vwRect;

        tcdSkinBase.getChildren().clear();
        try {
            int imgIdx = (control.getValue() != 0.0) ? TcdSkinEnums.Images.ICONON.ordinal() : TcdSkinEnums.Images.ICONOFF.ordinal();
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
        label.prefHeight(Double.parseDouble(props.getSizes(TcdSkinEnums.Sizes.TEXTSIZE.ordinal())));
        label.setTextOrigin(VPos.CENTER);
        label.setFont(lblFont);
        label.setFill(fontColor);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFontSmoothingType(FontSmoothingType.LCD);
        label.setMouseTransparent(true);

        tcdSkinBase.setCenter(label);
    }
}

