package co.com.juan.invbill.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.com.juan.invbill.enums.ReportFormatEnum;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "formatoReporteConverter")
@RequestScoped
public class FormatoReporteConverter implements Converter {

	private ReportFormatEnum formatoReporte;

	/**
	 * Gets the as object.
	 * 
	 * @param ctx       the ctx
	 * @param component the component
	 * @param value     the value
	 * @return the as object
	 */
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value == null)
			return "";

		ReportFormatEnum[] formatosReporteEnums = ReportFormatEnum.values();
		for (ReportFormatEnum formatosReporteEnumTemp : formatosReporteEnums) {
			if (value.equalsIgnoreCase(String.valueOf(formatosReporteEnumTemp.getFormat()))) {
				formatoReporte = formatosReporteEnumTemp;
				break;
			}
		}
		return formatoReporte;
	}

	/**
	 * Gets the as string.
	 * 
	 * @param fc  the fc
	 * @param uic the uic
	 * @param o   the o
	 * @return the as string
	 */
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null)
			return null;

		formatoReporte = (ReportFormatEnum) o;
		return formatoReporte.getFormat();
	}

	/**
	 * @return the formatoReporte
	 */
	public ReportFormatEnum getFormatoReporte() {
		return formatoReporte;
	}

	/**
	 * @param formatoReporte the formatoReporte to set
	 */
	public void setFormatoReporte(ReportFormatEnum formatoReporte) {
		this.formatoReporte = formatoReporte;
	}

}
