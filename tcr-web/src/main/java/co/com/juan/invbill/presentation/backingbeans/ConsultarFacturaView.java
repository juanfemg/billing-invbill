package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import co.com.juan.invbill.model.FacturaCabecera;
import co.com.juan.invbill.report.IReportController;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "consultarFactura")
@ViewScoped
public class ConsultarFacturaView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ConsultaFactura";
	private static final String REPORTE_FACTURA_VENTA = "reportFacturaVenta";
	private static final long serialVersionUID = 4670229557589957806L;
	private static final Logger log = LoggerFactory.getLogger(ConsultarFacturaView.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	@ManagedProperty(value = "#{ReportController}")
	private transient IReportController reportController;

	private List<FacturaCabecera> facturaCabeceras;
	private FacturaCabecera facturaCabecera;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ConsultarFacturaView() {
		super();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		facturaCabeceras = (List<FacturaCabecera>) session.getAttribute(SessionEnum.FACTURA_CABECERAS.name());

		if (facturaCabeceras == null) {
			facturaCabeceras = new ArrayList<>();
		} else {
			session.removeAttribute(SessionEnum.FACTURA_CABECERAS.name());
		}

		facturaCabecera = new FacturaCabecera();
	}

	public void initFacturas() {
		try {
			facturaCabeceras = businessDelegate.getFacturaCabeceras();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_FACTURAS"));
			log.error("=== Consulta de facturas: Fallo la consulta de las facturas", e);
		}
	}

	public void initFacturasCriteria(FacturaCabecera entity) {
		try {
			facturaCabeceras = businessDelegate.getFacturaCabecerasByCriteria(entity);
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_FACTURAS"));
			log.error("=== Consulta de facturas: Fallo la consulta de las facturas", e);
		}
	}

	public void initFactura() {
		FacturaCabecera facturaCabeceraTemp;

		try {
			facturaCabeceraTemp = businessDelegate.findFacturaCabeceraByID(facturaCabecera.getIdFactura());

			if (!facturaCabeceras.isEmpty())
				facturaCabeceras.clear();

			if (facturaCabeceraTemp != null)
				facturaCabeceras.add(facturaCabeceraTemp);

			log.info("=== Consulta de facturas: Factura consultada {} ===", facturaCabecera.getIdFactura());
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_FACTURA",
					facturaCabecera.getIdFactura().toString()));
			log.error("=== Consulta de factura: Fallo la consulta de la factura {}. Se ha producido un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
		}
	}

	public void actionConsultar() {
		if (facturaCabecera.getIdFactura() == null && facturaCabecera.getFechaCreacion() == null) {
			initFacturas();
		} else if (facturaCabecera.getFechaCreacion() == null) {
			initFactura();
		} else {
			initFacturasCriteria(facturaCabecera);
		}
	}

	public String actionConsultarDetalle() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(SessionEnum.FACTURA_CABECERA_DETALLE.name(), facturaCabecera);
		session.setAttribute(SessionEnum.FACTURA_CABECERAS.name(), facturaCabeceras);

		return "goConsultarFacturaDetalle";
	}

	public void actionImprimir() {
		try {
			if (reportController.getDefaultPrinter()) {
				printReporte();
			} else {
				addWarnMessage(properties.getParametroString("MSG_IMPRESORA_NO_CONFIGURADA"));
			}
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_IMPRESORA_NO_CONFIGURADA"));
			log.error(
					"=== Impresion de copia de factura: Fallo la impresion de la factura {}. Se ha presentado un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
		}
	}

	public void printReporte() {
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("NUMERO_FACTURA_VENTA", facturaCabecera.getIdFactura());
			parameters.put("FACTURA_ORIGINAL", Boolean.FALSE);
			reportController.printReport(REPORTE_FACTURA_VENTA, parameters);
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_IMPRESION"));
			log.error(
					"=== Impresion de copia de factura: Fallo la impresion de la factura {}. Se ha presentado un error: {}",
					facturaCabecera.getIdFactura(), e.getMessage());
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
	 * @return the facturaCabeceras
	 */
	public List<FacturaCabecera> getFacturaCabeceras() {
		return facturaCabeceras;
	}

	/**
	 * @param facturaCabeceras the facturaCabeceras to set
	 */
	public void setFacturaCabeceras(List<FacturaCabecera> facturaCabeceras) {
		this.facturaCabeceras = facturaCabeceras;
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
	 * @return the reportController
	 */
	public IReportController getReportController() {
		return reportController;
	}

	/**
	 * @param reportController the reportController to set
	 */
	public void setReportController(IReportController reportController) {
		this.reportController = reportController;
	}

}