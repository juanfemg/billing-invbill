/**
 *
 */
package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IProveedorDelegate;
import co.com.juan.invbill.model.ProveedorApp;
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
@Named("crearProveedor")
@ViewScoped
public class CrearProveedorView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_CreacionProveedor";
    private static final Logger log = LoggerFactory.getLogger(CrearProveedorView.class);
    private final Properties properties = new Properties(FILE_MESSAGES);
    private final IProveedorDelegate proveedorDelegate;
    private ProveedorApp proveedorApp;

    @Inject
    public CrearProveedorView(IProveedorDelegate proveedorDelegate) {
        this.proveedorDelegate = proveedorDelegate;
    }

    @PostConstruct
    public void init() {
        proveedorApp = new ProveedorApp();
    }

    public void actionGuardar() {
        try {
            this.proveedorDelegate.save(proveedorApp);
            addInfoMessage(this.properties.getParameterByKey(MSG_PROVEEDOR_CREADO));
            proveedorApp = new ProveedorApp();
        } catch (Exception e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CREACION_PROVEEDOR));
            log.error(ERROR_CREACION_PROVEEDOR, proveedorApp.getRazonSocial(), e.getMessage());
        }
    }

    public void actionLimpiar() {
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
    }

    public ProveedorApp getProveedorApp() {
        return proveedorApp;
    }

    public void setProveedorApp(ProveedorApp proveedorApp) {
        this.proveedorApp = proveedorApp;
    }

}
