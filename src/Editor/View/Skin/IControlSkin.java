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

    public Map<String, String> getColors();
    public void setColors(Map<String, String> colors);
    public Map<String, String> getImages();
    public void setImages(Map<String, String> images);
    public Map<String, String> getTexts();
    public void setTexts(Map<String, String> texts);
    public Map<String, String> getSizes();
    public void setSizes(Map<String, String> sizes);

    public void removeDecoration();
    public void addDecoration();
    public TcdProperties getProperties();

    public void initDefaults();
}
