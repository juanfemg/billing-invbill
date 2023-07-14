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

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.TipoUnidadMedida;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "consultarProducto")
@ViewScoped
public class ConsultarProductoView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ModificacionProducto";
	private static final long serialVersionUID = 9050729303899990211L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarProductoView.class);
	private static final String ID_DIALOG_MESSAGES = "menMod";

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IClienteDelegate businessDelegate;

	public IProductoDelegate getProductoDelegate() {
		return productoDelegate;
	}

	public void setProductoDelegate(IProductoDelegate productoDelegate) {
		this.productoDelegate = productoDelegate;
	}

	@ManagedProperty(value = "#{productoDelegate}")
	private transient IProductoDelegate productoDelegate;

	@ManagedProperty(value = "#{configDelegate}")
	private transient IConfigDelegate configDelegate;

	public IConfigDelegate getConfigDelegate() {
		return configDelegate;
	}

	public void setConfigDelegate(IConfigDelegate configDelegate) {
		this.configDelegate = configDelegate;
	}

	private Producto productoMod;
	private List<SelectItem> estadosApp;
	private List<Producto> productos;
	private List<CategoriaProducto> categoriasProducto;
	private List<SelectItem> categorias;
	private List<TipoUnidadMedida> tiposUnidadMedida;
	private List<SelectItem> unidades;
	private boolean showDialogModificarProducto;
	private transient Properties properties = new Properties(FILE_MESSAGES);

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
		initEstadosApp();
		initProductos();
	}

	public void initEstadosApp() {
		for (StatusEnum estadosAppEnumTemp : StatusEnum.values()) {
			estadosApp.add(new SelectItem(estadosAppEnumTemp, estadosAppEnumTemp.getStatus()));
		}
	}

	public void initProductos() {
		try {
			productos = this.productoDelegate.getProductos();
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_PRODUCTOS"));
			log.error("=== Consulta de Productos: Fallo la consulta de los productos", e);
		}
	}

	public void initCategoriasProducto() {
		try {
			categoriasProducto = this.productoDelegate.getCategoriasProductoSortByCategoria();

			for (CategoriaProducto categoriaProductoTemp : categoriasProducto) {
				if (categoriaProductoTemp.getEstado().equals(StatusEnum.A)) {
					categorias.add(new SelectItem(categoriaProductoTemp, categoriaProductoTemp.getCategoria()));
				}
			}

			if (categorias.isEmpty()) {
				addWarnMessage(properties.getParameterByKey("MSG_MODIFICACION_CATEGORIAS_INACTIVAS"));
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_CATEGORIAS"));
			log.error("=== Consulta de Categorias: Fallo la consulta de las categorias", e);
		}
	}

	public void initTiposUnidadMedida() {
		try {
			tiposUnidadMedida = this.configDelegate.getTiposUnidadMedida();

			for (TipoUnidadMedida tipoUnidadMedidaTemp : tiposUnidadMedida) {
				if (tipoUnidadMedidaTemp.getEstado().equals(StatusEnum.A)) {
					unidades.add(new SelectItem(tipoUnidadMedidaTemp, tipoUnidadMedidaTemp.getUnidad()));
				}
			}

			if (unidades.isEmpty()) {
				addWarnMessage(properties.getParameterByKey("MSG_MODIFICACION_UNIDADES_MEDIDA_INACTIVAS"));
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_UNIDADES_MEDIDA"));
			log.error("=== Consulta de Tipos de Unidades de Medida: Fallo la consulta de las unidades de medida", e);
		}
	}

	public void actionEditar() {
		initCategoriasProducto();
		initTiposUnidadMedida();
		showDialogModificarProducto = true;
	}

	public void actionModificar() {
		try {
			this.productoDelegate.update(productoMod);

			showDialogModificarProducto = false;
			log.info("=== Actualizacion de producto: Producto actualizado. Id={}, descripcion={} === ",
					productoMod.getIdProducto(), productoMod.getProducto());
			addInfoMessage(properties.getParameterByKey("MSG_PRODUCTO_ACTUALIZADO"));
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_ACTUALIZACION_PRODUCTO"), ID_DIALOG_MESSAGES);
			log.error(
					"=== Actualizacion de producto: Fallo la actualizacion del producto {}. Se ha producido un error: {}",
					productoMod.getIdProducto(), e.getMessage());
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
	public IClienteDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate the businessDelegate to set
	 */
	public void setBusinessDelegate(IClienteDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	/**
	 * @return the productoMod
	 */
	public Producto getProductoMod() {
		return productoMod;
	}

	/**
	 * @param productoMod the productoMod to set
	 */
	public void setProductoMod(Producto productoMod) {
		this.productoMod = productoMod;
	}

	/**
	 * @return the estadosApp
	 */
	public List<SelectItem> getEstadosApp() {
		return estadosApp;
	}

	/**
	 * @param estadosApp the estadosApp to set
	 */
	public void setEstadosApp(List<SelectItem> estadosApp) {
		this.estadosApp = estadosApp;
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
	 * @return the categoriasProducto
	 */
	public List<CategoriaProducto> getCategoriasProducto() {
		return categoriasProducto;
	}

	/**
	 * @param categoriasProducto the categoriasProducto to set
	 */
	public void setCategoriasProducto(List<CategoriaProducto> categoriasProducto) {
		this.categoriasProducto = categoriasProducto;
	}

	/**
	 * @return the showDialogModificarProducto
	 */
	public boolean isShowDialogModificarProducto() {
		return showDialogModificarProducto;
	}

	/**
	 * @param showDialogModificarProducto the showDialogModificarProducto to set
	 */
	public void setShowDialogModificarProducto(boolean showDialogModificarProducto) {
		this.showDialogModificarProducto = showDialogModificarProducto;
	}

	/**
	 * @return the productos
	 */
	public List<Producto> getProductos() {
		return productos;
	}

	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
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
	 * @return the tipoUnidadMedida
	 */
	public List<TipoUnidadMedida> getTiposUnidadMedida() {
		return tiposUnidadMedida;
	}

	/**
	 * @param tiposUnidadMedida the tiposUnidadMedida to set
	 */
	public void setTiposUnidadMedida(List<TipoUnidadMedida> tiposUnidadMedida) {
		this.tiposUnidadMedida = tiposUnidadMedida;
	}

	/**
	 * @return the unidades
	 */
	public List<SelectItem> getUnidades() {
		return unidades;
	}

	/**
	 * @param unidades the unidades to set
	 */
	public void setUnidades(List<SelectItem> unidades) {
		this.unidades = unidades;
	}

}
