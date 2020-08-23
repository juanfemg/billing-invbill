package co.com.juan.invbill.presentation.backingbeans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.enums.EstadosAppEnum;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.TipoUnidadMedida;
import co.com.juan.invbill.report.IReportController;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "reporteProductos")
@ViewScoped
public class ReporteProductosView implements Serializable {

	private static final long serialVersionUID = 2942121952052667470L;
	private static final String FILE_MESSAGES = "bundles.msg_ReporteProductos";
	private static final String REPORTE_PRODUCTOS = "reportProductos";
	private static final String SUFFIX_PDF = ".PDF";
	private static final String STREAM_CONTENT_TYPE = "application/pdf";
	private static final String PROPERTY_NAME_PRECIO_COMPRA = "precioCompra";
	private static final String PROPERTY_NAME_PRECIO_VENTA = "precioVenta";
	private static final String PROPERTY_NAME_STOCK = "stock";
	private static final Logger log = LoggerFactory.getLogger(CrearFacturaView.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	@ManagedProperty(value = "#{ReportController}")
	private transient IReportController reportController;

	private List<CategoriaProducto> categoria;
	private List<CategoriaProducto> categoriaFiltro;
	private List<SelectItem> categorias;
	private List<Producto> producto;
	private List<Producto> productoFiltro;
	private List<SelectItem> productos;
	private EstadosAppEnum[] estado;
	private List<EstadosAppEnum> estadoFiltro;
	private List<SelectItem> estados;
	private List<TipoUnidadMedida> tipoUnidadMedida;
	private List<TipoUnidadMedida> tiposUnidadMedidaFiltro;
	private List<SelectItem> tiposUnidadMedida;
	private StreamedContent content;
	private Integer stockProductoMaxPrecioCompra;
	private Integer stockProductoMaxPrecioVenta;
	private Integer stockProductoMaxStock;
	private Map<String, Integer> rangoPrecioCompra;
	private Map<String, Integer> rangoPrecioVenta;
	private Map<String, Integer> rangoStock;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ReporteProductosView() {
		super();
	}

	@PostConstruct
	public void init() {
		categoria = new ArrayList<>();
		categorias = new ArrayList<>();
		categoriaFiltro = new ArrayList<>();
		producto = new ArrayList<>();
		productos = new ArrayList<>();
		productoFiltro = new ArrayList<>();
		estado = null;
		estados = new ArrayList<>();
		estadoFiltro = new ArrayList<>();
		tipoUnidadMedida = new ArrayList<>();
		tiposUnidadMedida = new ArrayList<>();
		tiposUnidadMedidaFiltro = new ArrayList<>();
		rangoPrecioCompra = new HashMap<>();
		rangoPrecioVenta = new HashMap<>();
		rangoStock = new HashMap<>();
		stockProductoMaxPrecioCompra = new Integer(0);
		stockProductoMaxPrecioVenta = new Integer(0);
		stockProductoMaxStock = new Integer(0);
		initCategoriasProducto();
		initEstados();
		initTiposUnidadMedida();
		initMaxPrecioCompra();
		initMaxPrecioVenta();
		initMaxStock();
	}

	public void initCategoriasProducto() {
		try {
			categoria = businessDelegate.getCategoriasProductoSortByCategoria();

			for (CategoriaProducto categoriaProductoTemp : categoria) {
				categorias.add(new SelectItem(categoriaProductoTemp, categoriaProductoTemp.getCategoria()));
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Categorias de Productos : Fallo la consulta de las categorias de productos"
					+ ". ERROR : " + e.getMessage());
		}
	}

	public void initEstados() {
		try {
			estado = EstadosAppEnum.values();

			for (EstadosAppEnum estadoTemp : estado) {
				estados.add(new SelectItem(estadoTemp, estadoTemp.getEstado()));
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Estados : Fallo la consulta de los estados" + ". ERROR : " + e.getMessage());
		}
	}

	public void initTiposUnidadMedida() {
		try {
			tipoUnidadMedida = businessDelegate.getTiposUnidadMedida();

			for (TipoUnidadMedida tipoUnidadMedidaTemp : tipoUnidadMedida) {
				tiposUnidadMedida.add(new SelectItem(tipoUnidadMedidaTemp, tipoUnidadMedidaTemp.getUnidad()));
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Tipos de Unidades de Medida : Fallo la consulta de las unidades de medida"
					+ ". ERROR : " + e.getMessage());
		}
	}

	public void initProductos() {
		try {
			if (categoriaFiltro.isEmpty()) {
				producto.clear();
			} else {
				producto = businessDelegate.getProductosByCategoriasProducto(categoriaFiltro);
			}

			productos.clear();
			for (Producto productoTemp : producto) {
				productos.add(new SelectItem(productoTemp, productoTemp.getProducto()));
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Productos : Fallo la consulta de los productos" + ". ERROR : " + e.getMessage());
		}
	}

	public void initMaxPrecioCompra() {
		try {
			stockProductoMaxPrecioCompra = (Integer) businessDelegate
					.getMaximoStockProductoByPropertyName(PROPERTY_NAME_PRECIO_COMPRA);

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error(
					"=== Consulta de Stock Productos : Fallo la consulta del maximo del precio de compra de los productos"
							+ ". ERROR : " + e.getMessage());
		}
	}

	public void initMaxPrecioVenta() {
		try {
			stockProductoMaxPrecioVenta = (Integer) businessDelegate
					.getMaximoStockProductoByPropertyName(PROPERTY_NAME_PRECIO_VENTA);

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error(
					"=== Consulta de Stock Productos : Fallo la consulta del maximo del precio de venta de los productos"
							+ ". ERROR : " + e.getMessage());
		}
	}

	public void initMaxStock() {
		try {
			stockProductoMaxStock = (Integer) businessDelegate
					.getMaximoStockProductoByPropertyName(PROPERTY_NAME_STOCK);

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Stock Productos : Fallo la consulta del maximo del stock de los productos"
					+ ". ERROR : " + e.getMessage());
		}
	}

	public void actionGenerarReporte() {
		InputStream stream;

		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("LISTA_CATEGORIAS", filterNestedTypeInteger(categoriaFiltro));
			parameters.put("LISTA_PRODUCTOS", filterNestedTypeInteger(productoFiltro));
			parameters.put("LISTA_ESTADOS", filterNestedTypeString(estadoFiltro));
			parameters.put("LISTA_UNIDADES_MEDIDA", filterNestedTypeInteger(tiposUnidadMedidaFiltro));
			parameters.put("LOGO_APP", ImageIO.read(getClass().getResource("/images/logoApp.png")));
			parameters.put("RANGO_PRECIO_COMPRA", filterNestedTypeMap(rangoPrecioCompra, stockProductoMaxPrecioCompra));
			parameters.put("RANGO_PRECIO_VENTA", filterNestedTypeMap(rangoPrecioVenta, stockProductoMaxPrecioVenta));
			parameters.put("RANGO_STOCK", filterNestedTypeMap(rangoStock, stockProductoMaxStock));
			stream = reportController.getReport(REPORTE_PRODUCTOS, parameters);

			if (stream != null) {
				content = new DefaultStreamedContent(stream, STREAM_CONTENT_TYPE, REPORTE_PRODUCTOS.concat(SUFFIX_PDF));
			}

			addInfoMessage(properties.getParametroString("MSG_REPORTE_GENERADO"));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_GENERACION_REPORTE"));
			log.error("=== Generacion Reporte Productos : Fallo la generacion del reporte", e);
		}
	}

	public List<Integer> filterNestedTypeInteger(Collection<?> filterList) {
		List<Integer> filtered = new ArrayList<>();

		for (Object objectTemp : filterList) {
			if (objectTemp instanceof CategoriaProducto) {
				filtered.add(((CategoriaProducto) objectTemp).getIdCategoria());
			} else if (objectTemp instanceof Producto) {
				filtered.add(((Producto) objectTemp).getIdProducto());
			} else if (objectTemp instanceof TipoUnidadMedida) {
				filtered.add(((TipoUnidadMedida) objectTemp).getIdUnidadMedida());
			}
		}

		return filtered;
	}

	public Map<String, Integer> filterNestedTypeMap(Map<String, Integer> hash, Integer max) {
		Object maxValue = hash.get("MAX");
		Object minValue = hash.get("MIN");

		if (maxValue == null && minValue == null) {
			hash.put("MIN", new Integer(0));
			hash.put("MAX", max);
		}

		return hash;
	}

	public List<String> filterNestedTypeString(Collection<?> filterList) {
		List<String> filtered = new ArrayList<>();

		for (Object objectTemp : filterList) {
			filtered.add((String) objectTemp);
		}

		return filtered;
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
	 * @return the categoriaFiltro
	 */
	public List<CategoriaProducto> getCategoriaFiltro() {
		return categoriaFiltro;
	}

	/**
	 * @param categoriaFiltro the categoriaFiltro to set
	 */
	public void setCategoriaFiltro(List<CategoriaProducto> categoriaFiltro) {
		this.categoriaFiltro = categoriaFiltro;
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
	 * @return the productoFiltro
	 */
	public List<Producto> getProductoFiltro() {
		return productoFiltro;
	}

	/**
	 * @param productoFiltro the productoFiltro to set
	 */
	public void setProductoFiltro(List<Producto> productoFiltro) {
		this.productoFiltro = productoFiltro;
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
	 * @return the estado
	 */
	public EstadosAppEnum[] getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadosAppEnum[] estado) {
		this.estado = estado;
	}

	/**
	 * @return the estadoFiltro
	 */
	public List<EstadosAppEnum> getEstadoFiltro() {
		return estadoFiltro;
	}

	/**
	 * @param estadoFiltro the estadoFiltro to set
	 */
	public void setEstadoFiltro(List<EstadosAppEnum> estadoFiltro) {
		this.estadoFiltro = estadoFiltro;
	}

	/**
	 * @return the estados
	 */
	public List<SelectItem> getEstados() {
		return estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	/**
	 * @return the tipoUnidadMedida
	 */
	public List<TipoUnidadMedida> getTipoUnidadMedida() {
		return tipoUnidadMedida;
	}

	/**
	 * @param tipoUnidadMedida the tipoUnidadMedida to set
	 */
	public void setTipoUnidadMedida(List<TipoUnidadMedida> tipoUnidadMedida) {
		this.tipoUnidadMedida = tipoUnidadMedida;
	}

	/**
	 * @return the tiposUnidadMedidaFiltro
	 */
	public List<TipoUnidadMedida> getTiposUnidadMedidaFiltro() {
		return tiposUnidadMedidaFiltro;
	}

	/**
	 * @param tiposUnidadMedidaFiltro the tiposUnidadMedidaFiltro to set
	 */
	public void setTiposUnidadMedidaFiltro(List<TipoUnidadMedida> tiposUnidadMedidaFiltro) {
		this.tiposUnidadMedidaFiltro = tiposUnidadMedidaFiltro;
	}

	/**
	 * @return the tiposUnidadMedida
	 */
	public List<SelectItem> getTiposUnidadMedida() {
		return tiposUnidadMedida;
	}

	/**
	 * @param tiposUnidadMedida the tiposUnidadMedida to set
	 */
	public void setTiposUnidadMedida(List<SelectItem> tiposUnidadesMedida) {
		this.tiposUnidadMedida = tiposUnidadesMedida;
	}

	/**
	 * @return the content
	 */
	public StreamedContent getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(StreamedContent content) {
		this.content = content;
	}

	/**
	 * @return the stockProductoMaxPrecioCompra
	 */
	public Integer getStockProductoMaxPrecioCompra() {
		return stockProductoMaxPrecioCompra;
	}

	/**
	 * @param stockProductoMaxPrecioCompra the stockProductoMaxPrecioCompra to set
	 */
	public void setStockProductoMaxPrecioCompra(Integer stockProductoMaxPrecioCompra) {
		this.stockProductoMaxPrecioCompra = stockProductoMaxPrecioCompra;
	}

	/**
	 * @return the stockProductoMaxPrecioVenta
	 */
	public Integer getStockProductoMaxPrecioVenta() {
		return stockProductoMaxPrecioVenta;
	}

	/**
	 * @param stockProductoMaxPrecioVenta the stockProductoMaxPrecioVenta to set
	 */
	public void setStockProductoMaxPrecioVenta(Integer stockProductoMaxPrecioVenta) {
		this.stockProductoMaxPrecioVenta = stockProductoMaxPrecioVenta;
	}

	/**
	 * @return the stockProductoMaxStock
	 */
	public Integer getStockProductoMaxStock() {
		return stockProductoMaxStock;
	}

	/**
	 * @param stockProductoMaxStock the stockProductoMaxStock to set
	 */
	public void setStockProductoMaxStock(Integer stockProductoMaxStock) {
		this.stockProductoMaxStock = stockProductoMaxStock;
	}

	/**
	 * @return the rangoPrecioCompra
	 */
	public Map<String, Integer> getRangoPrecioCompra() {
		return rangoPrecioCompra;
	}

	/**
	 * @param rangoPrecioCompra the rangoPrecioCompra to set
	 */
	public void setRangoPrecioCompra(Map<String, Integer> rangoPrecioCompra) {
		this.rangoPrecioCompra = rangoPrecioCompra;
	}

	/**
	 * @return the rangoPrecioVenta
	 */
	public Map<String, Integer> getRangoPrecioVenta() {
		return rangoPrecioVenta;
	}

	/**
	 * @param rangoPrecioVenta the rangoPrecioVenta to set
	 */
	public void setRangoPrecioVenta(Map<String, Integer> rangoPrecioVenta) {
		this.rangoPrecioVenta = rangoPrecioVenta;
	}

	/**
	 * @return the rangoStock
	 */
	public Map<String, Integer> getRangoStock() {
		return rangoStock;
	}

	/**
	 * @param rangoStock the rangoStock to set
	 */
	public void setRangoStock(Map<String, Integer> rangoStock) {
		this.rangoStock = rangoStock;
	}
}
