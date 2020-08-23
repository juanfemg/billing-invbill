package co.com.juan.invbill.enums;

/**
 * @author Juan Felipe
 * 
 */
public enum ReporteMensualEnum {

	VENTAS("Reporte Mensual Ventas"), DEVOLUCIONES("Reporte Mensual Devoluciones"), COMPRAS("Reporte Mensual Compras");

	private final String reporte;

	private ReporteMensualEnum(String reporte) {
		this.reporte = reporte;
	}

	/**
	 * @return the reporte
	 */
	public String getReporte() {
		return reporte;
	}

}
