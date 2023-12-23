package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IUsuarioDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Juan Felipe
 */
@Named("crearUsuario")
@ViewScoped
public class CrearUsuarioView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_CreacionUsuario";
    private static final Logger log = LoggerFactory.getLogger(CrearUsuarioView.class);
    private final IUsuarioDelegate usuarioDelegate;
    private final Properties properties = new Properties(FILE_MESSAGES);
    private UsuarioApp usuarioApp;

    @Inject
    public CrearUsuarioView(IUsuarioDelegate usuarioDelegate) {
        this.usuarioDelegate = usuarioDelegate;
    }

    @PostConstruct
    public void init() {
        usuarioApp = new UsuarioApp();
    }

    public void actionGuardar() {
        try {
            this.usuarioDelegate.save(usuarioApp);
            addInfoMessage(this.properties.getParameterByKey(MSG_USUARIO_CREADO));
            usuarioApp = new UsuarioApp();
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CREACION_USUARIO));
            log.error(ERROR_CREACION_USUARIO, usuarioApp.getIdUsuarioApp(), e.getMessage());
        }
    }

    public void actionLimpiar() {
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
    }

    public UsuarioApp getUsuarioApp() {
        return usuarioApp;
    }

    public void setUsuarioApp(UsuarioApp usuarioApp) {
        this.usuarioApp = usuarioApp;
    }

}
