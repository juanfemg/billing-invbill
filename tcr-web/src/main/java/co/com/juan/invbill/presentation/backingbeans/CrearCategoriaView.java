package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
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
@Named("crearCategoria")
@ViewScoped
public class CrearCategoriaView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_CreacionCategoria";
    private static final Logger log = LoggerFactory.getLogger(CrearCategoriaView.class);
    private final IProductoDelegate productoDelegate;
    private final Properties properties = new Properties(FILE_MESSAGES);
    private CategoriaProducto categoriaProducto;

    @Inject
    public CrearCategoriaView(IProductoDelegate productoDelegate) {
        this.productoDelegate = productoDelegate;
    }

    @PostConstruct
    public void init() {
        categoriaProducto = new CategoriaProducto();
    }

    public void actionGuardar() {
        try {
            this.productoDelegate.save(categoriaProducto);
            addInfoMessage(this.properties.getParameterByKey(MSG_CATEGORIA_CREADA));
            categoriaProducto = new CategoriaProducto();
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CREACION_CATEGORIA));
            log.error(ERROR_CREACION_CATEGORIA, categoriaProducto.getCategoria(), e.getMessage());
        }
    }

    public void actionLimpiar() {
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

}
