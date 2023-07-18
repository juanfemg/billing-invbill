/**
 * 
 */
package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import co.com.juan.invbill.delegate.businessdelegate.IProveedorDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.ProveedorApp;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "crearProveedor")
@ViewScoped
public class CrearProveedorView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_CreacionProveedor";
	private static final long serialVersionUID = -2214542422972201117L;
	private static final Logger log = LoggerFactory.getLogger(CrearProveedorView.class);

	@ManagedProperty(value = "#{clienteDelegate}")
	private transient IClienteDelegate clienteDelegate;

	@ManagedProperty(value = "#{proveedorDelegate}")
	private IProveedorDelegate proveedorDelegate;

	public IProveedorDelegate getProveedorDelegate() {
		return proveedorDelegate;
	}

	public void setProveedorDelegate(IProveedorDelegate proveedorDelegate) {
		this.proveedorDelegate = proveedorDelegate;
	}

	private ProveedorApp proveedorApp;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public CrearProveedorView() {
		super();
	}

	@PostConstruct
	public void init() {
		proveedorApp = new ProveedorApp();
	}

	public void actionGuardar() {
		try {
			this.proveedorDelegate.save(proveedorApp);
			log.info("=== Creacion de proveedor: Proveedor creado {}", proveedorApp.getIdProveedorApp());
			addInfoMessage(properties.getParameterByKey("MSG_PROVEEDOR_CREADO"));
			proveedorApp = new ProveedorApp();
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_PROVEEDOR"));
			log.error("=== Creacion de proveedor: Fallo la creacion del proveedor {}. Se ha producido un error: {}",
					proveedorApp.getIdProveedorApp(), e.getMessage());
		}
	}

	public void actionLimpiar() {
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
	}

	public void addInfoMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addWarnMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addErrorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * @return the businessDelegate
	 */
	public IClienteDelegate getClienteDelegate() {
		return clienteDelegate;
	}

	/**
	 * 
	 */
	public void setClienteDelegate(IClienteDelegate clienteDelegate) {
		this.clienteDelegate = clienteDelegate;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
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

}
