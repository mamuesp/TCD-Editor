package Editor.View.Skin;

import Editor.Model.TcdControl;
import impl.org.controlsfx.i18n.Localization;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class TcdPropertyItem implements PropertySheet.Item {

    private String _category;
    private String _name;
    private Object _value;
    private String _description;
    private TcdControl _ctrl;

    public TcdPropertyItem(String category, String name, Object value, String description, TcdControl ctrl) {
        _category = category;
        _name = name;
        _value = value;
        _description = description;
        _ctrl = ctrl;
    }

    @Override
    public Class<?> getType() {
        return _value.getClass();
    }

    @Override
    public String getCategory() {
        return _category;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public Object getValue() {
        return _value;
    }

    @Override
    public void setValue(Object value) {
        _value = (Object) value;
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return null;
    }

}
