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
import co.com.juan.invbill.enums.SessionEnum;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "consultarCompra")
@ViewScoped
public class ConsultarCompraView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ConsultaCompra";
	private static final long serialVersionUID = 4670229557589957806L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarCompraView.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private List<CompraCabecera> compraCabeceras;
	private CompraCabecera compraCabecera;
	private CompraCabeceraId compraCabeceraId;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarCompraView() {
		super();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		compraCabeceras = (List<CompraCabecera>) session.getAttribute(SessionEnum.PURCHASE_HEADERS.name());

		if (compraCabeceras == null) {
			compraCabeceras = new ArrayList<>();
		} else {
			session.removeAttribute(SessionEnum.PURCHASE_HEADERS.name());
		}

		compraCabecera = new CompraCabecera();
		compraCabeceraId = new CompraCabeceraId();
	}

	public void initCompras() {
		try {
			compraCabeceras = businessDelegate.getCompraCabeceras();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_COMPRAS"));
			log.error("=== Consulta de compras: Fallo la consulta de las compras", e);
		}
	}

	public void initCompra() {
		CompraCabecera compraCabeceraTemp;

		try {
			compraCabeceraTemp = businessDelegate.findCompraCabeceraByID(compraCabeceraId);

			if (!compraCabeceras.isEmpty())
				compraCabeceras.clear();

			if (compraCabeceraTemp != null)
				compraCabeceras.add(compraCabeceraTemp);

			log.info("=== Consulta de compras: Factura consultada {}. Proveedor consultado {} ===",
					compraCabeceraId.getIdFacturaCompra(), compraCabeceraId.getIdProveedorApp());
		} catch (Exception e) {
			addErrorMessage(
					properties.getParametroString("MSG_ERROR_CONSULTA_COMPRA_BY_FACTURA_AND_PROVEEDOR", new String[] {
							compraCabeceraId.getIdFacturaCompra(), compraCabeceraId.getIdProveedorApp().toString() }));
			log.error(
					"=== Consulta de compras: Fallo la consulta de la compra, factura={}, proveedor={}. Se ha producido un error: {}",
					compraCabeceraId.getIdFacturaCompra(), compraCabeceraId.getIdProveedorApp(), e.getMessage());
		}
	}

	public void initComprasByID() {
		try {
			compraCabeceras = businessDelegate.getCompraCabeceras(compraCabeceraId);

			log.info("=== Consulta de compras: Factura consultada {}. Proveedor consultado {} ===",
					compraCabeceraId.getIdFacturaCompra(), compraCabeceraId.getIdProveedorApp());
		} catch (Exception e) {
			if (compraCabeceraId.getIdFacturaCompra().isEmpty()) {
				addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_COMPRA_BY_PROVEEDOR",
						compraCabeceraId.getIdProveedorApp().toString()));
			} else {
				addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_COMPRA_BY_FACTURA",
						compraCabeceraId.getIdFacturaCompra()));
			}
			log.error(
					"=== Consulta de compras: Fallo la consulta de la compra, factura={}, proveedor={}. Se ha producido un error: {}",
					compraCabeceraId.getIdFacturaCompra(), compraCabeceraId.getIdProveedorApp(), e.getMessage());
		}
	}

	public void actionConsultar() {
		if (compraCabeceraId.getIdFacturaCompra().isEmpty() && compraCabeceraId.getIdProveedorApp() == null) {
			initCompras();
		} else if (!compraCabeceraId.getIdFacturaCompra().isEmpty() && compraCabeceraId.getIdProveedorApp() != null) {
			initCompra();
		} else {
			initComprasByID();
		}
	}

	public String actionConsultarDetalle() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(SessionEnum.PURCHASE_HEADER_DETAIL.name(), compraCabecera);
		session.setAttribute(SessionEnum.PURCHASE_HEADERS.name(), compraCabeceras);

		return "goConsultarCompraDetalle";
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
	 * @return the compraCabeceras
	 */
	public List<CompraCabecera> getCompraCabeceras() {
		return compraCabeceras;
	}

	/**
	 * @param compraCabeceras the compraCabeceras to set
	 */
	public void setCompraCabeceras(List<CompraCabecera> compraCabeceras) {
		this.compraCabeceras = compraCabeceras;
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

}
