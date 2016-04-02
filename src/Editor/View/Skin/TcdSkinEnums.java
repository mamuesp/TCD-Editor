package Editor.View.Skin;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 02.04.16.
 *
 * @version ${Version}
 */
public class TcdSkinEnums {

    public enum Sizes {
        ITEMWIDTH("tcdItemWidth", 0),
        ITEMHEIGHT("tcdItemHeight", 1),
        TEXTSIZE("tcdTextSize", 2);

        private String name;
        private int idx;

        Sizes(String name, int idx) {
            this.name = name;
            this.idx = idx;
        }
        public String toString() {
            return name;
        }
    }

    public enum Images {
        ICONON("tcdIconOn"),
        ICONOFF("tcdIconOff");

        private String label;

        Images(String label) {
            this.label = label;
        }
        public String toString() {
            return label;
        }
    }

    public enum Texts {
        LABEL("tcdLabel");

        private String label;

        Texts(String label) {
            this.label = label;
        }
        public String toString() {
            return label;
        }
    }

    public enum Colors {
        TEXTCOLORON("tcdTextColorOn"),
        TEXTCOLOROFF("tcdTextColorOff");

        private String label;

        Colors(String label) {
            this.label = label;
        }
        public String toString() {
            return label;
        }
    }
}
