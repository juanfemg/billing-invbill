package co.com.juan.tcr.util;

/**
 * @author JuanFelipe
 *
 */
public class ParameterApp {

	private String name;

	/**
	 * @param name
	 */
	protected ParameterApp(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	@Override
	public String toString() {
		return this.name;
	}

	public static final ParameterApp IMPRESORA_PREDETERMINADA = new ParameterApp("IMPRESORA_PREDETERMINADA");

	public static final ParameterApp IMPUESTO_IVA = new ParameterApp("IVA");

	public static final ParameterApp TOPE_STOCK = new ParameterApp("TOPE_STOCK");

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
