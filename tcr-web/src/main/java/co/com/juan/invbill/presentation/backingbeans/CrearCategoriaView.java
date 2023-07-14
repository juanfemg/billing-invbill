package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "crearCategoria")
@ViewScoped
public class CrearCategoriaView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_CreacionCategoria";
	private static final long serialVersionUID = 6548574811248255082L;
	private static final Logger log = LoggerFactory
			.getLogger(CrearCategoriaView.class);

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

	private CategoriaProducto categoriaProducto;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public CrearCategoriaView() {
		super();
	}

	@PostConstruct
	public void init() {
		categoriaProducto = new CategoriaProducto();
	}

	public void actionGuardar() {
		try {
			this.productoDelegate.save(categoriaProducto);
			log.info("=== Creacion de categoria : Categoria creada "
					+ categoriaProducto.getIdCategoria() + " exitosamente ===");
			addInfoMessage(properties
					.getParameterByKey("MSG_CATEGORIA_CREADA"));
			categoriaProducto = new CategoriaProducto();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Creacion de categoria : Fallo la creacion de la categoria "
					+ categoriaProducto.getIdCategoria()
					+ ". ERROR : "
					+ e.getMessage());
		}
	}

	public void actionLimpiar() {
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
	}

	public void addInfoMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addWarnMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addErrorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * @return the businessDelegate
	 */
	public IClienteDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate
	 *            the businessDelegate to set
	 */
	public void setBusinessDelegate(IClienteDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	/**
	 * @return the categoriaProducto
	 */
	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	/**
	 * @param categoriaProducto
	 *            the categoriaProducto to set
	 */
	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
