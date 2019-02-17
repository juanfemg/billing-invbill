package co.com.juan.tcr.enums;

/**
 * @author Juan Felipe
 * 
 */
public enum EstadosAppEnum {

	A("ACTIVO"), I("INACTIVO");

	private final String estado;

	private EstadosAppEnum(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

}
