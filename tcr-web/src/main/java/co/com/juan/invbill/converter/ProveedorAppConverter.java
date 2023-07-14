package co.com.juan.invbill.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.com.juan.invbill.delegate.businessdelegate.IProveedorDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.ProveedorApp;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "proveedorAppConverter")
@RequestScoped
public class ProveedorAppConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(ProveedorAppConverter.class);

	@ManagedProperty(value = "#{businessDelegate}")
	private IClienteDelegate businessDelegate;

	@ManagedProperty(value = "#{proveedorDelegate}")
	private IProveedorDelegate proveedorDelegate;

	public IProveedorDelegate getProveedorDelegate() {
		return proveedorDelegate;
	}

	public void setProveedorDelegate(IProveedorDelegate proveedorDelegate) {
		this.proveedorDelegate = proveedorDelegate;
	}

	private ProveedorApp proveedorApp;

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

		try {
			proveedorApp = this.proveedorDelegate.findProveedorByID(Integer.parseInt(value));

		} catch (Exception e) {
			log.error(
					"== Proveedor App Converter: Fallo al retornar el objeto del valor {}. Se ha producido un error: {}",
					value, e.getMessage());
		}

		return proveedorApp;
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

		proveedorApp = (ProveedorApp) o;
		return String.valueOf(proveedorApp.getIdProveedorApp());
	}

	/**
	 * @return the proveedorApp
	 */
	public ProveedorApp getProveedorApp() {
		return proveedorApp;
	}

	/**
	 * @param proveedorApp the proveedorApp to set
	 */
	public void setProveedorApp(ProveedorApp proveedorApp) {
		this.proveedorApp = proveedorApp;
	}

	/**
	 * @return the businessDelegate
	 */
	public IClienteDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate the businessDelegate to set
	 */
	public void setBusinessDelegate(IClienteDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

}
