package co.com.juan.invbill.context;

import co.com.juan.invbill.model.AppConfig;
import org.springframework.security.core.context.SecurityContext;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Named
@SessionScoped
public class SessionContext implements Serializable {

    private String idLoginApp;
    private Date ultimoLogin;

    public String getIdLoginApp() {
        return idLoginApp;
    }

    public void setIdLoginApp(String idLoginApp) {
        this.idLoginApp = idLoginApp;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

}
