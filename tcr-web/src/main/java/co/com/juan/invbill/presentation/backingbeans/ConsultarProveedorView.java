package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.enums.EstadoEnum;
import co.com.juan.invbill.model.ProveedorApp;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "consultarProveedor")
@ViewScoped
public class ConsultarProveedorView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ModificacionProveedor";
	private static final long serialVersionUID = 7144818427907553778L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarProveedorView.class);
	private static final String ID_DIALOG_MESSAGES = "menMod";

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private ProveedorApp proveedorModApp;
	private List<SelectItem> estadosApp;
	private List<ProveedorApp> proveedoresApp;
	private boolean showDialogModificarProveedor;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarProveedorView() {
		super();
	}

	@PostConstruct
	public void init() {
		proveedorModApp = new ProveedorApp();
		estadosApp = new ArrayList<>();
		proveedoresApp = new ArrayList<>();
		showDialogModificarProveedor = false;
		initEstadosApp();
		initProveedores();
	}

	public void initEstadosApp() {
		for (EstadoEnum estadosAppEnumTemp : EstadoEnum.values()) {
			estadosApp.add(new SelectItem(estadosAppEnumTemp, estadosAppEnumTemp.getEstado()));
		}
	}

	public void initProveedores() {
		try {
			proveedoresApp = businessDelegate.getProveedores();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_PROVEEDORES"));
			log.error("=== Consulta de Proveedores: Fallo la consulta de los proveedores", e);
		}
	}

	public void actionEditar() {
		showDialogModificarProveedor = true;
	}

	public void actionModificar() {
		try {
			businessDelegate.update(proveedorModApp);
			showDialogModificarProveedor = false;
			log.info("=== Actualizacion de proveedor: Proveedor actualizado. Id={}, descripcion={} ===",
					proveedorModApp.getIdProveedorApp(), proveedorModApp.getRazonSocial());
			addInfoMessage(properties.getParametroString("MSG_PROVEEDOR_ACTUALIZADO"));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_ACTUALIZACION_PROVEEDOR"), ID_DIALOG_MESSAGES);
			log.error(
					"=== Actualizacion de proveedor: Fallo la actualizacion del proveedor {}. Se ha producido un error: {}",
					proveedorModApp.getIdProveedorApp(), e.getMessage());
		}
	}

	public void actionCancelar() {
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

	public void addErrorMessage(String summary, String clientId) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
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
	 * @return the proveedorModApp
	 */
	public ProveedorApp getProveedorModApp() {
		return proveedorModApp;
	}

	/**
	 * @param proveedorModApp the proveedorModApp to set
	 */
	public void setProveedorModApp(ProveedorApp proveedorModApp) {
		this.proveedorModApp = proveedorModApp;
	}

	/**
	 * @return the estadosApp
	 */
	public List<SelectItem> getEstadosApp() {
		return estadosApp;
	}

	/**
	 * @param estadosApp the estadosApp to set
	 */
	public void setEstadosApp(List<SelectItem> estadosApp) {
		this.estadosApp = estadosApp;
	}

	/**
	 * @return the proveedoresApp
	 */
	public List<ProveedorApp> getProveedoresApp() {
		return proveedoresApp;
	}

	/**
	 * @param proveedoresApp the proveedoresApp to set
	 */
	public void setProveedoresApp(List<ProveedorApp> proveedoresApp) {
		this.proveedoresApp = proveedoresApp;
	}

	/**
	 * @return the showDialogModificarProveedor
	 */
	public boolean isShowDialogModificarProveedor() {
		return showDialogModificarProveedor;
	}

	/**
	 * @param showDialogModificarProveedor the showDialogModificarProveedor to set
	 */
	public void setShowDialogModificarProveedor(boolean showDialogModificarProveedor) {
		this.showDialogModificarProveedor = showDialogModificarProveedor;
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

}
