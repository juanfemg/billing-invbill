package co.com.juan.invbill.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Felipe
 */
public class LoadBundle {

    private static final Logger log = LoggerFactory.getLogger(LoadBundle.class);
    private ResourceBundle resources;
    private String property;

    public LoadBundle(String file) {
        try {
            resources = ResourceBundle.getBundle(file, Locale.getDefault());
        } catch (Exception e) {
            log.error("file {} upload failed. An error has occurred: {}", file, e.getMessage());
        }
    }

    public String getProperty(String key) {
        try {
            property = resources.getString(key);
        } catch (Exception e) {
            log.error("finding key {} failed. An error has occurred: {}", key, e.getMessage());
        }

        return property;
    }

    public String getProperty(String key, String[] parameters) {
        try {
            property = resources.getString(key);
            for (int i = 0; i < parameters.length; i++) {
                property = property.replace("{" + i + "}", parameters[i]);
            }
        } catch (Exception e) {
            log.error("finding key {} failed with parameters. An error has occurred: {}", key, e.getMessage());
        }

        return property;
    }

    public String getProperty(String key, String parameter) {
        try {
            property = resources.getString(key);
            property = property.replace("{" + 0 + "}", parameter);
        } catch (Exception e) {
            log.error("finding key {} with parameter {} failed. An error has occurred: {}", key, parameter,
                    e.getMessage());
        }

        return property;
    }

}
