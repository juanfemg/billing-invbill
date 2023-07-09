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
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "consultarUsuario")
@ViewScoped
public class ConsultarUsuarioView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ModificacionUsuario";
	private static final long serialVersionUID = 4796917828185803978L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarUsuarioView.class);

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private UsuarioApp usuarioApp;
	private UsuarioApp usuarioModApp;
	private List<SelectItem> estadosApp;
	private boolean showPanelModificarUsuario;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarUsuarioView() {
		super();
	}

	@PostConstruct
	public void init() {
		usuarioApp = new UsuarioApp();
		usuarioModApp = new UsuarioApp();
		estadosApp = new ArrayList<>();
		showPanelModificarUsuario = false;
		initEstadosApp();
	}

	public void initEstadosApp() {
		StatusEnum[] estadosAppEnums = StatusEnum.values();
		for (StatusEnum estadosAppEnumTemp : estadosAppEnums) {
			estadosApp.add(new SelectItem(estadosAppEnumTemp, estadosAppEnumTemp.getStatus()));
		}
	}

	public void actionConsultar() {
		try {
			usuarioModApp = businessDelegate.findUsuarioByID(usuarioApp.getIdUsuarioApp());

			if (usuarioModApp != null)
				showPanelModificarUsuario = true;
			else
				addWarnMessage(properties.getParametroString("MSG_DATA_NO_FOUND"));

			log.info("=== Consulta de usuario: Usuario consultado {} ===", usuarioApp.getIdUsuarioApp());
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_USUARIO"));
			log.error("=== Consulta de usuario: Fallo la consulta del usuario {}. Se ha producido un error: {}",
					usuarioApp.getIdUsuarioApp(), e.getMessage());
		}
	}

	public void actionModificar() {
		try {
			businessDelegate.update(usuarioModApp);
			showPanelModificarUsuario = false;
			log.info("=== Actualizacion de usuario: Usuario actualizado. Id={}, descripcion={} === ",
					usuarioModApp.getIdUsuarioApp(), usuarioModApp.getNombre());
			addInfoMessage(properties.getParametroString("MSG_USUARIO_ACTUALIZADO"));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_ACTUALIZACION_USUARIO"));
			log.error(
					"=== Actualizacion de usuario: Fallo la actualizacion del usuario {}. Se ha producido un error: {}",
					usuarioModApp.getIdUsuarioApp(), e.getMessage());
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
	 * @return the usuarioModApp
	 */
	public UsuarioApp getUsuarioModApp() {
		return usuarioModApp;
	}

	/**
	 * @param usuarioModApp the usuarioModApp to set
	 */
	public void setUsuarioModApp(UsuarioApp usuarioModApp) {
		this.usuarioModApp = usuarioModApp;
	}

	/**
	 * @return the showPanelModificarUsuario
	 */
	public boolean isShowPanelModificarUsuario() {
		return showPanelModificarUsuario;
	}

	/**
	 * @param showPanelModificarUsuario the showPanelModificarUsuario to set
	 */
	public void setShowPanelModificarUsuario(boolean showPanelModificarUsuario) {
		this.showPanelModificarUsuario = showPanelModificarUsuario;
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
