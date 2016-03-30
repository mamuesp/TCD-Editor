package Editor.Items.Skins;

import Editor.Items.TcdButton;
import Editor.Items.TcdControl;
import Editor.Items.TcdSwitch;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;

public class TcdSwitchSkin extends SkinBase<TcdSwitch> {

    protected String tcdLabel = "A switch!";
    protected String tcdIconOff = "resources/img/switch-off.png";
    protected String tcdIconOn = "resources/img/switch-on.png";
    protected Color tcdTextColorOff = Color.web("#8b7c71");
    protected Color tcdTextColorOn = Color.web("#8b7c71");
    protected int tcdTextSize = 20;
    protected BorderPane tcdSkinBase = new BorderPane();

    protected double value = 0;

    public TcdSwitchSkin(final TcdSwitch control) {
        super(control);
        initializeGraphics();
    }

    public void initializeGraphics() {

        // draw switch area

        TcdSwitch control = (TcdSwitch) getSkinnable();
        getChildren().clear();
        getChildren().add(tcdSkinBase);

        double w = control.getWidth();
        double h = control.getHeight();

        ImageView ivImg = null;
        Color fontColor = (control.getValue() != 0.0) ? tcdTextColorOn : tcdTextColorOff;
        Font lblFont = new Font("Arial", tcdTextSize);

        try {
            String imgFile = (control.getValue() != 0.0) ? tcdIconOn : tcdIconOff;
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

        Label label = new Label(tcdLabel);
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

    public String getLabelText() {
        return tcdLabel;
    }

    public void setLabelText(String value) {
        tcdLabel = value;
    }
}

