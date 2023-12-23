package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
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
@ManagedBean(name = "consultarCategoria")
@ViewScoped
public class ConsultarCategoriaView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_ModificacionCategoria";
    private static final Logger log = LoggerFactory.getLogger(ConsultarCategoriaView.class);
    private final Properties properties = new Properties(FILE_MESSAGES);
    @ManagedProperty(value = "#{productoDelegate}")
    private IProductoDelegate productoDelegate;
    private CategoriaProducto categoriaModProducto;
    private List<SelectItem> estadosApp;
    private List<CategoriaProducto> categoriasProducto;
    private boolean showDialogModificarCategoria;

    public ConsultarCategoriaView() {
        super();
    }

    @PostConstruct
    public void init() {
        categoriaModProducto = new CategoriaProducto();
        estadosApp = new ArrayList<>();
        categoriasProducto = new ArrayList<>();
        showDialogModificarCategoria = false;
        this.initEstadosApp();
        this.initCategoriasProducto();
    }

    private void initEstadosApp() {
        Arrays.stream(StatusEnum.values())
                .forEach(statusEnum -> estadosApp.add(new SelectItem(statusEnum, statusEnum.getStatus())));
    }

    private void initCategoriasProducto() {
        try {
            categoriasProducto = this.productoDelegate.getCategoriasProductoSortByCategoria();
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_CATEGORIAS));
            log.error(ERROR_CONSULTA_CATEGORIAS, e);
        }
    }

    public void actionEditar() {
        showDialogModificarCategoria = true;
    }

    public void actionModificar() {
        try {
            this.productoDelegate.update(categoriaModProducto);
            addInfoMessage(this.properties.getParameterByKey(MSG_CATEGORIA_ACTUALIZADA));
            showDialogModificarCategoria = false;
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_ACTUALIZACION_CATEGORIA), ID_DIALOG_MESSAGES);
            log.error(ERROR_ACTUALIZACION_CATEGORIA, categoriaModProducto.getIdCategoria(), e.getMessage());
        }
    }

    public void actionCancelar() {
        showDialogModificarCategoria = false;
    }

    public CategoriaProducto getCategoriaModProducto() {
        return categoriaModProducto;
    }

    public void setCategoriaModProducto(CategoriaProducto categoriaModProducto) {
        this.categoriaModProducto = categoriaModProducto;
    }

    public List<SelectItem> getEstadosApp() {
        return estadosApp;
    }

    public void setEstadosApp(List<SelectItem> estadosApp) {
        this.estadosApp = estadosApp;
    }

    public List<CategoriaProducto> getCategoriasProducto() {
        return categoriasProducto;
    }

    public void setCategoriasProducto(List<CategoriaProducto> categoriasProducto) {
        this.categoriasProducto = categoriasProducto;
    }

    public boolean isShowDialogModificarCategoria() {
        return showDialogModificarCategoria;
    }

    public void setShowDialogModificarCategoria(boolean showDialogModificarCategoria) {
        this.showDialogModificarCategoria = showDialogModificarCategoria;
    }

    public IProductoDelegate getProductoDelegate() {
        return productoDelegate;
    }

    public void setProductoDelegate(IProductoDelegate productoDelegate) {
        this.productoDelegate = productoDelegate;
    }
}
