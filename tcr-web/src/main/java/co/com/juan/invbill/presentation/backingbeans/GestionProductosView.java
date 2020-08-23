package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.util.ParameterApp;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "gestionProductos")
@ViewScoped
public class GestionProductosView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_GestionProductos";
	private static final long serialVersionUID = 5778879622123315873L;
	private static final Logger log = LoggerFactory.getLogger(GestionProductosView.class);
	private static final String ID_DIALOG_MESSAGES = "menMod";

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private StockProducto stockProductoMod;
	private List<StockProducto> stockProductos;
	private List<CompraDetalle> compraDetalles;
	private boolean showDialogModificarStockProducto;
	private AppConfig topeStock;
	private transient Properties properties = new Properties(FILE_MESSAGES);

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
		initStockProductos();
		initTopeStockProductos();
	}

	public void initStockProductos() {
		try {
			stockProductos = businessDelegate.getStockProductos();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_STOCK_PRODUCTOS"));
			log.error("=== Consulta de Stock Productos: Fallo la consulta del stock de los productos", e);
		}
	}

	public void initTopeStockProductos() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		topeStock = (AppConfig) session.getAttribute(ParameterApp.TOPE_STOCK.toString());
	}

	public void actionEditar() {
		showDialogModificarStockProducto = true;
	}

	public void actionConsultarHistoricoCompra(StockProducto stockProductoCurrent) {
		try {
			compraDetalles = businessDelegate.findCompraDetalleByProducto(stockProductoCurrent.getProducto());
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_HISTORICO_COMPRA"));
			log.error(
					"=== Consulta de Historico Compra Detalle: Fallo la consulta del detalle de la compra del producto {}. Se ha producido un error: {}",
					stockProductoCurrent.getProducto().getIdProducto(), e.getMessage());
		}
	}

	public void actionModificar() {
		try {
			businessDelegate.update(stockProductoMod);
			showDialogModificarStockProducto = false;
			log.info("=== Actualizacion de stock producto: Stock Producto actualizado. Id={}, precioVenta={} ",
					stockProductoMod.getIdStockProducto(), stockProductoMod.getPrecioVenta());
			addInfoMessage(properties.getParametroString("MSG_STOCK_PRODUCTO_ACTUALIZADO"));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_ACTUALIZACION_STOCK_PRODUCTO"),
					ID_DIALOG_MESSAGES);
			log.error(
					"=== Actualizacion de stock producto: Fallo la actualizacion del stock producto {}. Se ha producido un error: {}",
					stockProductoMod.getIdStockProducto(), e.getMessage());
		}
	}

	public void actionCancelar() {
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
	}

	public void addInfoMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addWarnMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addErrorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addErrorMessage(String summary, String clientId) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
	}

	/**
	 * @return the businessDelegate
	 */
	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate the businessDelegate to set
	 */
	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the showDialogModificarStockProducto
	 */
	public boolean isShowDialogModificarStockProducto() {
		return showDialogModificarStockProducto;
	}

	/**
	 * @param showDialogModificarStockProducto the showDialogModificarStockProducto
	 *                                         to set
	 */
	public void setShowDialogModificarStockProducto(boolean showDialogModificarStockProducto) {
		this.showDialogModificarStockProducto = showDialogModificarStockProducto;
	}

	/**
	 * @return the stockProductoMod
	 */
	public StockProducto getStockProductoMod() {
		return stockProductoMod;
	}

	/**
	 * @param stockProductoMod the stockProductoMod to set
	 */
	public void setStockProductoMod(StockProducto stockProductoMod) {
		this.stockProductoMod = stockProductoMod;
	}

	/**
	 * @return the stockProductos
	 */
	public List<StockProducto> getStockProductos() {
		return stockProductos;
	}

	/**
	 * @param stockProductos the stockProductos to set
	 */
	public void setStockProductos(List<StockProducto> stockProductos) {
		this.stockProductos = stockProductos;
	}

	/**
	 * @return the compraDetalles
	 */
	public List<CompraDetalle> getCompraDetalles() {
		return compraDetalles;
	}

	/**
	 * @param compraDetalles the compraDetalles to set
	 */
	public void setCompraDetalles(List<CompraDetalle> compraDetalles) {
		this.compraDetalles = compraDetalles;
	}

	/**
	 * @return the topeStock
	 */
	public AppConfig getTopeStock() {
		return topeStock;
	}

	/**
	 * @param topeStock the topeStock to set
	 */
	public void setTopeStock(AppConfig topeStock) {
		this.topeStock = topeStock;
	}

}
