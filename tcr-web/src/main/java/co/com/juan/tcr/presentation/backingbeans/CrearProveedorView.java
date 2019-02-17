/**
 * 
 */
package co.com.juan.tcr.presentation.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.tcr.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.tcr.model.ProveedorApp;
import co.com.juan.tcr.util.Properties;

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

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

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
			businessDelegate.save(proveedorApp);
			log.info("=== Creacion de proveedor: Proveedor creado {}", proveedorApp.getIdProveedorApp());
			addInfoMessage(properties.getParametroString("MSG_PROVEEDOR_CREADO"));
			proveedorApp = new ProveedorApp();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CREACION_PROVEEDOR"));
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
	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate the businessDelegate to set
	 */
	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
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
