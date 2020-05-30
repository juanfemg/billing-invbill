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
import co.com.juan.tcr.model.ClienteApp;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "clienteAppConverter")
@RequestScoped
public class ClienteAppConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(ClienteAppConverter.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private IBusinessDelegate businessDelegate;

	private ClienteApp clienteApp;

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
			List<ClienteApp> clienteesApp = businessDelegate.getClientes();

			for (ClienteApp clienteAppTemp : clienteesApp) {
				if (value.equalsIgnoreCase(String.valueOf(clienteAppTemp.getRazonSocial()))) {
					clienteApp = clienteAppTemp;
					break;
				}
			}

			if (clienteApp == null) {
				clienteApp = new ClienteApp();
				clienteApp.setIdClienteApp(Integer.valueOf(value));
			}
		} catch (Exception e) {
			log.error(
					"== Cliente App Converter: Fallo al retornar el objeto del valor {}. Se ha producido un error: {}",
					value, e.getMessage());
		}

		return clienteApp;
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

		clienteApp = (ClienteApp) o;
		return clienteApp.getRazonSocial();
	}

	/**
	 * @return the clienteApp
	 */
	public ClienteApp getClienteApp() {
		return clienteApp;
	}

	/**
	 * @param clienteApp the clienteApp to set
	 */
	public void setClienteApp(ClienteApp clienteApp) {
		this.clienteApp = clienteApp;
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
