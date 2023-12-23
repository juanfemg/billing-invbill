package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.authentication.AppAuthenticationProvider;
import co.com.juan.invbill.authentication.AppUserDetails;
import co.com.juan.invbill.context.SessionContext;
import co.com.juan.invbill.delegate.businessdelegate.IUsuarioDelegate;
import co.com.juan.invbill.enums.SessionEnum;
import co.com.juan.invbill.exceptions.CredentialsException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author Juan Felipe
 */
@Named("login")
@ViewScoped
public class LoginView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_LoginUsuario";
    private static final Logger log = LoggerFactory.getLogger(LoginView.class);
    private final AppAuthenticationProvider authenticationProvider;
    private final IUsuarioDelegate usuarioDelegate;
    private final Properties properties = new Properties(FILE_MESSAGES);
    private final SessionContext sessionContext;
    private LoginApp loginApp;
    private String userId;
    private String password;

    @Inject
    public LoginView(AppAuthenticationProvider authenticationProvider, IUsuarioDelegate usuarioDelegate, SessionContext sessionContext) {
        this.authenticationProvider = authenticationProvider;
        this.usuarioDelegate = usuarioDelegate;
        this.sessionContext = sessionContext;
    }

    public String actionLogin() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Authentication request = new UsernamePasswordAuthenticationToken(userId, password);
            Authentication result = this.authenticationProvider.authenticate(request);

            if (result != null && result.getPrincipal() != null) {
                AppUserDetails userDetail = (AppUserDetails) result.getPrincipal();

                if (userDetail.isEnabled()) {
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(result);
                    this.saveOrUpdateLogin(result.getName());
                    session.setAttribute(SessionEnum.SPRING_SECURITY_CONTEXT.name(), securityContext);
                    this.sessionContext.setIdLoginApp(loginApp.getIdLoginApp());
                } else {
                    throw new CredentialsException(result.getName());
                }
            }
        } catch (EntityException e) {
            return "error";
        } catch (CredentialsException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_USER_NO_FOUND));
            log.error(ERROR_CREDENCIALES_INCORRECTAS, e.getMessage());
            return "error";
        }

        return "success";
    }

    private void saveOrUpdateLogin(String idLoginApp) {
        String idSession = RequestContextHolder.currentRequestAttributes().getSessionId();
        loginApp = this.usuarioDelegate.findLoginByID(idLoginApp);

        if (loginApp == null) {
            loginApp = this.buildLoginApp(idLoginApp, idSession);
            this.saveLogin(loginApp);
        } else {
            this.sessionContext.setUltimoLogin(loginApp.getUltimoLogin());
            this.updateLogin(this.buildLoginApp(loginApp.getIdLoginApp(), idSession));
        }
    }

    private LoginApp buildLoginApp(String idLoginApp, String idSession) {
        return LoginApp.builder()
                .idLoginApp(idLoginApp)
                .idSession(idSession)
                .build();
    }

    private void saveLogin(LoginApp loginApp) {
        try {
            this.usuarioDelegate.save(loginApp);
        } catch (EntityException e) {
            log.error(ERROR_CREACION_LOGIN, loginApp.getIdLoginApp(), e.getMessage());
            throw e;
        }
    }

    private void updateLogin(LoginApp loginApp) {
        try {
            this.usuarioDelegate.update(loginApp);
        } catch (EntityException e) {
            log.error(ERROR_ACTUALIZACION_LOGIN, loginApp.getIdLoginApp(), e.getMessage());
            throw e;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
