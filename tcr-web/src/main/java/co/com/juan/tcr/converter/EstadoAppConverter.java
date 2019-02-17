package co.com.juan.tcr.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.com.juan.tcr.enums.EstadosAppEnum;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "estadoAppConverter")
@RequestScoped
public class EstadoAppConverter implements Converter {

	private EstadosAppEnum estadosApp;

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
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		EstadosAppEnum[] estadosAppEnums = EstadosAppEnum.values();
		for (EstadosAppEnum estadosAppEnumTemp : estadosAppEnums) {
			if (value.equalsIgnoreCase(String.valueOf(estadosAppEnumTemp
					.getEstado()))) {
				estadosApp = estadosAppEnumTemp;
				break;
			}
		}
		return estadosApp;
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
		estadosApp = (EstadosAppEnum) o;
		return estadosApp.getEstado();
	}

	/**
	 * @return the estadosApp
	 */
	public EstadosAppEnum getEstadosApp() {
		return estadosApp;
	}

	/**
	 * @param estadosApp
	 *            the estadosApp to set
	 */
	public void setEstadosApp(EstadosAppEnum estadosApp) {
		this.estadosApp = estadosApp;
	}

}
