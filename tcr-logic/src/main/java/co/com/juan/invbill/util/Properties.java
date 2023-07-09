package co.com.juan.invbill.util;

/**
 * @author Juan Felipe
 */
public class Properties {

    private LoadBundle loadBundle;

    public Properties() {
    }

    public Properties(String file) {
        loadBundle = new LoadBundle(file);
    }

    public String getParameterByKey(String key) {
        return loadBundle.getProperty(key);
    }

    public String getParameterByKeyAndNameArray(String key, String[] parameters) {
        return loadBundle.getProperty(key, parameters);
    }

    public String getParameterByKeyAndName(String key, String parameter) {
        return loadBundle.getProperty(key, parameter);
    }

}
