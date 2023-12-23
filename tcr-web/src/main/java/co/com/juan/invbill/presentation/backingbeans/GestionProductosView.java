package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.context.ApplicationContext;
import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.delegate.businessdelegate.ICompraDelegate;
import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.enums.ParameterEnum;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Felipe
 */
@ManagedBean(name = "gestionProductos")
@ViewScoped
public class GestionProductosView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_GestionProductos";
    private static final Logger log = LoggerFactory.getLogger(GestionProductosView.class);
    private final Properties properties = new Properties(FILE_MESSAGES);
    @ManagedProperty(value = "#{clienteDelegate}")
    private IClienteDelegate clienteDelegate;
    @ManagedProperty(value = "#{productoDelegate}")
    private IProductoDelegate productoDelegate;
    @ManagedProperty(value = "#{compraDelegate}")
    private ICompraDelegate compraDelegate;
    @ManagedProperty(value = "#{applicationContext}")
    private ApplicationContext applicationContext;
    private StockProducto stockProductoMod;
    private List<StockProducto> stockProductos;
    private List<CompraDetalle> compraDetalles;
    private boolean showDialogModificarStockProducto;
    private AppConfig topeStock;

    public GestionProductosView() {
        super();
    }

    @PostConstruct
    public void init() {
        stockProductoMod = new StockProducto();
        stockProductos = new ArrayList<>();
        compraDetalles = new ArrayList<>();
        showDialogModificarStockProducto = false;
        topeStock = new AppConfig();
        this.initStockProductos();
        this.initTopeStockProductos();
    }

    public void initStockProductos() {
        try {
            stockProductos = this.productoDelegate.getStockProductos();
        } catch (Exception e) {
            addErrorMessage(properties.getParameterByKey(MSG_ERROR_STOCK_PRODUCTOS));
            log.error(ERROR_STOCK_PRODUCTOS, e);
        }
    }

    public void initTopeStockProductos() {
        topeStock = this.applicationContext.getAppConfigs().stream()
                .filter(appConfig -> appConfig.getIdAppConfig().equalsIgnoreCase(ParameterEnum.TOPE_STOCK.name()))
                .findFirst()
                .orElse(null);
    }

    public void actionEditar() {
        showDialogModificarStockProducto = true;
    }

    public void actionConsultarHistoricoCompra(StockProducto stockProductoCurrent) {
        try {
            compraDetalles = this.compraDelegate.findCompraDetalleByProducto(stockProductoCurrent.getProducto());
        } catch (Exception e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_HISTORICO_COMPRA));
            log.error(ERROR_HISTORICO_COMPRA, e);
        }
    }

    public void actionModificar() {
        try {
            this.productoDelegate.update(stockProductoMod);
            showDialogModificarStockProducto = false;
            addInfoMessage(this.properties.getParameterByKey(MSG_STOCK_PRODUCTO_ACTUALIZADO));
        } catch (Exception e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_ACTUALIZACION_STOCK_PRODUCTO), ID_DIALOG_MESSAGES);
            log.error(ERROR_ACTUALIZACION_STOCK_PRODUCTO, stockProductoMod.getIdStockProducto(), e.getMessage());
        }
    }

    public void actionCancelar() {
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
    }

    public boolean isShowDialogModificarStockProducto() {
        return showDialogModificarStockProducto;
    }

    public void setShowDialogModificarStockProducto(boolean showDialogModificarStockProducto) {
        this.showDialogModificarStockProducto = showDialogModificarStockProducto;
    }

    public StockProducto getStockProductoMod() {
        return stockProductoMod;
    }

    public void setStockProductoMod(StockProducto stockProductoMod) {
        this.stockProductoMod = stockProductoMod;
    }

    public List<StockProducto> getStockProductos() {
        return stockProductos;
    }

    public void setStockProductos(List<StockProducto> stockProductos) {
        this.stockProductos = stockProductos;
    }

    public List<CompraDetalle> getCompraDetalles() {
        return compraDetalles;
    }

    public void setCompraDetalles(List<CompraDetalle> compraDetalles) {
        this.compraDetalles = compraDetalles;
    }

    public AppConfig getTopeStock() {
        return topeStock;
    }

    public void setTopeStock(AppConfig topeStock) {
        this.topeStock = topeStock;
    }

    public IClienteDelegate getClienteDelegate() {
        return clienteDelegate;
    }

    public void setClienteDelegate(IClienteDelegate clienteDelegate) {
        this.clienteDelegate = clienteDelegate;
    }

    public IProductoDelegate getProductoDelegate() {
        return productoDelegate;
    }

    public void setProductoDelegate(IProductoDelegate productoDelegate) {
        this.productoDelegate = productoDelegate;
    }

    public ICompraDelegate getCompraDelegate() {
        return compraDelegate;
    }

    public void setCompraDelegate(ICompraDelegate compraDelegate) {
        this.compraDelegate = compraDelegate;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
