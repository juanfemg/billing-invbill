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
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Juan Felipe
 */
@ManagedBean(name = "consultarProducto")
@ViewScoped
public class ConsultarProductoView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_ModificacionProducto";
    private static final Logger log = LoggerFactory.getLogger(ConsultarProductoView.class);
    private final Properties properties = new Properties(FILE_MESSAGES);
    @ManagedProperty(value = "#{productoDelegate}")
    private IProductoDelegate productoDelegate;
    @ManagedProperty(value = "#{configDelegate}")
    private IConfigDelegate configDelegate;
    private Producto productoMod;
    private List<SelectItem> estadosApp;
    private List<Producto> productos;
    private List<CategoriaProducto> categoriasProducto;
    private List<SelectItem> categorias;
    private List<TipoUnidadMedida> tiposUnidadMedida;
    private List<SelectItem> unidades;
    private boolean showDialogModificarProducto;

    public ConsultarProductoView() {
        super();
    }

    @PostConstruct
    public void init() {
        productoMod = new Producto();
        estadosApp = new ArrayList<>();
        categorias = new ArrayList<>();
        productos = new ArrayList<>();
        categoriasProducto = new ArrayList<>();
        tiposUnidadMedida = new ArrayList<>();
        unidades = new ArrayList<>();
        showDialogModificarProducto = false;
        this.initEstadosApp();
        this.initProductos();
        this.initCategoriasProducto();
        this.initTiposUnidadMedida();
    }

    private void initEstadosApp() {
        Arrays.stream(StatusEnum.values())
                .forEach(statusEnum -> estadosApp.add(new SelectItem(statusEnum, statusEnum.getStatus())));
    }

    private void initProductos() {
        try {
            productos = this.productoDelegate.getProductos();
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_PRODUCTOS));
            log.error(ERROR_CONSULTA_PRODUCTOS, e);
        }
    }

    private void initCategoriasProducto() {
        try {
            categoriasProducto = this.productoDelegate.getCategoriasProductoSortByCategoria();
            categoriasProducto.stream()
                    .filter(categoriaProducto -> StatusEnum.A.equals(categoriaProducto.getEstado()))
                    .forEach(categoriaProducto -> categorias.add(new SelectItem(categoriaProducto, categoriaProducto.getCategoria())));

            if (categorias.isEmpty()) {
                addWarnMessage(this.properties.getParameterByKey(MSG_MODIFICACION_CATEGORIAS_INACTIVAS));
            }
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_CATEGORIAS));
            log.error(ERROR_CONSULTA_CATEGORIAS, e);
        }
    }

    private void initTiposUnidadMedida() {
        try {
            tiposUnidadMedida = this.configDelegate.getTiposUnidadMedida();
            tiposUnidadMedida.stream()
                    .filter(tipoUnidadMedida -> StatusEnum.A.equals(tipoUnidadMedida.getEstado()))
                    .forEach(tipoUnidadMedida -> unidades.add(new SelectItem(tipoUnidadMedida, tipoUnidadMedida.getUnidad())));

            if (unidades.isEmpty()) {
                addWarnMessage(this.properties.getParameterByKey(MSG_MODIFICACION_UNIDADES_MEDIDA_INACTIVAS));
            }
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_UNIDADES_MEDIDA));
            log.error(ERROR_CONSULTA_UNIDADES_MEDIDA, e);
        }
    }

    public void actionEditar() {
        showDialogModificarProducto = true;
    }

    public void actionModificar() {
        try {
            this.productoDelegate.update(productoMod);
            addInfoMessage(this.properties.getParameterByKey(MSG_PRODUCTO_ACTUALIZADO));
            showDialogModificarProducto = false;
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_ACTUALIZACION_PRODUCTO), ID_DIALOG_MESSAGES);
            log.error(ERROR_ACTUALIZACION_PRODUCTO, productoMod.getIdProducto(), e.getMessage());
        }
    }

    public void actionCancelar() {
        showDialogModificarProducto = false;
    }

    public Producto getProductoMod() {
        return productoMod;
    }

    public void setProductoMod(Producto productoMod) {
        this.productoMod = productoMod;
    }

    public List<SelectItem> getEstadosApp() {
        return estadosApp;
    }

    public void setEstadosApp(List<SelectItem> estadosApp) {
        this.estadosApp = estadosApp;
    }

    public boolean isShowDialogModificarProducto() {
        return showDialogModificarProducto;
    }

    public void setShowDialogModificarProducto(boolean showDialogModificarProducto) {
        this.showDialogModificarProducto = showDialogModificarProducto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
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
