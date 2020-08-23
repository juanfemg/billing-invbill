package co.com.juan.invbill.dto;

/**
 * @author Juan Felipe
 *
 */
public class ReporteDevolucionMensual implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Double valorTotal;
	private Integer diaMes;

	/**
	 * Constructor
	 */
	public ReporteDevolucionMensual() {
		super();
	}

	/**
	 * @param valorTotal
	 * @param diaMes
	 */
	public ReporteDevolucionMensual(Double valorTotal, Integer diaMes) {
		super();
		this.valorTotal = valorTotal;
		this.diaMes = diaMes;
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
	 * @return the diaMes
	 */
	public Integer getDiaMes() {
		return diaMes;
	}

	/**
	 * @param diaMes
	 *            the diaMes to set
	 */
	public void setDiaMes(Integer diaMes) {
		this.diaMes = diaMes;
	}

}
