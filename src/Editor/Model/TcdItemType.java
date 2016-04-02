package Editor.Model;

public enum TcdItemType {

    NONE("TcdControl"),
    BUTTON("TcdButton"),
    SWITCH("TcdSwitch"),
    KNOB("TcdFader");

    private String label;

    TcdItemType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
