package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IUsuarioDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Named("consultarUsuario")
@RequestScoped
public class ConsultarUsuarioView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_ModificacionUsuario";
    private static final Logger log = LoggerFactory.getLogger(ConsultarUsuarioView.class);
    private final Properties properties = new Properties(FILE_MESSAGES);
    private final IUsuarioDelegate usuarioDelegate;
    private UsuarioApp usuarioApp;
    private UsuarioApp usuarioModApp;
    private List<SelectItem> estadosApp;
    private boolean showPanelModificarUsuario;

    @Inject
    public ConsultarUsuarioView(IUsuarioDelegate usuarioDelegate) {
        this.usuarioDelegate = usuarioDelegate;
    }

    @PostConstruct
    public void init() {
        usuarioApp = new UsuarioApp();
        usuarioModApp = new UsuarioApp();
        estadosApp = new ArrayList<>();
        showPanelModificarUsuario = false;
        this.initEstadosApp();
    }

    private void initEstadosApp() {
        Arrays.stream(StatusEnum.values())
                .forEach(statusEnum -> estadosApp.add(new SelectItem(statusEnum, statusEnum.getStatus())));
    }

    public void actionConsultar() {
        try {
            usuarioModApp = this.usuarioDelegate.findUsuarioByID(usuarioApp.getIdUsuarioApp());

            if (usuarioModApp != null) {
                showPanelModificarUsuario = true;
            } else {
                addWarnMessage(this.properties.getParameterByKey(MSG_DATA_NO_FOUND));
            }
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_USUARIO));
            log.error(ERROR_CONSULTA_USUARIO, usuarioApp.getIdUsuarioApp(), e.getMessage());
        }
    }

    public void actionModificar() {
        try {
            this.usuarioDelegate.update(usuarioModApp);
            addInfoMessage(this.properties.getParameterByKey(MSG_USUARIO_ACTUALIZADO));
            showPanelModificarUsuario = false;
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_ACTUALIZACION_USUARIO));
            log.error(ERROR_ACTUALIZACION_USUARIO, usuarioModApp.getIdUsuarioApp(), e.getMessage());
        }
    }

    public void actionLimpiar() {
        showPanelModificarUsuario = false;
    }

    public UsuarioApp getUsuarioApp() {
        return usuarioApp;
    }

    public void setUsuarioApp(UsuarioApp usuarioApp) {
        this.usuarioApp = usuarioApp;
    }

    public List<SelectItem> getEstadosApp() {
        return estadosApp;
    }

    public void setEstadosApp(List<SelectItem> estadosApp) {
        this.estadosApp = estadosApp;
    }

    public UsuarioApp getUsuarioModApp() {
        return usuarioModApp;
    }

    public void setUsuarioModApp(UsuarioApp usuarioModApp) {
        this.usuarioModApp = usuarioModApp;
    }

    public boolean isShowPanelModificarUsuario() {
        return showPanelModificarUsuario;
    }

    public void setShowPanelModificarUsuario(boolean showPanelModificarUsuario) {
        this.showPanelModificarUsuario = showPanelModificarUsuario;
    }
}
