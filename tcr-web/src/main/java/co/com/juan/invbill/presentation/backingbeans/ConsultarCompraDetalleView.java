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
import co.com.juan.invbill.model.CompraCabecera;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "consultarCompraDetalle")
@ViewScoped
public class ConsultarCompraDetalleView implements Serializable {

	private static final long serialVersionUID = -6989714907754566718L;

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IClienteDelegate businessDelegate;

	private CompraCabecera compraCabecera;

	public ConsultarCompraDetalleView() {
		super();
	}

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		compraCabecera = (CompraCabecera) session.getAttribute(SessionEnum.PURCHASE_HEADER_DETAIL.name());
		session.removeAttribute(SessionEnum.PURCHASE_HEADER_DETAIL.name());
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

}
