package Editor.View.Skin;

import java.util.Map;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 31.03.16.
 *
 * @version ${Version}
 */
public interface IControlSkin {

    /*
    public String getColors(int index);
    public void setColors(int index, String value);
    public String getImages(int index);
    public void setImages(int index, String value);
    public String getTexts(int index);
    public void setTexts(int index, String value);
    public String getSizes(int index);
    public void setSizes(int index, String value);
    */

    public String[] getColors();
    public void setColors(String[] colors);
    public String[] getImages();
    public void setImages(String[] images);
    public String[] getTexts();
    public void setTexts(String[] texts);
    public String[] getSizes();
    public void setSizes(String[] sizes);

    public void removeDecoration();
    public void addDecoration();
    public TcdPropertiesBean getProperties();

    public void initDefaults();
}
