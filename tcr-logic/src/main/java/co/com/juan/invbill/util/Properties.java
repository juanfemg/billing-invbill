package co.com.juan.invbill.util;

/**
 * @author Juan Felipe
 * 
 */
public class Properties {

	private LoadBundle loadBundle;

	/**
	 * Constructor por defecto.
	 * 
	 */
	public Properties() {
	}

	/**
	 * Constructor por defecto. Carga el archivo de propiedades.
	 * 
	 */
	public Properties(String file) {
		loadBundle = new LoadBundle(file);
	}

	/**
	 * Retorna la cadena de la llave ingresada como atributo.
	 * 
	 * @param key String que sera consultado en el archivo de propiedades.
	 * @return El String de la llave ingresada.
	 */
	public String getParametroString(String key) {
		return loadBundle.getProperty(key);
	}

	/**
	 * Retorna la cadena de la llave y parametros ingresados como atributo.
	 * 
	 * @param key        String que sera consultado en el archivo de propiedades.
	 * @param parameters Arreglo de parametros
	 * @return El String de la llave ingresada.
	 */
	public String getParametroString(String key, String[] parameters) {
		return loadBundle.getProperty(key, parameters);
	}

	/**
	 * Retorna la cadena de la llave y parametro ingresado como atributo.
	 * 
	 * @param key       String que sera consultado en el archivo de propiedades.
	 * @param parameter Parametro
	 * @return El String de la llave ingresada.
	 */
	public String getParametroString(String key, String parameter) {
		return loadBundle.getProperty(key, parameter);
	}

	/**
	 * Retorna el valor numerico de la llave ingresada como atributo.
	 * 
	 * @param key String que sera consultado en el archivo de propiedades.
	 * @return El valor numerico de la llave ingresada.
	 */
	public int getParametroInteger(String key) {
		int value;
		try {
			value = Integer.parseInt(loadBundle.getProperty(key));
			return value;
		} catch (Exception e) {
			value = 0;
		}
		return value;
	}

}
