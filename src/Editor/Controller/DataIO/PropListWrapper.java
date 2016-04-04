package Editor.Controller.DataIO;

import Editor.Model.TcdInfoCtrl;
import Editor.View.Skin.TcdPropertyItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(namespace = "https://mueller-spaeth.com/", name = "properties")
public class PropListWrapper {

    private List<TcdPropertyItem> properties;

    public PropListWrapper() {}

    @XmlElement(name="property")
    public List<TcdPropertyItem> getProperties() {
        return this.properties;
    }

    public void setProperties(List<TcdPropertyItem> value) {
        this.properties = value;
    }
}