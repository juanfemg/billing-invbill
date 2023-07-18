package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.enums.SessionEnum;
import co.com.juan.invbill.model.DevolucionCabecera;
import co.com.juan.invbill.model.FacturaCabecera;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "consultarDevolucionDetalle")
@ViewScoped
public class ConsultarDevolucionDetalleView implements Serializable {

	private static final long serialVersionUID = -6989714907754566718L;

	@ManagedProperty(value = "#{clienteDelegate}")
	private transient IClienteDelegate clienteDelegate;

	private DevolucionCabecera devolucionCabecera;
	private FacturaCabecera facturaCabecera;

	public ConsultarDevolucionDetalleView() {
		super();
	}

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		devolucionCabecera = (DevolucionCabecera) session.getAttribute(SessionEnum.RETURN_HEADER_DETAIL.name());
		facturaCabecera = (FacturaCabecera) session.getAttribute(SessionEnum.INVOICE_HEADER_DETAIL.name());
	}

	public String actionVolver() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if (facturaCabecera != null) {
			session.setAttribute(SessionEnum.INVOICE_HEADER_DETAIL.name(), facturaCabecera);
			return "goConsultarFacturaDetalle";
		}
		return "goConsultarDevolucion";
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
	public IClienteDelegate getClienteDelegate() {
		return clienteDelegate;
	}

	/**
	 * 
	 */
	public void setClienteDelegate(IClienteDelegate clienteDelegate) {
		this.clienteDelegate = clienteDelegate;
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

}
