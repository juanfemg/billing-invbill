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
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "consultarCategoria")
@ViewScoped
public class ConsultarCategoriaView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ModificacionCategoria";
	private static final long serialVersionUID = 1709195946652231206L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarCategoriaView.class);
	private static final String ID_DIALOG_MESSAGES = "menMod";

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private CategoriaProducto categoriaModProducto;
	private List<SelectItem> estadosApp;
	private List<CategoriaProducto> categoriasProducto;
	private boolean showDialogModificarCategoria;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarCategoriaView() {
		super();
	}

	@PostConstruct
	public void init() {
		categoriaModProducto = new CategoriaProducto();
		estadosApp = new ArrayList<>();
		categoriasProducto = new ArrayList<>();
		showDialogModificarCategoria = false;
		initEstadosApp();
		initCategoriasProducto();
	}

	public void initEstadosApp() {
		for (StatusEnum estadosAppEnumTemp : StatusEnum.values()) {
			estadosApp.add(new SelectItem(estadosAppEnumTemp, estadosAppEnumTemp.getStatus()));
		}
	}

	public void initCategoriasProducto() {
		try {
			categoriasProducto = businessDelegate.getCategoriasProductoSortByCategoria();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_CATEGORIAS"));
			log.error("=== Consulta de Categorias: Fallo la consulta de las categorias", e);
		}
	}

	public void actionEditar() {
		showDialogModificarCategoria = true;
	}

	public void actionModificar() {
		try {
			businessDelegate.update(categoriaModProducto);
			showDialogModificarCategoria = false;
			log.info("=== Actualizacion de categoria: Categoria actualizada. Id={}, descripcion={} ===",
					categoriaModProducto.getIdCategoria(), categoriaModProducto.getCategoria());
			addInfoMessage(properties.getParametroString("MSG_CATEGORIA_ACTUALIZADA"));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_ACTUALIZACION_CATEGORIA"), ID_DIALOG_MESSAGES);
			log.error(
					"=== Actualizacion de categoria: Fallo la actualizacion de la categoria {}. Se ha producido un error: {}",
					categoriaModProducto.getIdCategoria(), e.getMessage());
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
	 * @return the categoriaModProducto
	 */
	public CategoriaProducto getCategoriaModProducto() {
		return categoriaModProducto;
	}

	/**
	 * @param categoriaModProducto the categoriaModProducto to set
	 */
	public void setCategoriaModProducto(CategoriaProducto categoriaModProducto) {
		this.categoriaModProducto = categoriaModProducto;
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
	 * @return the showDialogModificarCategoria
	 */
	public boolean isShowDialogModificarCategoria() {
		return showDialogModificarCategoria;
	}

	/**
	 * @param showDialogModificarCategoria the showDialogModificarCategoria to set
	 */
	public void setShowDialogModificarCategoria(boolean showDialogModificarCategoria) {
		this.showDialogModificarCategoria = showDialogModificarCategoria;
	}

}
