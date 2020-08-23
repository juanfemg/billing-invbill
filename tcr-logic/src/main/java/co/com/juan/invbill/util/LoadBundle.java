package co.com.juan.invbill.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Felipe
 * 
 */
public class LoadBundle {

	private static final Logger log = LoggerFactory.getLogger(LoadBundle.class);
	private ResourceBundle resources;
	private String property;

	/**
	 * Contructor de la clase.
	 * 
	 */
	public LoadBundle() {
	}

	/**
	 * Instancia de inicializacion.
	 * 
	 */
	public LoadBundle(String file) {
		try {
			resources = ResourceBundle.getBundle(file, Locale.getDefault());
		} catch (Exception e) {
			log.error("file {} upload failed. An error has occurred: {}", file, e.getMessage());
		}
	}

	/**
	 * Retorna la propiedad que le pertenece a la llave entregada por parametro.
	 * 
	 * @param key String que se consulta en el archivo de propiedades.
	 * @return El String de la propiedad del parametro ingresado.
	 */
	public String getProperty(String key) {
		try {
			property = resources.getString(key);
		} catch (Exception e) {
			log.error("finding key {} failed. An error has occurred: {}", key, e.getMessage());
		}

		return property;
	}

	/**
	 * Retorna la propiedad que le pertenece a la llave entregada por parametros.
	 * 
	 * @return El String de la propiedad del parametro ingresado.
	 */
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

	/**
	 * Retorna la propiedad que le pertenece a la llave entregada por parametro.
	 * 
	 * @return El String de la propiedad del parametro ingresado.
	 */
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

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

}
