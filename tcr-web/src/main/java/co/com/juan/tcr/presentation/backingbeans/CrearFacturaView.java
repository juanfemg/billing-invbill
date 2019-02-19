package co.com.juan.tcr.presentation.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.tcr.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.tcr.enums.EstadosAppEnum;
import co.com.juan.tcr.model.CategoriaProducto;
import co.com.juan.tcr.model.FacturaCabecera;
import co.com.juan.tcr.model.FacturaDetalle;
import co.com.juan.tcr.model.FacturaDetalleId;
import co.com.juan.tcr.model.Producto;
import co.com.juan.tcr.model.StockProducto;
import co.com.juan.tcr.report.IReportController;
import co.com.juan.tcr.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "crearFactura")
@ViewScoped
public class CrearFacturaView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_CreacionFactura";
	private static final String REPORTE_FACTURA_VENTA = "reportFacturaVenta";
	private static final long serialVersionUID = 8071841544486569522L;
	private static final Logger log = LoggerFactory.getLogger(CrearFacturaView.class);
	private static final String ID_DIALOG_MESSAGES = "menMod";

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	@ManagedProperty(value = "#{ReportController}")
	private transient IReportController reportController;

	private FacturaCabecera facturaCabecera;
	private List<FacturaDetalle> facturaDetalle;
	private FacturaDetalle facturaModDetalle;
	private FacturaDetalle facturaDelDetalle;
	private FacturaDetalleId facturaDetalleId;
	private List<Producto> producto;
	private List<CategoriaProducto> categoria;
	private CategoriaProducto categoriaProducto;
	private List<SelectItem> categorias;
	private List<SelectItem> productos;
	private StockProducto stockProducto;
	private boolean showDialogAdicionarItem;
	private boolean showDialogConfirmacionActualizacion;
	private boolean showDialogResumenFactura;
	private boolean showInputFacturaTercero;
	private boolean showButtonImprimir;
	private String nitTercero;
	private String razonSocialTercero;
	private int cantidad;
	private double valorIva = 0D;
	private int metodoPago;
	private Integer cambio;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public CrearFacturaView() {
		super();
	}

	@PostConstruct
	public void init() {
		facturaCabecera = new FacturaCabecera();
		facturaDetalle = new ArrayList<>();
		facturaModDetalle = new FacturaDetalle();
		facturaDelDetalle = new FacturaDetalle();
		categoria = new ArrayList<>();
		categorias = new ArrayList<>();
		producto = new ArrayList<>();
		productos = new ArrayList<>();
		categoriaProducto = new CategoriaProducto();
		stockProducto = new StockProducto();
		facturaDetalleId = new FacturaDetalleId();
		showDialogAdicionarItem = true;
		showDialogConfirmacionActualizacion = false;
		showDialogResumenFactura = false;
		showInputFacturaTercero = false;
		showButtonImprimir = false;
		cantidad = 1;
		initCategoriasProducto();
	}

	public void initCategoriasProducto() {
		try {
			categoria = businessDelegate.getCategoriasProducto();

			for (CategoriaProducto categoriaProductoTemp : categoria) {
				if (categoriaProductoTemp.getEstado().equals(EstadosAppEnum.A))
					categorias.add(new SelectItem(categoriaProductoTemp, categoriaProductoTemp.getCategoria()));
			}

			if (categorias.isEmpty())
				addWarnMessage(properties.getParametroString("MSG_CATEGORIAS_INACTIVAS"));
			else {
				categoriaProducto = categoria.get(0);
				initProductos();
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Categorias de Productos : Fallo la consulta de las categorias de productos"
					+ ". ERROR : " + e.getMessage());
		}
	}

	public void initProductos() {
		try {
			producto = businessDelegate.getProductosByCategoriaProducto(categoriaProducto);

			productos.clear();

			for (Producto productoTemp : producto) {
				if (productoTemp.getEstado().equals(EstadosAppEnum.A))
					productos.add(new SelectItem(productoTemp, productoTemp.getProducto()));
			}

			if (productos.isEmpty())
				addWarnMessage(properties.getParametroString("MSG_PRODUCTOS_INACTIVOS"));
			else {
				facturaModDetalle.setProducto((Producto) productos.get(0).getValue());
				initStockProducto();
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Productos : Fallo la consulta de los productos" + ". ERROR : " + e.getMessage());
		}
	}

	public void initStockProducto() {
		try {
			stockProducto = businessDelegate.getStockProductoByProducto(facturaModDetalle.getProducto());

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Stock de Producto : Fallo la consulta del stock de producto" + ". ERROR : "
					+ e.getMessage());
		}
	}

	public void actionAdicionar() {
		showDialogAdicionarItem = true;
	}

	public void actionAdicionarItem() {
		for (FacturaDetalle facturaDetalleTemp : facturaDetalle) {
			if (facturaDetalleTemp.getProducto().getIdProducto()
					.compareTo(facturaModDetalle.getProducto().getIdProducto()) == 0) {
				showDialogConfirmacionActualizacion = true;
				facturaModDetalle = facturaDetalleTemp;
				break;
			}
		}

		if (!showDialogConfirmacionActualizacion) {
			try {
				stockProducto = businessDelegate.getStockProductoByProducto(facturaModDetalle.getProducto());

				if (stockProducto.getStock() < cantidad) {
					String[] parameters = { facturaModDetalle.getProducto().getProducto(),
							String.valueOf(stockProducto.getStock()) };
					addWarnMessage(properties.getParametroString("MSG_PRODUCTO_SIN_STOCK", parameters),
							ID_DIALOG_MESSAGES);
				} else {
					facturaModDetalle.setCantidad(cantidad);
					facturaModDetalle.setPrecioVenta(stockProducto.getPrecioVenta().doubleValue());
					facturaModDetalle.setValorIva(valorIva);
					facturaDetalle.add(facturaModDetalle);

					facturaModDetalle = new FacturaDetalle();
					cantidad = 1;
					showDialogAdicionarItem = false;
					actualizarFacturaResumen();
				}

			} catch (Exception e) {
				addErrorMessage(e.getMessage());
				log.error("=== Consulta de Stock de Producto : Fallo la consulta del stock de producto" + ". ERROR : "
						+ e.getMessage());
			}
		}
	}

	public void actionActualizarItem() {
		if (stockProducto.getStock() < cantidad) {
			String[] parameters = { facturaModDetalle.getProducto().getProducto(),
					String.valueOf(stockProducto.getStock()) };
			addWarnMessage(properties.getParametroString("MSG_PRODUCTO_SIN_STOCK", parameters));
			showDialogConfirmacionActualizacion = false;
		} else {
			facturaModDetalle.setCantidad(cantidad);

			facturaModDetalle = new FacturaDetalle();
			cantidad = 1;
			showDialogConfirmacionActualizacion = false;
			showDialogAdicionarItem = false;
			actualizarFacturaResumen();
		}
	}

	public void actionEliminar() {
		facturaDetalle.remove(facturaDelDetalle);
		actualizarFacturaResumen();
	}

	public void actionCancelar() {
		if (showDialogConfirmacionActualizacion && !showDialogResumenFactura)
			showDialogConfirmacionActualizacion = false;
		else
			showDialogAdicionarItem = false;

		if (showDialogResumenFactura)
			showDialogResumenFactura = false;
	}

	public void actualizarFacturaResumen() {
		Double valorTotal = 0D;
		HttpSession session = null;

		if (facturaCabecera.getUsuarioCreacion() == null) {
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			facturaCabecera.setUsuarioCreacion(session.getAttribute("LOGIN").toString());
		}

		for (FacturaDetalle facturaDetalleTemp : facturaDetalle) {
			valorTotal += facturaDetalleTemp.getCantidad() * facturaDetalleTemp.getPrecioVenta();
		}

		facturaCabecera.setValorIva(valorIva);
		facturaCabecera.setValorNeto(valorTotal * (1 - valorIva));
		facturaCabecera.setValorTotal(valorTotal.intValue());
	}

	public void actionGenerarFactura() {
		showDialogResumenFactura = true;
	}

	public void actionGuardar() {
		if (facturaCabecera.getValorTotal() > metodoPago) {
			addWarnMessage(properties.getParametroString("MSG_VALOR_RECIBIDO_INFERIOR"));
		} else {
			try {
				businessDelegate.save(facturaCabecera);
				log.info("=== Creacion de factura Cabecera : Factura creada " + facturaCabecera.getIdFactura()
						+ " exitosamente ===");

				saveFacturaDetalle();

				cambio = metodoPago - facturaCabecera.getValorTotal();

				getDefaultPrinter();
			} catch (Exception e) {
				addErrorMessage(e.getMessage());
				log.error("=== Creacion de factura Cabecera : Fallo la creacion de la factura "
						+ facturaCabecera.getIdFactura() + ". ERROR : " + e.getMessage());
			}
		}
	}

	public void getDefaultPrinter() {
		try {
			if (reportController.getDefaultPrinter())
				showButtonImprimir = true;
			else
				addWarnMessage(properties.getParametroString("MSG_IMPRESORA_NO_CONFIGURADA"));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_IMPRESORA_NO_CONFIGURADA"));
			log.error("=== Impresion de factura : Fallo la impresion de la factura" + ". ERROR : " + e.getMessage());
		}
	}

	public void saveFacturaDetalle() {
		for (FacturaDetalle facturaDetalleTemp : facturaDetalle) {
			try {
				facturaDetalleTemp.setFacturaCabecera(facturaCabecera);
				facturaDetalleId = new FacturaDetalleId(facturaCabecera.getIdFactura(),
						facturaDetalleTemp.getProducto().getIdProducto());
				facturaDetalleTemp.setId(facturaDetalleId);
				businessDelegate.save(facturaDetalleTemp);
				log.info("=== Creacion de factura Detalle : Factura creada " + "Factura: "
						+ facturaDetalleTemp.getFacturaCabecera().getIdFactura() + " Producto: "
						+ facturaDetalleTemp.getProducto().getIdProducto() + " exitosamente ===");

				getStockProducto(facturaDetalleTemp);
			} catch (Exception e) {
				addErrorMessage(e.getMessage());
				log.error("=== Creacion de factura Detalle : Fallo la creacion de la factura " + "Factura: "
						+ facturaDetalleTemp.getFacturaCabecera().getIdFactura() + " Producto: "
						+ facturaDetalleTemp.getProducto().getIdProducto() + ". ERROR : " + e.getMessage());
			}
		}
	}

	public void getStockProducto(FacturaDetalle facturaDetalleTemp) {
		try {
			stockProducto = businessDelegate.getStockProductoByProducto(facturaDetalleTemp.getProducto());

			updateStockProducto(facturaDetalleTemp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Stock de Producto : Fallo la consulta del stock de producto" + ". ERROR : "
					+ e.getMessage());
		}
	}

	public void updateStockProducto(FacturaDetalle facturaDetalleTemp) {
		try {
			stockProducto.setStock(stockProducto.getStock() - facturaDetalleTemp.getCantidad());
			businessDelegate.update(stockProducto);
			log.info("=== Actualizacion de stock de producto : Stock de producto actualizado "
					+ stockProducto.getIdStockProducto() + " exitosamente ===");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Actualizacion de Stock de Producto : Fallo la actualizacion del stock de producto"
					+ ". ERROR : " + e.getMessage());
		}
	}

	public void actionImprimir() {
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("NUMERO_FACTURA_VENTA", facturaCabecera.getIdFactura());
			parameters.put("EFECTIVO", metodoPago);
			parameters.put("CAMBIO", cambio);
			if (nitTercero == null) {
				parameters.put("NIT_TERCERO", nitTercero);
			} else {
				parameters.put("NIT_TERCERO", nitTercero.equals("") ? null : nitTercero);
			}
			parameters.put("RAZON_SOCIAL_TERCERO", razonSocialTercero);
			reportController.printReport(REPORTE_FACTURA_VENTA, parameters);
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_IMPRESION"));
			log.error("=== Impresion de factura : Fallo la impresion de la factura", e);
		}
	}

	public void actionFacturaNormal() {
		showInputFacturaTercero = false;
	}

	public void actionFacturaTercero() {
		showInputFacturaTercero = true;
	}

	public void actionLimpiar() {
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

	public void addWarnMessage(String summary, String clientId) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
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
	 * @return the reportController
	 */
	public IReportController getReportController() {
		return reportController;
	}

	/**
	 * @param reportController the reportController to set
	 */
	public void setReportController(IReportController reportController) {
		this.reportController = reportController;
	}

	/**
	 * @return the facturaCabecera
	 */
	public FacturaCabecera getFacturaCabecera() {
		return facturaCabecera;
	}

	/**
	 * @param facturaCabecera the facturaCabecera to set
	 */
	public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
		this.facturaCabecera = facturaCabecera;
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
	 * @return the producto
	 */
	public List<Producto> getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}

	/**
	 * @return the productos
	 */
	public List<SelectItem> getProductos() {
		return productos;
	}

	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<SelectItem> productos) {
		this.productos = productos;
	}

	/**
	 * @return the facturaDetalle
	 */
	public List<FacturaDetalle> getFacturaDetalle() {
		return facturaDetalle;
	}

	/**
	 * @param facturaDetalle the facturaDetalle to set
	 */
	public void setFacturaDetalle(List<FacturaDetalle> facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	/**
	 * @return the categoria
	 */
	public List<CategoriaProducto> getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(List<CategoriaProducto> categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the categorias
	 */
	public List<SelectItem> getCategorias() {
		return categorias;
	}

	/**
	 * @param categorias the categorias to set
	 */
	public void setCategorias(List<SelectItem> categorias) {
		this.categorias = categorias;
	}

	/**
	 * @return the categoriaProducto
	 */
	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	/**
	 * @param categoriaProducto the categoriaProducto to set
	 */
	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	/**
	 * @return the facturaModDetalle
	 */
	public FacturaDetalle getFacturaModDetalle() {
		return facturaModDetalle;
	}

	/**
	 * @param facturaModDetalle the facturaModDetalle to set
	 */
	public void setFacturaModDetalle(FacturaDetalle facturaModDetalle) {
		this.facturaModDetalle = facturaModDetalle;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the valorIva
	 */
	public double getValorIva() {
		return valorIva;
	}

	/**
	 * @param valorIva the valorIva to set
	 */
	public void setValorIva(double valorIva) {
		this.valorIva = valorIva;
	}

	/**
	 * @return the showDialogAdicionarItem
	 */
	public boolean isShowDialogAdicionarItem() {
		return showDialogAdicionarItem;
	}

	/**
	 * @param showDialogAdicionarItem the showDialogAdicionarItem to set
	 */
	public void setShowDialogAdicionarItem(boolean showDialogAdicionarItem) {
		this.showDialogAdicionarItem = showDialogAdicionarItem;
	}

	/**
	 * @return the facturaDelDetalle
	 */
	public FacturaDetalle getFacturaDelDetalle() {
		return facturaDelDetalle;
	}

	/**
	 * @param facturaDelDetalle the facturaDelDetalle to set
	 */
	public void setFacturaDelDetalle(FacturaDetalle facturaDelDetalle) {
		this.facturaDelDetalle = facturaDelDetalle;
	}

	/**
	 * @return the showDialogConfirmacionActualizacion
	 */
	public boolean isShowDialogConfirmacionActualizacion() {
		return showDialogConfirmacionActualizacion;
	}

	/**
	 * @param showDialogConfirmacionActualizacion the
	 *                                            showDialogConfirmacionActualizacion
	 *                                            to set
	 */
	public void setShowDialogConfirmacionActualizacion(boolean showDialogConfirmacionActualizacion) {
		this.showDialogConfirmacionActualizacion = showDialogConfirmacionActualizacion;
	}

	/**
	 * @return the showDialogResumenFactura
	 */
	public boolean isShowDialogResumenFactura() {
		return showDialogResumenFactura;
	}

	/**
	 * @param showDialogResumenFactura the showDialogResumenFactura to set
	 */
	public void setShowDialogResumenFactura(boolean showDialogResumenFactura) {
		this.showDialogResumenFactura = showDialogResumenFactura;
	}

	/**
	 * @return the metodoPago
	 */
	public int getMetodoPago() {
		return metodoPago;
	}

	/**
	 * @param metodoPago the metodoPago to set
	 */
	public void setMetodoPago(int metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * @return the cambio
	 */
	public Integer getCambio() {
		return cambio;
	}

	/**
	 * @param cambio the cambio to set
	 */
	public void setCambio(Integer cambio) {
		this.cambio = cambio;
	}

	/**
	 * @return the showInputFacturaTercero
	 */
	public boolean isShowInputFacturaTercero() {
		return showInputFacturaTercero;
	}

	/**
	 * @param showInputFacturaTercero the showInputFacturaTercero to set
	 */
	public void setShowInputFacturaTercero(boolean showInputFacturaTercero) {
		this.showInputFacturaTercero = showInputFacturaTercero;
	}

	/**
	 * @return the nitTercero
	 */
	public String getNitTercero() {
		return nitTercero;
	}

	/**
	 * @param nitTercero the nitTercero to set
	 */
	public void setNitTercero(String nitTercero) {
		this.nitTercero = nitTercero;
	}

	/**
	 * @return the razonSocialTercero
	 */
	public String getRazonSocialTercero() {
		return razonSocialTercero;
	}

	/**
	 * @param razonSocialTercero the razonSocialTercero to set
	 */
	public void setRazonSocialTercero(String razonSocialTercero) {
		this.razonSocialTercero = razonSocialTercero;
	}

	/**
	 * @return the stockProducto
	 */
	public StockProducto getStockProducto() {
		return stockProducto;
	}

	/**
	 * @param stockProducto the stockProducto to set
	 */
	public void setStockProducto(StockProducto stockProducto) {
		this.stockProducto = stockProducto;
	}

	/**
	 * @return the facturaDetalleId
	 */
	public FacturaDetalleId getFacturaDetalleId() {
		return facturaDetalleId;
	}

	/**
	 * @param facturaDetalleId the facturaDetalleId to set
	 */
	public void setFacturaDetalleId(FacturaDetalleId facturaDetalleId) {
		this.facturaDetalleId = facturaDetalleId;
	}

	/**
	 * @return the showButtonImprimir
	 */
	public boolean isShowButtonImprimir() {
		return showButtonImprimir;
	}

	/**
	 * @param showButtonImprimir the showButtonImprimir to set
	 */
	public void setShowButtonImprimir(boolean showButtonImprimir) {
		this.showButtonImprimir = showButtonImprimir;
	}

}