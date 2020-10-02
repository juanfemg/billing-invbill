package co.com.juan.invbill.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.com.juan.invbill.enums.EstadoEnum;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "estadoConverter")
@RequestScoped
public class EstadoConverter implements Converter {

	private EstadoEnum estado;

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

		EstadoEnum[] estadosAppEnums = EstadoEnum.values();
		for (EstadoEnum estadosAppEnumTemp : estadosAppEnums) {
			if (value.equalsIgnoreCase(String.valueOf(estadosAppEnumTemp.getEstado()))) {
				estado = estadosAppEnumTemp;
				break;
			}
		}
		return estado;
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

		estado = (EstadoEnum) o;
		return estado.getEstado();
	}

	/**
	 * @return the estado
	 */
	public EstadoEnum getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

}
