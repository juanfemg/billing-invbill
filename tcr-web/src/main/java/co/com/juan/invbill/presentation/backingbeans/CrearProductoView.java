package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.TipoUnidadMedida;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Felipe
 */
@ManagedBean(name = "crearProducto")
@ViewScoped
public class CrearProductoView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_CreacionProducto";
    private static final Logger log = LoggerFactory.getLogger(CrearProductoView.class);
    private final Properties properties = new Properties(FILE_MESSAGES);
    @ManagedProperty(value = "#{productoDelegate}")
    private IProductoDelegate productoDelegate;
    @ManagedProperty(value = "#{configDelegate}")
    private IConfigDelegate configDelegate;
    private Producto producto;
    private List<SelectItem> categorias;
    private List<SelectItem> unidades;
    private List<CategoriaProducto> categoriasProducto;
    private List<TipoUnidadMedida> tiposUnidadMedida;

    public CrearProductoView() {
        super();
    }

    @PostConstruct
    public void init() {
        producto = new Producto();
        categorias = new ArrayList<>();
        unidades = new ArrayList<>();
        categoriasProducto = new ArrayList<>();
        tiposUnidadMedida = new ArrayList<>();
        this.initCategoriasProducto();
        this.initTiposUnidadMedida();
    }

    public void initCategoriasProducto() {
        try {
            categoriasProducto = this.productoDelegate.getCategoriasProductoSortByCategoria();
            categoriasProducto.stream()
                    .filter(categoriaProducto -> StatusEnum.A.equals(categoriaProducto.getEstado()))
                    .forEach(categoriaProducto -> categorias.add(new SelectItem(categoriaProducto, categoriaProducto.getCategoria())));

            if (categorias.isEmpty()) {
                addWarnMessage(this.properties.getParameterByKey(MSG_CATEGORIAS_INACTIVAS));
            }
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_CATEGORIAS));
            log.error(ERROR_CONSULTA_CATEGORIAS, e);
        }
    }

    public void initTiposUnidadMedida() {
        try {
            tiposUnidadMedida = this.configDelegate.getTiposUnidadMedida();
            tiposUnidadMedida.stream()
                    .filter(tipoUnidadMedida -> StatusEnum.A.equals(tipoUnidadMedida.getEstado()))
                    .forEach(tipoUnidadMedida -> unidades.add(new SelectItem(tipoUnidadMedida, tipoUnidadMedida.getUnidad())));

            if (unidades.isEmpty()) {
                addWarnMessage(this.properties.getParameterByKey(MSG_UNIDADES_MEDIDA_INACTIVAS));
            }
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_UNIDADES_MEDIDA));
            log.error(ERROR_CONSULTA_UNIDADES_MEDIDA, e);
        }
    }

    public void actionGuardar() {
        try {
            this.productoDelegate.save(producto);
            addInfoMessage(this.properties.getParameterByKey(MSG_PRODUCTO_CREADO));
            producto = new Producto();
        } catch (Exception e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CREACION_PRODUCTO));
            log.error(ERROR_CREACION_PRODUCTO, producto.getProducto(), e.getMessage());
        }
    }

    public void actionLimpiar() {
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

    public List<SelectItem> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<SelectItem> unidades) {
        this.unidades = unidades;
    }

    public IProductoDelegate getProductoDelegate() {
        return productoDelegate;
    }

    public void setProductoDelegate(IProductoDelegate productoDelegate) {
        this.productoDelegate = productoDelegate;
    }

    public IConfigDelegate getConfigDelegate() {
        return configDelegate;
    }

    public void setConfigDelegate(IConfigDelegate configDelegate) {
        this.configDelegate = configDelegate;
    }
}
