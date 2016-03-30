package Editor.Items.Skins;

import Editor.Items.TcdControl;
import Editor.Items.TcdButton;
import com.sun.tools.doclets.internal.toolkit.util.Group;
import javafx.geometry.*;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;
import java.util.ArrayList;

import static com.sun.tools.doclint.Entity.image;

public class TcdButtonSkin extends SkinBase<TcdButton> {

    protected String tcdLabel = "A button!";
    protected String tcdIconOff = "resources/img/button-off.png";
    protected String tcdIconOn = "resources/img/button-on.png";
    protected int tcdItemWidth = 150;
    protected int tcdItemHeight = 40;
    protected Color tcdTextColorOff = Color.WHITE;
    protected Color tcdTextColorOn = Color.web("#8b7c71");
    protected int tcdTextSize = 20;
    protected StackPane tcdSkinBase = new StackPane();

    protected double value = 0;

    public TcdButtonSkin(final TcdButton control) {
        super(control);
        initializeGraphics();
    }

    public void initializeGraphics() {

        // draw Button area
        TcdButton control = (TcdButton) getSkinnable();
        getChildren().clear();
        getChildren().add(tcdSkinBase);

        double w = control.getWidth();
        double h = control.getHeight();

        ImageView ivImgOff = null;
        ImageView ivImgOn = null;
        Color fontColor = (control.getValue() != 0.0) ? tcdTextColorOn : tcdTextColorOff;
        Rectangle2D vwRect;
        Text label = new Text(tcdLabel);
        Font lblFont = new Font("Arial", tcdTextSize);

        tcdSkinBase.getChildren().clear();
        try {
            Image imgOn = new Image(this.getClass().getClassLoader().getResourceAsStream(tcdIconOn));
            Image imgOff = new Image(this.getClass().getClassLoader().getResourceAsStream(tcdIconOff));
            ivImgOn = new ImageView(imgOn);
            ivImgOff = new ImageView(imgOff);
            ivImgOn.setVisible(control.getValue() != 0.0);
            ivImgOff.setVisible(control.getValue() == 0.0);

            ImageView[] ivColl = { ivImgOn, ivImgOff };
            for (ImageView ivCurr: ivColl) {
                ivCurr.setPreserveRatio(true);
                ivCurr.setSmooth(true);
                ivCurr.setCache(true);
                ivCurr.setPickOnBounds(false);
                ivCurr.setMouseTransparent(true);
            }
            tcdSkinBase.getChildren().addAll(ivColl);
        } catch (Exception ex) {
            ivImgOff = null;
            ivImgOn = null;
            tcdSkinBase.getChildren().add(new Text("No Image!"));
        }

        label.setPickOnBounds(false);
        label.prefHeight(tcdTextSize);
        label.setTextOrigin(VPos.CENTER);
        label.setFont(lblFont);
        label.setFill(fontColor);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFontSmoothingType(FontSmoothingType.LCD);
        label.setMouseTransparent(true);
        tcdSkinBase.getChildren().add(label);
    }

    public String getLabelText() {
        return tcdLabel;
    }

    public void setLabelText(String value) {
        tcdLabel = value;
    }
}

