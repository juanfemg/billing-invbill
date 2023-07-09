package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

import co.com.juan.invbill.authentication.AppUserDetails;
import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.enums.SessionEnum;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.util.ParameterApp;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "login")
@ViewScoped
public class LoginView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_LoginUsuario";
	private static final long serialVersionUID = -2224027393110431390L;
	private static final Logger log = LoggerFactory.getLogger(LoginView.class);

	@ManagedProperty(value = "#{authenticationManager}")
	private transient AuthenticationManager authenticationManager;

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private LoginApp loginApp;
	private String userId;
	private String password;
	private List<AppConfig> appConfigs;
	private transient HttpSession session;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public LoginView() {
		super();
	}

	@PostConstruct
	public void init() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	public String actionLogin() {
		try {
			AppUserDetails userDetail = null;
			Authentication request = new UsernamePasswordAuthenticationToken(this.getUserId(), this.getPassword());
			Authentication result = authenticationManager.authenticate(request);

			if (result != null) {
				if (result.getPrincipal() != null) {
					userDetail = (AppUserDetails) result.getPrincipal();

					if (userDetail.isEnabled()) {
						SecurityContext securityContext = SecurityContextHolder.getContext();
						securityContext.setAuthentication(result);
						session.setAttribute(SessionEnum.SPRING_SECURITY_CONTEXT.name(), securityContext);
						String idSession = RequestContextHolder.currentRequestAttributes().getSessionId();

						loginApp = businessDelegate.findLoginByID(result.getName());

						if (loginApp == null) {
							loginApp = new LoginApp(result.getName(), new Date(System.currentTimeMillis()), idSession);
							saveLogin(loginApp);
						} else {
							session.setAttribute(SessionEnum.LAST_LOGIN.name(), loginApp.getUltimoLogin());
							loginApp.setUltimoLogin(new Date(System.currentTimeMillis()));
							loginApp.setIdSession(idSession);
							updateLogin(loginApp);
						}

						session.setAttribute(SessionEnum.LOGIN.name(), loginApp.getIdLoginApp());
						initParametrosConfiguracion();
					} else {
						addErrorMessage(properties.getParametroString("MSG_USER_NO_FOUND"));
						log.error(
								"=== Login de usuario: Fallo la consulta del usuario o no existe el usuario con las credenciales indicadas");
						return "error";
					}
				}
			}
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_USER_NO_FOUND"));
			log.error(
					"=== Login de usuario: Fallo la consulta del usuario o no existe el usuario con las credenciales indicadas",
					e);
			return "error";
		}
		return "exito";

	}

	public void saveLogin(LoginApp loginApp) {
		try {
			businessDelegate.save(loginApp);
		} catch (Exception e) {
			log.error("=== Login de usuario: Fallo el registro del login {}. Se ha producido un error: {}",
					loginApp.getIdLoginApp(), e.getMessage());
			throw e;
		}
	}

	public void updateLogin(LoginApp loginApp) {
		try {
			businessDelegate.update(loginApp);
		} catch (Exception e) {
			log.error("=== Login de usuario: Fallo la actualizacion del login {}. Se ha producido un error: {}",
					loginApp.getIdLoginApp(), e.getMessage());
			throw e;
		}
	}

	public void initParametrosConfiguracion() {
		try {
			appConfigs = businessDelegate.getAppConfigs();

			if (!appConfigs.isEmpty()) {
				for (AppConfig appConfig : appConfigs) {
					if (appConfig.getIdAppConfig().equalsIgnoreCase(ParameterApp.IMPRESORA_PREDETERMINADA.toString())) {
						session.setAttribute(ParameterApp.IMPRESORA_PREDETERMINADA.toString(), appConfig);
					} else if (appConfig.getIdAppConfig().equalsIgnoreCase(ParameterApp.IMPUESTO_IVA.toString())) {
						session.setAttribute(ParameterApp.IMPUESTO_IVA.toString(), appConfig);
					} else if (appConfig.getIdAppConfig().equalsIgnoreCase(ParameterApp.TOPE_STOCK.toString())) {
						session.setAttribute(ParameterApp.TOPE_STOCK.toString(), appConfig);
					}
				}
			}
		} catch (Exception e) {
			log.error("=== Configuracion del Sistema: No fue posible cargar los parametros", e);
		}
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
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginApp
	 */
	public LoginApp getLoginApp() {
		return loginApp;
	}

	/**
	 * @param loginApp the loginApp to set
	 */
	public void setLoginApp(LoginApp loginApp) {
		this.loginApp = loginApp;
	}

	/**
	 * @return the appConfigs
	 */
	public List<AppConfig> getAppConfigs() {
		return appConfigs;
	}

	/**
	 * @param appConfigs the appConfigs to set
	 */
	public void setAppConfigs(List<AppConfig> appConfigs) {
		this.appConfigs = appConfigs;
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

}
