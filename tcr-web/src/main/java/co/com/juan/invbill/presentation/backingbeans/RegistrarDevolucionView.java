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

import co.com.juan.invbill.delegate.businessdelegate.IFacturaDelegate;
import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.enums.SessionEnum;
import co.com.juan.invbill.model.DevolucionCabecera;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import co.com.juan.invbill.model.FacturaCabecera;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "registrarDevolucion")
@ViewScoped
public class RegistrarDevolucionView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_RegistroDevolucion";
	private static final long serialVersionUID = 1475142644023643893L;
	private static final Logger log = LoggerFactory.getLogger(RegistrarDevolucionView.class);

	@ManagedProperty(value = "#{clienteDelegate}")
	private transient IClienteDelegate clienteDelegate;

	public IFacturaDelegate getFacturaDelegate() {
		return facturaDelegate;
	}

	public void setFacturaDelegate(IFacturaDelegate facturaDelegate) {
		this.facturaDelegate = facturaDelegate;
	}

	@ManagedProperty(value = "#{facturaDelegate}")
	private transient IFacturaDelegate facturaDelegate;

	public IProductoDelegate getProductoDelegate() {
		return productoDelegate;
	}

	public void setProductoDelegate(IProductoDelegate productoDelegate) {
		this.productoDelegate = productoDelegate;
	}

	@ManagedProperty(value = "#{productoDelegate}")
	private transient IProductoDelegate productoDelegate;

	private FacturaCabecera facturaCabecera;
	private DevolucionCabecera devolucionCabecera;
	private DevolucionCabecera devolucionCabeceraResumen;
	private List<FacturaDetalle> facturaDetalles;
	private List<FacturaDetalle> facturaDevDetalles;
	private FacturaDetalle facturaDetalle;
	private FacturaDetalle facturaDevDetalle;
	private FacturaDetalle facturaDelDetalle;
	private List<DevolucionDetalle> devolucionDetalles;
	private DevolucionDetalle devolucionDetalle;
	private DevolucionDetalle devolucionDetalleRegistrada;
	private StockProducto stockProducto;
	private DevolucionDetalleId devolucionDetalleId;
	private boolean showPanelRegistrarDevolucion;
	private boolean showDialogDevolverProducto;
	private boolean showPanelDetalleDevoluciones;
	private boolean showDialogConfirmacionActualizacion;
	private boolean showDialogRegistroDevolucion;
	private boolean existDevolucionFactura;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public RegistrarDevolucionView() {
		super();
	}

	@PostConstruct
	public void init() {
		facturaCabecera = new FacturaCabecera();
		devolucionCabecera = new DevolucionCabecera();
		devolucionCabeceraResumen = new DevolucionCabecera();
		facturaDetalles = new ArrayList<>();
		devolucionDetalles = new ArrayList<>();
		facturaDevDetalles = new ArrayList<>();
		devolucionDetalle = new DevolucionDetalle();
		devolucionDetalleRegistrada = new DevolucionDetalle();
		facturaDetalle = new FacturaDetalle();
		facturaDevDetalle = new FacturaDetalle();
		facturaDelDetalle = new FacturaDetalle();
		devolucionDetalleId = new DevolucionDetalleId();
		stockProducto = new StockProducto();
		showPanelRegistrarDevolucion = false;
		showDialogDevolverProducto = false;
		showPanelDetalleDevoluciones = false;
		showDialogConfirmacionActualizacion = false;
		showDialogRegistroDevolucion = false;
		existDevolucionFactura = false;
	}

	public void actionConsultar() {
		try {
			facturaDetalles = this.facturaDelegate.getFacturaDetalleDevolucionByIdFactura(facturaCabecera.getIdFactura());

			if (!facturaDetalles.isEmpty()) {
				findDevolucionCabeceraByID();
			} else {
				addWarnMessage(properties.getParameterByKey("MSG_DATA_NO_FOUND"));
			}

			devolucionDetalles.clear();
			facturaDevDetalles.clear();

			showPanelDetalleDevoluciones = false;

			log.info("=== Consulta de factura: Factura consultada {}", facturaCabecera.getIdFactura());
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_FACTURA"));
			log.error("=== Consulta de facturas: Fallo la consulta de la factura {}. Se ha producido un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
		}
	}

	public void findDevolucionCabeceraByID() {
		try {
			devolucionCabecera = this.facturaDelegate.findDevolucionCabeceraByID(facturaCabecera.getIdFactura());

			log.info("=== Consulta de devolucion : Devolucion consultada asociada a la factura {}",
					facturaCabecera.getIdFactura());
			if (devolucionCabecera != null) {
				existDevolucionFactura = true;
				addWarnMessage(properties.getParameterByKey("MSG_FACTURA_PRESENTA_DEVOLUCION"));
			} else {
				devolucionCabecera = new DevolucionCabecera();
				existDevolucionFactura = false;
			}
			showPanelRegistrarDevolucion = true;
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_DEVOLUCION"));
			log.error(
					"=== Consulta de devolucion : Fallo la consulta de la devolucion asociada a la factura {}. Se ha producido un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
		}
	}

	public void actionDevolver() {
		showDialogDevolverProducto = true;
	}

	public void actionCancelar() {
		if (showDialogDevolverProducto) {
			showDialogDevolverProducto = false;
		}

		if (showDialogConfirmacionActualizacion) {
			showDialogConfirmacionActualizacion = false;
		}

		if (showDialogRegistroDevolucion) {
			showDialogRegistroDevolucion = false;
		}
	}

	public void actionDevolverProducto() {
		for (FacturaDetalle facturaDetalleTemp : facturaDevDetalles) {
			if (facturaDetalleTemp.getProducto().getIdProducto()
					.compareTo(facturaDevDetalle.getProducto().getIdProducto()) == 0) {
				showDialogConfirmacionActualizacion = true;
				actualizarDetalleFactura();
				break;
			}
		}

		if (!showDialogConfirmacionActualizacion) {
			facturaDevDetalles.add(facturaDevDetalle);

			facturaDevDetalle = new FacturaDetalle();

			showDialogDevolverProducto = false;
			showPanelDetalleDevoluciones = true;

			actualizarDetalleFactura();
			actualizarDevolucionResumen();
		}
	}

	public void actualizarDetalleFactura() {
		try {
			facturaDetalles = this.facturaDelegate.getFacturaDetalleDevolucionByIdFactura(facturaCabecera.getIdFactura());

			log.info("=== Consulta de factura: Factura consultada {}", facturaCabecera.getIdFactura());
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_FACTURA"));
			log.error("=== Consulta de facturas: Fallo la consulta de la factura {}. Se ha producido un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
		}
	}

	public void actionActualizarItem() {
		for (FacturaDetalle facturaDetalleTemp : facturaDevDetalles) {
			if (facturaDetalleTemp.getProducto().getIdProducto()
					.compareTo(facturaDevDetalle.getProducto().getIdProducto()) == 0) {
				facturaDetalleTemp.setCantidad(facturaDevDetalle.getCantidad());
				showDialogConfirmacionActualizacion = false;
				showDialogDevolverProducto = false;
				break;
			}
		}

		actualizarDevolucionResumen();
	}

	public void actionEliminar() {
		facturaDevDetalles.remove(facturaDelDetalle);
		actualizarDevolucionResumen();
	}

	public void actualizarDevolucionResumen() {
		Double valorTotal;
		Double valorNeto = 0D;
		Double valorIva = 0D;
		HttpSession session = null;

		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		for (FacturaDetalle facturaDetalleTemp : facturaDevDetalles) {
			valorNeto += facturaDetalleTemp.getCantidad() * facturaDetalleTemp.getPrecioVenta();
			valorIva += facturaDetalleTemp.getValorIva();
		}

		valorTotal = valorNeto + valorIva;

		devolucionCabeceraResumen.setValorIva(valorIva);
		devolucionCabeceraResumen.setValorNeto(valorNeto);
		devolucionCabeceraResumen.setValorTotal(valorTotal.intValue());

		devolucionCabecera.setUsuarioCreacion(session.getAttribute(SessionEnum.LOGIN.name()).toString());
		devolucionCabecera.setIdFactura(facturaCabecera.getIdFactura());
	}

	public void actionRegistrarDevolucion() {
		showDialogRegistroDevolucion = true;
	}

	public void actionGuardar() {
		try {
			devolucionCabecera.setValorIva((devolucionCabecera.getValorIva() != null)
					? (devolucionCabecera.getValorIva() + devolucionCabeceraResumen.getValorIva())
					: devolucionCabeceraResumen.getValorIva());
			devolucionCabecera.setValorNeto((devolucionCabecera.getValorNeto() != null)
					? (devolucionCabecera.getValorNeto() + devolucionCabeceraResumen.getValorNeto())
					: devolucionCabeceraResumen.getValorNeto());
			devolucionCabecera.setValorTotal((devolucionCabecera.getValorTotal() != null)
					? (devolucionCabecera.getValorTotal() + devolucionCabeceraResumen.getValorTotal())
					: devolucionCabeceraResumen.getValorTotal());

			if (existDevolucionFactura) {
				this.facturaDelegate.update(devolucionCabecera);
				log.info("=== Actualizacion de devolucion Cabecera: Devolucion actualizada asociada a la factura {} ",
						devolucionCabecera.getIdFactura());
			} else {
				this.facturaDelegate.save(devolucionCabecera);
				log.info("=== Creacion de devolucion Cabecera: Devolucion creada asociada a la factura {} ",
						devolucionCabecera.getIdFactura());
			}

			saveDevolucionDetalle();

			FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();

			addInfoMessage(properties.getParameterByKey("MSG_DEVOLUCION_CREADA"));

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_DEVOLUCION"));
			log.error(
					"=== Creacion y/o actualizacion de devolucion Cabecera: Fallo la creacion de la devolucion asociada a la factura {}. Se ha producido un error: {}",
					devolucionCabecera.getIdFactura(), e.getMessage());
		}

	}

	public void saveDevolucionDetalle() {
		for (FacturaDetalle facturaDetalleTemp : facturaDevDetalles) {
			try {
				devolucionDetalleRegistrada = this.facturaDelegate.findDevolucionDetalleByID(
						facturaDetalleTemp.getFacturaCabecera().getIdFactura(),
						facturaDetalleTemp.getProducto().getIdProducto());

				if (devolucionDetalleRegistrada != null) {
					devolucionDetalleRegistrada
							.setCantidad(devolucionDetalleRegistrada.getCantidad() + facturaDetalleTemp.getCantidad());
					this.facturaDelegate.update(devolucionDetalleRegistrada);
					log.info(
							"=== Actualizacion de devolucion Detalle : Devolucion actualizada. IdFactura={}, idProducto={} ",
							facturaDetalleTemp.getFacturaCabecera().getIdFactura(),
							facturaDetalleTemp.getProducto().getIdProducto());

					devolucionDetalleRegistrada.setCantidad(facturaDetalleTemp.getCantidad());
					getStockProducto(devolucionDetalleRegistrada);
				} else {
					devolucionDetalle.setDevolucionCabecera(devolucionCabecera);
					devolucionDetalle.setCantidad(facturaDetalleTemp.getCantidad());
					devolucionDetalle.setPrecioVenta(facturaDetalleTemp.getPrecioVenta());
					devolucionDetalle.setProducto(facturaDetalleTemp.getProducto());
					devolucionDetalle.setValorIva(facturaDetalleTemp.getValorIva());
					devolucionDetalleId = new DevolucionDetalleId(devolucionCabecera.getIdFactura(),
							devolucionDetalle.getProducto().getIdProducto());
					devolucionDetalle.setId(devolucionDetalleId);
					this.facturaDelegate.save(devolucionDetalle);
					log.info("=== Creacion de devolucion Detalle : Devolucion creada. IdFactura={}, idProducto={} ",
							facturaDetalleTemp.getFacturaCabecera().getIdFactura(),
							facturaDetalleTemp.getProducto().getIdProducto());

					getStockProducto(devolucionDetalle);
				}
			} catch (Exception e) {
				addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_DEVOLUCION_DETALLE"));
				log.error(
						"=== Creacion y/o actualizacion de devolucion Detalle: Fallo la creacion o actualizacion de la devolucion asociada a la factura {} y al producto {}. Se ha producido un error: {}",
						devolucionDetalle.getDevolucionCabecera().getIdFactura(),
						devolucionDetalle.getProducto().getIdProducto(), e.getMessage());
			}
		}
	}

	public void getStockProducto(DevolucionDetalle devolucionDetalle) {
		try {
			stockProducto = this.productoDelegate.getStockProductoByProducto(devolucionDetalle.getProducto());

			updateStockProducto(devolucionDetalle);
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKeyAndName("MSG_ERROR_CONSULTA_STOCK_PRODUCTO",
					devolucionDetalle.getProducto().getProducto()));
			log.error(
					"=== Consulta de Stock de Producto: Fallo la consulta del stock de producto {}. Se ha producido un error: {}",
					devolucionDetalle.getProducto().getIdProducto(), e.getMessage());
		}
	}

	public void updateStockProducto(DevolucionDetalle devolucionDetalle) {
		try {
			stockProducto.setStock(stockProducto.getStock() + devolucionDetalle.getCantidad());

			this.productoDelegate.update(stockProducto);
			log.info("=== Actualizacion de stock de producto: Stock de producto actualizado. IdProducto={}, valor={} ",
					stockProducto.getProducto().getIdProducto(), stockProducto.getStock());
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKeyAndName("MSG_ERROR_ACTUALIZACION_STOCK_PRODUCTO",
					stockProducto.getProducto().getProducto()));
			log.error(
					"=== Actualizacion de Stock de Producto: Fallo la actualizacion del stock de producto {}. Se ha producido un error: {}",
					stockProducto.getIdStockProducto(), e.getMessage());
		}
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

	/**
	 * @return the businessDelegate
	 */
	public IClienteDelegate getClienteDelegate() {
		return clienteDelegate;
	}

	/**
	 * 
	 */
	public void setClienteDelegate(IClienteDelegate clienteDelegate) {
		this.clienteDelegate = clienteDelegate;
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
	 * @return the facturaDetalles
	 */
	public List<FacturaDetalle> getFacturaDetalles() {
		return facturaDetalles;
	}

	/**
	 * @param facturaDetalles the facturaDetalles to set
	 */
	public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {
		this.facturaDetalles = facturaDetalles;
	}

	/**
	 * @return the showPanelRegistrarDevolucion
	 */
	public boolean isShowPanelRegistrarDevolucion() {
		return showPanelRegistrarDevolucion;
	}

	/**
	 * @param showPanelRegistrarDevolucion the showPanelRegistrarDevolucion to set
	 */
	public void setShowPanelRegistrarDevolucion(boolean showPanelRegistrarDevolucion) {
		this.showPanelRegistrarDevolucion = showPanelRegistrarDevolucion;
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
	 * @return the devolucionDetalles
	 */
	public List<DevolucionDetalle> getDevolucionDetalles() {
		return devolucionDetalles;
	}

	/**
	 * @param devolucionDetalles the devolucionDetalles to set
	 */
	public void setDevolucionDetalles(List<DevolucionDetalle> devolucionDetalles) {
		this.devolucionDetalles = devolucionDetalles;
	}

	/**
	 * @return the showDialogDevolverProducto
	 */
	public boolean isShowDialogDevolverProducto() {
		return showDialogDevolverProducto;
	}

	/**
	 * @param showDialogDevolverProducto the showDialogDevolverProducto to set
	 */
	public void setShowDialogDevolverProducto(boolean showDialogDevolverProducto) {
		this.showDialogDevolverProducto = showDialogDevolverProducto;
	}

	/**
	 * @return the devolucionDetalle
	 */
	public DevolucionDetalle getDevolucionDetalle() {
		return devolucionDetalle;
	}

	/**
	 * @param devolucionDetalle the devolucionDetalle to set
	 */
	public void setDevolucionDetalle(DevolucionDetalle devolucionDetalle) {
		this.devolucionDetalle = devolucionDetalle;
	}

	/**
	 * @return the facturaDetalle
	 */
	public FacturaDetalle getFacturaDetalle() {
		return facturaDetalle;
	}

	/**
	 * @param facturaDetalle the facturaDetalle to set
	 */
	public void setFacturaDetalle(FacturaDetalle facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	/**
	 * @return the facturaDevDetalle
	 */
	public FacturaDetalle getFacturaDevDetalle() {
		return facturaDevDetalle;
	}

	/**
	 * @param facturaDevDetalle the facturaDevDetalle to set
	 */
	public void setFacturaDevDetalle(FacturaDetalle facturaDevDetalle) {
		this.facturaDevDetalle = facturaDevDetalle;
	}

	/**
	 * @return the showPanelDetalleDevoluciones
	 */
	public boolean isShowPanelDetalleDevoluciones() {
		return showPanelDetalleDevoluciones;
	}

	/**
	 * @param showPanelDetalleDevoluciones the showPanelDetalleDevoluciones to set
	 */
	public void setShowPanelDetalleDevoluciones(boolean showPanelDetalleDevoluciones) {
		this.showPanelDetalleDevoluciones = showPanelDetalleDevoluciones;
	}

	/**
	 * @return the facturaDevDetalles
	 */
	public List<FacturaDetalle> getFacturaDevDetalles() {
		return facturaDevDetalles;
	}

	/**
	 * @param facturaDevDetalles the facturaDevDetalles to set
	 */
	public void setFacturaDevDetalles(List<FacturaDetalle> facturaDevDetalles) {
		this.facturaDevDetalles = facturaDevDetalles;
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
	 * @return the devolucionCabecera
	 */
	public DevolucionCabecera getDevolucionCabecera() {
		return devolucionCabecera;
	}

	/**
	 * @param devolucionCabecera the devolucionCabecera to set
	 */
	public void setDevolucionCabecera(DevolucionCabecera devolucionCabecera) {
		this.devolucionCabecera = devolucionCabecera;
	}

	/**
	 * @return the showDialogRegistroDevolucion
	 */
	public boolean isShowDialogRegistroDevolucion() {
		return showDialogRegistroDevolucion;
	}

	/**
	 * @param showDialogRegistroDevolucion the showDialogRegistroDevolucion to set
	 */
	public void setShowDialogRegistroDevolucion(boolean showDialogRegistroDevolucion) {
		this.showDialogRegistroDevolucion = showDialogRegistroDevolucion;
	}

	/**
	 * @return the devolucionDetalleId
	 */
	public DevolucionDetalleId getDevolucionDetalleId() {
		return devolucionDetalleId;
	}

	/**
	 * @param devolucionDetalleId the devolucionDetalleId to set
	 */
	public void setDevolucionDetalleId(DevolucionDetalleId devolucionDetalleId) {
		this.devolucionDetalleId = devolucionDetalleId;
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
	 * @return the devolucionCabeceraResumen
	 */
	public DevolucionCabecera getDevolucionCabeceraResumen() {
		return devolucionCabeceraResumen;
	}

	/**
	 * @param devolucionCabeceraResumen the devolucionCabeceraResumen to set
	 */
	public void setDevolucionCabeceraResumen(DevolucionCabecera devolucionCabeceraResumen) {
		this.devolucionCabeceraResumen = devolucionCabeceraResumen;
	}

	/**
	 * @return the existDevolucionFactura
	 */
	public boolean isExistDevolucionFactura() {
		return existDevolucionFactura;
	}

	/**
	 * @param existDevolucionFactura the existDevolucionFactura to set
	 */
	public void setExistDevolucionFactura(boolean existDevolucionFactura) {
		this.existDevolucionFactura = existDevolucionFactura;
	}

	/**
	 * @return the devolucionDetalleRegistrada
	 */
	public DevolucionDetalle getDevolucionDetalleRegistrada() {
		return devolucionDetalleRegistrada;
	}

	/**
	 * @param devolucionDetalleRegistrada the devolucionDetalleRegistrada to set
	 */
	public void setDevolucionDetalleRegistrada(DevolucionDetalle devolucionDetalleRegistrada) {
		this.devolucionDetalleRegistrada = devolucionDetalleRegistrada;
	}

}
