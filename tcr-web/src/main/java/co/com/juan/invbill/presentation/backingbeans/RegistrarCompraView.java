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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.enums.SessionEnum;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.ProveedorApp;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.enums.ParameterEnum;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */

@ManagedBean(name = "registrarCompra")
@ViewScoped
public class RegistrarCompraView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_RegistroCompra";
	private static final long serialVersionUID = -6989714907754566718L;
	private static final Logger log = LoggerFactory.getLogger(RegistrarCompraView.class);
	private static final String ID_DIALOG_MESSAGES_PROVEEDOR = "menProveedor";
	private static final String ID_DIALOG_MESSAGES_CATEGORIA = "menCategorias";
	private static final String ID_DIALOG_MESSAGES_PRODUCTO = "menProductos";

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	public IProductoDelegate getProductoDelegate() {
		return productoDelegate;
	}

	public void setProductoDelegate(IProductoDelegate productoDelegate) {
		this.productoDelegate = productoDelegate;
	}

	@ManagedProperty(value = "#{productoDelegate}")
	private transient IProductoDelegate productoDelegate;

	private transient HttpSession session;
	private List<CategoriaProducto> categoria;
	private CompraCabecera compraCabecera;
	private List<SelectItem> categorias;
	private CategoriaProducto categoriaProducto;
	private List<Producto> producto;
	private List<SelectItem> productos;
	private CompraDetalle compraModDetalle;
	private CompraDetalle compraDelDetalle;
	private List<CompraDetalle> compraDetalle;
	private ProveedorApp proveedorApp;
	private CompraCabeceraId compraCabeceraId;
	private CompraDetalleId compraDetalleId;
	private StockProducto stockProducto;
	private boolean showDialogProveedor;
	private boolean showDialogAdicionarItem;
	private boolean showDialogRegistroCompra;
	private boolean showDialogConfirmacionActualizacion;
	private int cantidad;
	private Double valorIva;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public RegistrarCompraView() {
		super();
	}

	@PostConstruct
	public void init() {
		categoria = new ArrayList<>();
		categorias = new ArrayList<>();
		categoriaProducto = new CategoriaProducto();
		producto = new ArrayList<>();
		productos = new ArrayList<>();
		compraModDetalle = new CompraDetalle();
		compraDetalle = new ArrayList<>();
		compraDetalleId = new CompraDetalleId();
		compraDelDetalle = new CompraDetalle();
		compraCabecera = new CompraCabecera();
		proveedorApp = new ProveedorApp();
		compraCabeceraId = new CompraCabeceraId();
		stockProducto = new StockProducto();
		showDialogProveedor = true;
		showDialogAdicionarItem = false;
		showDialogConfirmacionActualizacion = false;
		cantidad = 1;
		initCategoriasProducto();
	}

	public void initParametrosConfiguracion() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		AppConfig appConfig = (AppConfig) session.getAttribute(ParameterEnum.IVA.name());

		if (appConfig.getValor() == null) {
			addErrorMessage(properties.getParameterByKey("MSG_IVA_NO_PARAMETRIZADO"));
			showDialogAdicionarItem = false;
			showDialogProveedor = false;
		} else {
			try {
				valorIva = Double.parseDouble(appConfig.getValor());

				initCategoriasProducto();
			} catch (NumberFormatException nfe) {
				addErrorMessage(properties.getParameterByKey("MSG_IVA_NO_PARAMETRIZADO"));
				showDialogAdicionarItem = false;
				showDialogProveedor = false;
				log.error("=== Consulta de parametros de configuracion: Fallo la consulta del iva", nfe);
			}
		}
	}

	public void initCategoriasProducto() {
		try {
			categoria = this.productoDelegate.getCategoriasProductoSortByCategoria();

			categorias.clear();

			for (CategoriaProducto categoriaProductoTemp : categoria) {
				if (categoriaProductoTemp.getEstado().equals(StatusEnum.A)) {
					categorias.add(new SelectItem(categoriaProductoTemp, categoriaProductoTemp.getCategoria()));
				}
			}

			if (categorias.isEmpty()) {
				addWarnMessage(properties.getParameterByKey("MSG_CATEGORIAS_INACTIVAS"), ID_DIALOG_MESSAGES_CATEGORIA);
			} else {
				categoriaProducto = categoria.get(0);
				initProductos();
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_CATEGORIAS"));
			log.error("=== Consulta de Categorias de Productos: Fallo la consulta de las categorias de productos", e);
		}
	}

	public void initProductos() {
		try {
			producto = this.productoDelegate.getProductosByCategoriaProductoSortByProducto(categoriaProducto);

			productos.clear();

			for (Producto productoTemp : producto) {
				if (productoTemp.getEstado().equals(StatusEnum.A)) {
					productos.add(new SelectItem(productoTemp, productoTemp.getProducto()));
				}
			}

			if (productos.isEmpty()) {
				addWarnMessage(properties.getParameterByKey("MSG_PRODUCTOS_INACTIVOS"), ID_DIALOG_MESSAGES_PRODUCTO);
			} else {
				compraModDetalle.setProducto((Producto) productos.get(0).getValue());
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_PRODUCTOS"));
			log.error("=== Consulta de Productos: Fallo la consulta de los productos", e);
		}
	}

	public List<ProveedorApp> completeProveedorApp(String proveedorApp) {
		List<ProveedorApp> allProveedoresApp = businessDelegate.getProveedores();
		List<ProveedorApp> filteredProveedoresApp = new ArrayList<ProveedorApp>();

		for (ProveedorApp proveedorAppTemp : allProveedoresApp) {
			if (proveedorAppTemp.getRazonSocial().toLowerCase().contains(proveedorApp.toLowerCase())) {
				filteredProveedoresApp.add(proveedorAppTemp);
			}
		}

		return filteredProveedoresApp;
	}

	public void actionAdicionarProveedor() {
		showDialogProveedor = true;
	}

	public void actionIngresarProveedor() {
		try {
			if (proveedorApp == null) {
				addWarnMessage(properties.getParameterByKey("MSG_PROVEEDOR_NO_EXISTE"));
			} else {
				if (proveedorApp.getEstado().equals(StatusEnum.I)) {
					addWarnMessage(properties.getParameterByKey("MSG_PROVEEDOR_INACTIVO"));
				} else {
					compraCabeceraId.setIdProveedorApp(proveedorApp.getIdProveedorApp());
					findCompraCabeceraByID();
				}
			}
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_PROVEEDOR"),
					ID_DIALOG_MESSAGES_PROVEEDOR);
			log.error("=== Consulta de Proovedor: Fallo la consulta del proveedor {}. Se ha producido un error: {}",
					proveedorApp.getIdProveedorApp(), e.getMessage());
		}

	}

	public void findCompraCabeceraByID() {
		CompraCabecera compraCabeceraTemp = null;
		try {
			compraCabeceraTemp = businessDelegate.findCompraCabeceraByID(compraCabeceraId);

			if (compraCabeceraTemp != null) {
				String[] parameters = { proveedorApp.getRazonSocial() };
				addWarnMessage(properties.getParameterByKeyAndNameArray("MSG_FACTURA_EXISTE", parameters));
			} else {
				showDialogAdicionarItem = true;
				initParametrosConfiguracion();
				compraCabecera.setProveedorApp(proveedorApp);
			}
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_BY_FACTURA_PROVEEDOR"));
			log.error(
					"=== Consulta de compra: Fallo la consulta de la compra con factura {} y proveedor {}. Se ha producido un error: {}",
					compraCabeceraId.getIdFacturaCompra(), compraCabeceraId.getIdProveedorApp(), e.getMessage());
		}
	}

	public void actionAdicionar() {
		showDialogAdicionarItem = true;
	}

	public void actionAdicionarItem() {
		Double valorIvaTemp = 0D;

		for (CompraDetalle compraDetalleTemp : compraDetalle) {
			if (compraDetalleTemp.getProducto().getIdProducto()
					.compareTo(compraModDetalle.getProducto().getIdProducto()) == 0) {
				showDialogConfirmacionActualizacion = true;
				compraModDetalle = compraDetalleTemp;
				break;
			}
		}

		if (!showDialogConfirmacionActualizacion) {
			if (compraModDetalle.getDiscriminarIva()) {
				valorIvaTemp = compraModDetalle.getPrecioCompra() * ((1 + (this.valorIva / 100)) - 1);
			}

			compraModDetalle.setValorIva((double) Math.round(valorIvaTemp * 100) / 100);
			compraModDetalle.setPrecioCompra((double) Math.round(compraModDetalle.getPrecioCompra() * 100) / 100);
			compraModDetalle.setCantidad(cantidad);
			compraDetalle.add(compraModDetalle);

			compraModDetalle = new CompraDetalle();
			cantidad = 1;
			showDialogAdicionarItem = false;
			actualizarCompraResumen();
		}

		if (proveedorApp != null) {
			compraCabecera.setId(compraCabeceraId);
			showDialogProveedor = false;
		}
	}

	public void actionRegistrarCompra() {
		showDialogRegistroCompra = true;
	}

	public void actionActualizarItem() {
		compraModDetalle.setCantidad(cantidad);

		compraModDetalle = new CompraDetalle();
		cantidad = 1;
		showDialogConfirmacionActualizacion = false;
		showDialogAdicionarItem = false;
		actualizarCompraResumen();
	}

	public void actionEliminar() {
		compraDetalle.remove(compraDelDetalle);
		if (compraDetalle.isEmpty()) {
			compraCabecera = new CompraCabecera();
		}
		actualizarCompraResumen();
	}

	public void actionCancelar() {
		if (showDialogConfirmacionActualizacion && !showDialogRegistroCompra) {
			showDialogConfirmacionActualizacion = false;
		} else {
			showDialogAdicionarItem = false;
		}

		if (showDialogRegistroCompra) {
			showDialogRegistroCompra = false;
		}

		if (showDialogProveedor) {
			showDialogProveedor = false;
		}
	}

	public void actualizarCompraResumen() {
		Double valorNeto = 0D;
		Double valorIvaTemp = 0D;
		Double valorTotal;

		if (compraCabecera.getUsuarioCreacion() == null) {
			compraCabecera.setUsuarioCreacion(session.getAttribute(SessionEnum.LOGIN.name()).toString());
		}

		for (CompraDetalle compraDetalleTemp : compraDetalle) {
			valorNeto += compraDetalleTemp.getCantidad() * compraDetalleTemp.getPrecioCompra();
			valorIvaTemp += compraDetalleTemp.getCantidad() * compraDetalleTemp.getValorIva();
		}

		valorTotal = valorNeto + valorIvaTemp;

		compraCabecera.setValorIva((double) Math.round(valorIvaTemp * 100) / 100);
		compraCabecera.setValorNeto((double) Math.round(valorNeto * 100) / 100);
		compraCabecera.setValorTotal((int) Math.round(valorTotal));
	}

	public void actionGuardar() {
		try {
			businessDelegate.save(compraCabecera);
			log.info("=== Creacion de compra Cabecera: Compra creada. IdProveedor={}, idFactura={} ",
					compraCabecera.getId().getIdProveedorApp(), compraCabecera.getId().getIdFacturaCompra());

			saveCompraDetalle();

			FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();

			addInfoMessage(properties.getParameterByKey("MSG_COMPRA_CREADA"));
			compraDetalle.clear();

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_COMPRA"));
			log.error(
					"=== Creacion de compra Cabecera: Fallo la creacion de la compra asociada al proveedor {} con factura {}. Se ha producido un error: {}",
					compraCabecera.getId().getIdProveedorApp(), compraCabecera.getId().getIdFacturaCompra(),
					e.getMessage());
		}
	}

	public void saveCompraDetalle() {
		for (CompraDetalle compraDetalleTemp : compraDetalle) {
			try {
				compraDetalleId = new CompraDetalleId(compraCabecera.getId().getIdProveedorApp(),
						compraCabecera.getId().getIdFacturaCompra(), compraDetalleTemp.getProducto().getIdProducto());
				compraDetalleTemp.setCompraCabecera(compraCabecera);
				compraDetalleTemp.setId(compraDetalleId);

				businessDelegate.save(compraDetalleTemp);
				log.info("=== Creacion de compra Detalle: Compra creada. IdProveedor={}, idFactura={}, idProducto={} ",
						compraDetalleTemp.getId().getIdProveedorApp(), compraDetalleTemp.getId().getIdFacturaCompra(),
						compraDetalleTemp.getId().getIdProducto());

				getStockProducto(compraDetalleTemp);
			} catch (Exception e) {
				addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_COMPRA_DETALLE"));
				log.error(
						"=== Creacion de compra Detalle: Fallo la creacion del detalle de la compra. IdProveedor={}, idFactura={}, idProducto={}. Se ha producido un error: {}",
						compraDetalleTemp.getId().getIdProveedorApp(), compraDetalleTemp.getId().getIdFacturaCompra(),
						compraDetalleTemp.getId().getIdProducto(), e.getMessage());
			}
		}
	}

	public void getStockProducto(CompraDetalle compraDetalleTemp) {
		try {
			stockProducto = this.productoDelegate.getStockProductoByProducto(compraDetalleTemp.getProducto());

			updateStockProducto(compraDetalleTemp);
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKeyAndName("MSG_ERROR_CONSULTA_STOCK_PRODUCTO",
					compraDetalleTemp.getProducto().getProducto()));
			log.error(
					"=== Consulta de Stock de Producto: Fallo la consulta del stock de producto {}. Se ha producido un error: {}",
					compraDetalleTemp.getProducto().getIdProducto(), e.getMessage());
		}
	}

	public void updateStockProducto(CompraDetalle compraDetalleTemp) {
		try {
			Double precioCompra;

			stockProducto.setStock(stockProducto.getStock() + compraDetalleTemp.getCantidad());

			precioCompra = compraDetalleTemp.getPrecioCompra() + compraDetalleTemp.getValorIva();

			stockProducto.setPrecioCompra(precioCompra.intValue());
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

	public void addErrorMessage(String summary, String clientId) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
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
	 * @return the compraModDetalle
	 */
	public CompraDetalle getCompraModDetalle() {
		return compraModDetalle;
	}

	/**
	 * @param compraModDetalle the compraModDetalle to set
	 */
	public void setCompraModDetalle(CompraDetalle compraModDetalle) {
		this.compraModDetalle = compraModDetalle;
	}

	/**
	 * @return the compraDetalle
	 */
	public List<CompraDetalle> getCompraDetalle() {
		return compraDetalle;
	}

	/**
	 * @param compraDetalle the compraDetalle to set
	 */
	public void setCompraDetalle(List<CompraDetalle> compraDetalle) {
		this.compraDetalle = compraDetalle;
	}

	/**
	 * @return the compraDelDetalle
	 */
	public CompraDetalle getCompraDelDetalle() {
		return compraDelDetalle;
	}

	/**
	 * @param compraDelDetalle the compraDelDetalle to set
	 */
	public void setCompraDelDetalle(CompraDetalle compraDelDetalle) {
		this.compraDelDetalle = compraDelDetalle;
	}

	/**
	 * @return the compraCabecera
	 */
	public CompraCabecera getCompraCabecera() {
		return compraCabecera;
	}

	/**
	 * @param compraCabecera the compraCabecera to set
	 */
	public void setCompraCabecera(CompraCabecera compraCabecera) {
		this.compraCabecera = compraCabecera;
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
	 * @return the showDialogRegistroCompra
	 */
	public boolean isShowDialogRegistroCompra() {
		return showDialogRegistroCompra;
	}

	/**
	 * @param showDialogRegistroCompra the showDialogRegistroCompra to set
	 */
	public void setShowDialogRegistroCompra(boolean showDialogRegistroCompra) {
		this.showDialogRegistroCompra = showDialogRegistroCompra;
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
	public Double getValorIva() {
		return valorIva;
	}

	/**
	 * @param valorIva the valorIva to set
	 */
	public void setValorIva(Double valorIva) {
		this.valorIva = valorIva;
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
	 * @return the showDialogProveedor
	 */
	public boolean isShowDialogProveedor() {
		return showDialogProveedor;
	}

	/**
	 * @param showDialogProveedor the showDialogProveedor to set
	 */
	public void setShowDialogProveedor(boolean showDialogProveedor) {
		this.showDialogProveedor = showDialogProveedor;
	}

	/**
	 * @return the proveedorApp
	 */
	public ProveedorApp getProveedorApp() {
		return proveedorApp;
	}

	/**
	 * @param proveedorApp the proveedorApp to set
	 */
	public void setProveedorApp(ProveedorApp proveedorApp) {
		this.proveedorApp = proveedorApp;
	}

	/**
	 * @return the compraCabeceraId
	 */
	public CompraCabeceraId getCompraCabeceraId() {
		return compraCabeceraId;
	}

	/**
	 * @param compraCabeceraId the compraCabeceraId to set
	 */
	public void setCompraCabeceraId(CompraCabeceraId compraCabeceraId) {
		this.compraCabeceraId = compraCabeceraId;
	}

	/**
	 * @return the compraDetalleId
	 */
	public CompraDetalleId getCompraDetalleId() {
		return compraDetalleId;
	}

	/**
	 * @param compraDetalleId the compraDetalleId to set
	 */
	public void setCompraDetalleId(CompraDetalleId compraDetalleId) {
		this.compraDetalleId = compraDetalleId;
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
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

}
