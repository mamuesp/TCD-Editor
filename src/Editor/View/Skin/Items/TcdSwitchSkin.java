package Editor.View.Skin.Items;

import Editor.Model.Items.TcdSwitch;
import Editor.View.Skin.IControlSkin;
import Editor.View.Skin.TcdControlSkin;
import Editor.View.Skin.TcdSkinEnums;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
        this.props = (this.props == null) ? props : this.props;

        tmpList.add(TcdSkinEnums.Images.ICONON.ordinal(), "resources/img/switch-on.png");
        tmpList.add(TcdSkinEnums.Images.ICONOFF.ordinal(), "resources/img/switch-off.png");
        props.setImages(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TcdSkinEnums.Colors.TEXTCOLORON.ordinal(), "#8b7c71");
        tmpList.add(TcdSkinEnums.Colors.TEXTCOLOROFF.ordinal(), "#8b7c71");
        props.setColors(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TcdSkinEnums.Texts.LABEL.ordinal(), "A switch!");
        props.setTexts(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        tmpList.add(TcdSkinEnums.Sizes.ITEMWIDTH.ordinal(), "100.0");
        tmpList.add(TcdSkinEnums.Sizes.ITEMHEIGHT.ordinal(), "80.0");
        tmpList.add(TcdSkinEnums.Sizes.TEXTSIZE.ordinal(), "20.0");
        props.setSizes(tmpList.toArray(new String[tmpList.size()]));
        tmpList.clear();

        super.initDefaults();
    }

    public void initializeGraphics() {

        // draw switch area

        TcdSwitch control = (TcdSwitch) getSkinnable();
        getChildren().clear();
        getChildren().add(tcdSkinBase);

        double w = Double.parseDouble(props.getSizes(TcdSkinEnums.Sizes.ITEMWIDTH.ordinal()));
        double h = Double.parseDouble(props.getSizes(TcdSkinEnums.Sizes.ITEMHEIGHT.ordinal()));
        control.setPrefSize(w, h);

        ImageView ivImg = null;
        Font lblFont = new Font("Arial", Double.parseDouble(props.getSizes(TcdSkinEnums.Sizes.TEXTSIZE.ordinal())));
        Color fontColor = Color.web(props.getColors((control.getValue() != 0.0) ? TcdSkinEnums.Colors.TEXTCOLORON.ordinal() : TcdSkinEnums.Colors.TEXTCOLOROFF.ordinal()));
        Rectangle2D vwRect;
        try {
            String imgFile = props.getImages((control.getValue() != 0.0) ? TcdSkinEnums.Images.ICONON.ordinal() : TcdSkinEnums.Images.ICONOFF.ordinal());
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

        Label label = new Label(props.getTexts(TcdSkinEnums.Texts.LABEL.ordinal()));
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

