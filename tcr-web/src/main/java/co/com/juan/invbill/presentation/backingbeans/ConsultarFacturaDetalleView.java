package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;
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
import co.com.juan.invbill.model.FacturaCabecera;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */

@ManagedBean(name = "consultarFacturaDetalle")
@ViewScoped
public class ConsultarFacturaDetalleView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ConsultaFacturaDetalle";
	private static final long serialVersionUID = -6989714907754566718L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarFacturaDetalleView.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private FacturaCabecera facturaCabecera;
	private List<FacturaDetalle> facturaDetalles;
	private DevolucionCabecera devolucionCabecera;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarFacturaDetalleView() {
		super();
	}

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		facturaCabecera = (FacturaCabecera) session.getAttribute(SessionEnum.FACTURA_CABECERA_DETALLE.name());

		try {
			if (facturaCabecera != null) {
				devolucionCabecera = businessDelegate.findDevolucionCabeceraByID(facturaCabecera.getIdFactura());
			}
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_DEVOLUCION",
					facturaCabecera.getIdFactura().toString()));
			log.error("=== Consulta de devolucion: Fallo la consulta de la devolucion {}. Se ha producido un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
		}
	}

	public String actionConsultarDevoluciones() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(SessionEnum.DEVOLUCION_CABECERA_DETALLE.name(), devolucionCabecera);
		session.setAttribute(SessionEnum.FACTURA_CABECERA_DETALLE.name(), facturaCabecera);

		return "goConsultarDevolucionDetalle";
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

}
