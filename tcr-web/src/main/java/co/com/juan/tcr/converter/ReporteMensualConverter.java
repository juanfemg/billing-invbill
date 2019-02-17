package co.com.juan.tcr.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.com.juan.tcr.enums.ReporteMensualEnum;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "reporteMensualConverter")
@RequestScoped
public class ReporteMensualConverter implements Converter {

	private ReporteMensualEnum reporteMensual;

	/**
	 * Gets the as object.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param component
	 *            the component
	 * @param value
	 *            the value
	 * @return the as object
	 */
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value == null)
			return "";

		ReporteMensualEnum[] reportesMensualEnums = ReporteMensualEnum.values();
		for (ReporteMensualEnum reporteMensualEnumTemp : reportesMensualEnums) {
			if (value.equalsIgnoreCase(String.valueOf(reporteMensualEnumTemp.getReporte()))) {
				reporteMensual = reporteMensualEnumTemp;
				break;
			}
		}
		return reporteMensual;
	}

	/**
	 * Gets the as string.
	 * 
	 * @param fc
	 *            the fc
	 * @param uic
	 *            the uic
	 * @param o
	 *            the o
	 * @return the as string
	 */
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null)
			return null;

		reporteMensual = (ReporteMensualEnum) o;
		return reporteMensual.getReporte();
	}

	/**
	 * @return the reporteMensual
	 */
	public ReporteMensualEnum getReporteMensual() {
		return reporteMensual;
	}

	/**
	 * @param reporteMensual
	 *            the reporteMensual to set
	 */
	public void setReporteMensual(ReporteMensualEnum reporteMensual) {
		this.reporteMensual = reporteMensual;
	}

}
