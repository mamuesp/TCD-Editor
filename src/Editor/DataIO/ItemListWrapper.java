package Editor.DataIO;

import Editor.Items.TcdInfoCtrl;

import javax.xml.bind.annotation.*;
import java.util.List;

//@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(namespace = "https://mueller-spaeth.com/", name = "collection")
public class ItemListWrapper {

    private List<TcdInfoCtrl> controls;

    public ItemListWrapper() {}

    @XmlElement(name="controls")
    public List<TcdInfoCtrl> getControls() {
        return this.controls;
    }

    public void setControls(List<TcdInfoCtrl> value) {
        this.controls = value;
    }
}