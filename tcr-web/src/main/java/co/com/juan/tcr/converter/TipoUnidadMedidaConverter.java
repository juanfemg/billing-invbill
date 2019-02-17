package co.com.juan.tcr.converter;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.tcr.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.tcr.model.TipoUnidadMedida;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "tipoUnidadMedidaConverter")
@RequestScoped
public class TipoUnidadMedidaConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(TipoUnidadMedidaConverter.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private IBusinessDelegate businessDelegate;

	private TipoUnidadMedida tipoUnidadMedida;

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
		try {
			List<TipoUnidadMedida> tiposUnidadMedida = businessDelegate.getTiposUnidadMedida();

			for (TipoUnidadMedida tipoUnidadMedidaTemp : tiposUnidadMedida) {
				if (value.equalsIgnoreCase(String.valueOf(tipoUnidadMedidaTemp.getUnidad()))) {
					tipoUnidadMedida = tipoUnidadMedidaTemp;
					break;
				}
			}
		} catch (Exception e) {
			log.error(
					"== Tipo Unidad Medida Converter: Fallo al retornar el objeto del valor {}. Se ha producido un error: {}",
					value, e.getMessage());
		}

		return tipoUnidadMedida;
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
		tipoUnidadMedida = (TipoUnidadMedida) o;
		return tipoUnidadMedida.getUnidad();
	}

	/**
	 * @return the tipoUnidadMedida
	 */
	public TipoUnidadMedida getTipoUnidadMedida() {
		return tipoUnidadMedida;
	}

	/**
	 * @param tipoUnidadMedida the tipoUnidadMedida to set
	 */
	public void setTipoUnidadMedida(TipoUnidadMedida tipoUnidadMedida) {
		this.tipoUnidadMedida = tipoUnidadMedida;
	}

	/**
	 * @return the businessDelegate
	 */
	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate the businessDelegate to set
	 */
	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

}
