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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.TipoUnidadMedida;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "crearProducto")
@ViewScoped
public class CrearProductoView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_CreacionProducto";
	private static final long serialVersionUID = 5895645994887623582L;
	private static final Logger log = LoggerFactory.getLogger(CrearProductoView.class);

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private Producto producto;
	private List<CategoriaProducto> categoriasProducto;
	private List<SelectItem> categorias;
	private List<TipoUnidadMedida> tiposUnidadMedida;
	private List<SelectItem> unidades;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public CrearProductoView() {
		super();
	}

	@PostConstruct
	public void init() {
		producto = new Producto();
		categorias = new ArrayList<>();
		categoriasProducto = new ArrayList<>();
		tiposUnidadMedida = new ArrayList<>();
		unidades = new ArrayList<>();
		initCategoriasProducto();
		initTiposUnidadMedida();
	}

	public void initCategoriasProducto() {
		try {
			categoriasProducto = businessDelegate.getCategoriasProductoSortByCategoria();

			for (CategoriaProducto categoriaProductoTemp : categoriasProducto) {
				if (categoriaProductoTemp.getEstado().equals(StatusEnum.A)) {
					categorias.add(new SelectItem(categoriaProductoTemp, categoriaProductoTemp.getCategoria()));
				}
			}

			if (categorias.isEmpty()) {
				addWarnMessage(properties.getParameterByKey("MSG_CATEGORIAS_INACTIVAS"));
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_CATEGORIAS"));
			log.error("=== Consulta de Categorias: Fallo la consulta de las categorias", e);
		}
	}

	public void initTiposUnidadMedida() {
		try {
			tiposUnidadMedida = businessDelegate.getTiposUnidadMedida();

			for (TipoUnidadMedida tipoUnidadMedidaTemp : tiposUnidadMedida) {
				if (tipoUnidadMedidaTemp.getEstado().equals(StatusEnum.A)) {
					unidades.add(new SelectItem(tipoUnidadMedidaTemp, tipoUnidadMedidaTemp.getUnidad()));
				}
			}

			if (unidades.isEmpty()) {
				addWarnMessage(properties.getParameterByKey("MSG_UNIDADES_MEDIDA_INACTIVAS"));
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_UNIDADES_MEDIDA"));
			log.error("=== Consulta de Tipos de Unidades de Medida: Fallo la consulta de las unidades de medida", e);
		}
	}

	public void actionGuardar() {
		try {
			businessDelegate.save(producto);
			log.info("=== Creacion de producto: Producto creado {}", producto.getIdProducto());
			addInfoMessage(properties.getParameterByKey("MSG_PRODUCTO_CREADO"));
			producto = new Producto();
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CREACION_PRODUCTO"));
			log.error("=== Creacion de producto: Fallo la creacion del producto {}. Se ha producido un error: {}",
					producto.getIdProducto(), e.getMessage());
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
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
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
	 * @return the tiposUnidadMedida
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
