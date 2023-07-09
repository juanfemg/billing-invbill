package co.com.juan.invbill.model;

/**
 * @author Juan Felipe
 *
 */
public class ReporteVentaDiaria implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Double valorTotal;
	private Integer cantidadProductos;

	/**
	 * Constructor
	 */
	public ReporteVentaDiaria() {
		super();
	}

	/**
	 * @param valorTotal
	 * @param cantidadProductos
	 */
	public ReporteVentaDiaria(Double valorTotal, Integer cantidadProductos) {
		super();
		this.valorTotal = valorTotal;
		this.cantidadProductos = cantidadProductos;
	}

	/**
	 * @return the valorTotal
	 */
	public Double getValorTotal() {
		return valorTotal;
	}

	/**
	 * @param valorTotal
	 *            the valorTotal to set
	 */
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
	 * @return the cantidadProductos
	 */
	public Integer getCantidadProductos() {
		return cantidadProductos;
	}

	/**
	 * @param cantidadProductos
	 *            the cantidadProductos to set
	 */
	public void setCantidadProductos(Integer cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

}
