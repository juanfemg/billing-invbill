package co.com.juan.invbill.enums;

/**
 * @author Juan Felipe
 * 
 */
public enum EstadoEnum {

	A("ACTIVO"), I("INACTIVO");

	private final String estado;

	private EstadoEnum(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

}
