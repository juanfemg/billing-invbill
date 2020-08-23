package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
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

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

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
			businessDelegate.save(categoriaProducto);
			log.info("=== Creacion de categoria : Categoria creada "
					+ categoriaProducto.getIdCategoria() + " exitosamente ===");
			addInfoMessage(properties
					.getParametroString("MSG_CATEGORIA_CREADA"));
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
	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate
	 *            the businessDelegate to set
	 */
	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
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
