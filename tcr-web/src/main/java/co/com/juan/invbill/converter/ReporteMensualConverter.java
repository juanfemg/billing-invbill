package co.com.juan.invbill.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.com.juan.invbill.enums.MonthlyReportEnum;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "reporteMensualConverter")
@RequestScoped
public class ReporteMensualConverter implements Converter {

	private MonthlyReportEnum reporteMensual;

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

		MonthlyReportEnum[] reportesMensualEnums = MonthlyReportEnum.values();
		for (MonthlyReportEnum monthlyReportEnumTemp : reportesMensualEnums) {
			if (value.equalsIgnoreCase(String.valueOf(monthlyReportEnumTemp.getReport()))) {
				reporteMensual = monthlyReportEnumTemp;
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

		reporteMensual = (MonthlyReportEnum) o;
		return reporteMensual.getReport();
	}

	/**
	 * @return the reporteMensual
	 */
	public MonthlyReportEnum getReporteMensual() {
		return reporteMensual;
	}

	/**
	 * @param reporteMensual
	 *            the reporteMensual to set
	 */
	public void setReporteMensual(MonthlyReportEnum reporteMensual) {
		this.reporteMensual = reporteMensual;
	}

}
