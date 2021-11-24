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
import co.com.juan.invbill.model.DevolucionCabecera;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "consultarDevolucion")
@ViewScoped
public class ConsultarDevolucionView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ConsultaDevolucion";
	private static final long serialVersionUID = 4670229557589957806L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarDevolucionView.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private List<DevolucionCabecera> devolucionCabeceras;
	private DevolucionCabecera devolucionCabecera;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarDevolucionView() {
		super();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		devolucionCabeceras = (List<DevolucionCabecera>) session.getAttribute(SessionEnum.DEVOLUCION_CABECERAS.name());

		if (devolucionCabeceras == null) {
			devolucionCabeceras = new ArrayList<>();
		} else {
			session.removeAttribute(SessionEnum.DEVOLUCION_CABECERAS.name());
		}

		devolucionCabecera = new DevolucionCabecera();
	}

	public void initDevoluciones() {
		try {
			devolucionCabeceras = businessDelegate.getDevolucionCabeceras();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_DEVOLUCIONES"));
			log.error("=== Consulta de devoluciones: Fallo la consulta de las devoluciones", e);
		}
	}

	public void initDevolucionCriteria(DevolucionCabecera entity) {
		try {
			devolucionCabeceras = businessDelegate.getDevolucionCabecerasByCriteria(entity);
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_DEVOLUCIONES"));
			log.error("=== Consulta de devoluciones: Fallo la consulta de las devoluciones", e);
		}
	}

	public void initDevolucion() {
		DevolucionCabecera devolucionCabeceraTemp;

		try {
			devolucionCabeceraTemp = businessDelegate.findDevolucionCabeceraByID(devolucionCabecera.getIdFactura());

			if (!devolucionCabeceras.isEmpty())
				devolucionCabeceras.clear();

			if (devolucionCabeceraTemp != null)
				devolucionCabeceras.add(devolucionCabeceraTemp);

			log.info("=== Consulta de devoluciones: Devolucion consultada {} ===", devolucionCabecera.getIdFactura());
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_DEVOLUCION",
					devolucionCabecera.getIdFactura().toString()));
			log.error("=== Consulta de devolucion: Fallo la consulta de la devolucion {}. Se ha producido un error: {}",
					devolucionCabecera.getIdFactura(), e.getMessage());
		}
	}

	public void actionConsultar() {
		if (devolucionCabecera.getIdFactura() == null && devolucionCabecera.getFechaCreacion() == null) {
			initDevoluciones();
		} else if (devolucionCabecera.getFechaCreacion() == null) {
			initDevolucion();
		} else {
			initDevolucionCriteria(devolucionCabecera);
		}
	}

	public String actionConsultarDetalle() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(SessionEnum.DEVOLUCION_CABECERA_DETALLE.name(), devolucionCabecera);
		session.setAttribute(SessionEnum.DEVOLUCION_CABECERAS.name(), devolucionCabeceras);

		return "goConsultarDevolucionDetalle";
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
	 * @return the devolucionCabeceras
	 */
	public List<DevolucionCabecera> getDevolucionCabeceras() {
		return devolucionCabeceras;
	}

	/**
	 * @param devolucionCabeceras the devolucionCabeceras to set
	 */
	public void setDevolucionCabeceras(List<DevolucionCabecera> devolucionCabeceras) {
		this.devolucionCabeceras = devolucionCabeceras;
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
