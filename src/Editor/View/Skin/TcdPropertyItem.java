package Editor.View.Skin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(name = "item")
public class TcdPropertyItem {

    private SimpleStringProperty pCategory;
    private SimpleStringProperty pName;
    private SimpleStringProperty pValue;
    private SimpleStringProperty pIdx;
    private SimpleStringProperty pDescription;
    private SimpleStringProperty pType;

    public TcdPropertyItem() {
    }

    public TcdPropertyItem(String category, String name, String value, String index, String description, String type) {
        this.pCategory = new SimpleStringProperty(category);
        this.pName = new SimpleStringProperty(name);
        this.pValue = new SimpleStringProperty(value);
        this.pIdx = new SimpleStringProperty(index);
        this.pDescription = new SimpleStringProperty(description);
        this.pType = new SimpleStringProperty(type);
    }

    @XmlElement(name = "category")
    public String getCategory() {
        return this.pCategory.get();
    }

    public void setCategory(String fValue) {
        this.pCategory = (this.pCategory == null) ? new SimpleStringProperty(fValue) : this.pCategory;
        this.pCategory.set(fValue);
    }

    @XmlElement(name = "name")
    public String getName() {
        return this.pName.get();
    }

    public void setName(String fValue) {
        this.pName = (this.pName == null) ? new SimpleStringProperty(fValue) : this.pName;
        this.pName.set(fValue);
    }

    @XmlElement(name = "value")
    public String getValue() {
        return this.pValue.get();
    }

    public void setValue(String fValue) {
        this.pValue = (this.pValue == null) ? new SimpleStringProperty(fValue) : this.pValue;
        this.pValue.set(fValue);
    }

    @XmlElement(name = "index")
    public String getIdx() {
        return this.pIdx.get();
    }

    public void setIdx(String fValue) {
        this.pIdx = (this.pIdx == null) ? new SimpleStringProperty(fValue) : this.pIdx;
        this.pIdx.set(fValue);
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return this.pDescription.get();
    }

    public void setDescription(String fValue) {
        this.pDescription = (this.pDescription == null) ? new SimpleStringProperty(fValue) : this.pDescription;
        this.pDescription.set(fValue);
    }

    @XmlElement(name = "type")
    public String getType() {
        return this.pType.get();
    }

    public void setType(String fValue) {
        this.pType = (this.pType == null) ? new SimpleStringProperty(fValue) : this.pType;
        this.pType.set(fValue);
    }

}
