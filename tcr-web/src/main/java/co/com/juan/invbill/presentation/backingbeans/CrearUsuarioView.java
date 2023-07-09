package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "crearUsuario")
@ViewScoped
public class CrearUsuarioView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_CreacionUsuario";
	private static final long serialVersionUID = -1691288731051059695L;
	private static final Logger log = LoggerFactory.getLogger(CrearUsuarioView.class);

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private UsuarioApp usuarioApp;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public CrearUsuarioView() {
		super();
	}

	@PostConstruct
	public void init() {
		usuarioApp = new UsuarioApp();
	}

	public void actionGuardar() {
		try {
			businessDelegate.save(usuarioApp);
			log.info("=== Creacion de usuario: Usuario creado {}", usuarioApp.getIdUsuarioApp());
			addInfoMessage(properties.getParameterByKey("MSG_USUARIO_CREADO"));
			usuarioApp = new UsuarioApp();
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_USUARIO"));
			log.error("=== Creacion de usuario: Fallo la creacion del usuario {}. Se ha producido un error: {}",
					usuarioApp.getIdUsuarioApp(), e.getMessage());
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
	 * @return the usuarioApp
	 */
	public UsuarioApp getUsuarioApp() {
		return usuarioApp;
	}

	/**
	 * @param usuarioApp the usuarioApp to set
	 */
	public void setUsuarioApp(UsuarioApp usuarioApp) {
		this.usuarioApp = usuarioApp;
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
