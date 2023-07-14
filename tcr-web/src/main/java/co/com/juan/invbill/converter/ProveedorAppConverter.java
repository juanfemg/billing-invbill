package co.com.juan.invbill.converter;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import co.com.juan.invbill.delegate.businessdelegate.IProveedorDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.model.ProveedorApp;

/**
 * @author Juan Felipe
 * 
 */
@Named
@RequestScoped
public class ProveedorAppConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(ProveedorAppConverter.class);
	private final IProveedorDelegate proveedorDelegate;

	@Inject
	public ProveedorAppConverter(IProveedorDelegate proveedorDelegate) {
		this.proveedorDelegate = proveedorDelegate;
	}

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		try {
			return this.proveedorDelegate.findProveedorByID(Integer.parseInt(value));
		} catch (Exception e) {
			log.error("An exception has occurred finding a proveedor with id {}. Error: {}", value, e.getMessage());
		}

		return "";
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		return (o != null) ? String.valueOf(((ProveedorApp) o).getIdProveedorApp()) : null;
	}

}
