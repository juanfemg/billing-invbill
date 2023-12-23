package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IProveedorDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ProveedorApp;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Juan Felipe
 */
@ManagedBean(name = "consultarProveedor")
@ViewScoped
public class ConsultarProveedorView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_ModificacionProveedor";
    private static final Logger log = LoggerFactory.getLogger(ConsultarProveedorView.class);
    private static final String ID_DIALOG_MESSAGES = "menMod";
    private final Properties properties = new Properties(FILE_MESSAGES);
    @ManagedProperty(value = "#{proveedorDelegate}")
    private IProveedorDelegate proveedorDelegate;
    private ProveedorApp proveedorModApp;
    private List<SelectItem> estadosApp;
    private List<ProveedorApp> proveedoresApp;
    private boolean showDialogModificarProveedor;

    public ConsultarProveedorView() {
        super();
    }

    @PostConstruct
    public void init() {
        proveedorModApp = new ProveedorApp();
        estadosApp = new ArrayList<>();
        proveedoresApp = new ArrayList<>();
        showDialogModificarProveedor = false;
        this.initEstadosApp();
        this.initProveedores();
    }

    private void initEstadosApp() {
        Arrays.stream(StatusEnum.values())
                .forEach(statusEnum -> estadosApp.add(new SelectItem(statusEnum, statusEnum.getStatus())));
    }

    private void initProveedores() {
        try {
            proveedoresApp = this.proveedorDelegate.getProveedores();
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_PROVEEDORES));
            log.error(ERROR_CONSULTA_PROVEEDORES, e);
        }
    }

    public void actionEditar() {
        showDialogModificarProveedor = true;
    }

    public void actionModificar() {
        try {
            this.proveedorDelegate.update(proveedorModApp);
            addInfoMessage(this.properties.getParameterByKey(MSG_PROVEEDOR_ACTUALIZADO));
            showDialogModificarProveedor = false;
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_ACTUALIZACION_PROVEEDOR), ID_DIALOG_MESSAGES);
            log.error(ERROR_ACTUALIZACION_PROVEEDOR, proveedorModApp.getIdProveedorApp(), e.getMessage());
        }
    }

    public void actionCancelar() {
        showDialogModificarProveedor = false;
    }

    public ProveedorApp getProveedorModApp() {
        return proveedorModApp;
    }

    public void setProveedorModApp(ProveedorApp proveedorModApp) {
        this.proveedorModApp = proveedorModApp;
    }

    public List<SelectItem> getEstadosApp() {
        return estadosApp;
    }

    public void setEstadosApp(List<SelectItem> estadosApp) {
        this.estadosApp = estadosApp;
    }

    public List<ProveedorApp> getProveedoresApp() {
        return proveedoresApp;
    }

    public void setProveedoresApp(List<ProveedorApp> proveedoresApp) {
        this.proveedoresApp = proveedoresApp;
    }

    public boolean isShowDialogModificarProveedor() {
        return showDialogModificarProveedor;
    }

    public void setShowDialogModificarProveedor(boolean showDialogModificarProveedor) {
        this.showDialogModificarProveedor = showDialogModificarProveedor;
    }

    public IProveedorDelegate getProveedorDelegate() {
        return proveedorDelegate;
    }

    public void setProveedorDelegate(IProveedorDelegate proveedorDelegate) {
        this.proveedorDelegate = proveedorDelegate;
    }
}
