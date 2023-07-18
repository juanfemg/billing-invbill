package co.com.juan.invbill.presentation.backingbeans;

import java.awt.print.PrinterJob;
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
import javax.print.PrintService;
import javax.servlet.http.HttpSession;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.enums.ParameterEnum;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "gestionConfiguracion")
@ViewScoped
public class GestionConfiguracionView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_ModificacionConfiguracion";
	private static final long serialVersionUID = 5778879622123315873L;
	private static final Logger log = LoggerFactory.getLogger(GestionConfiguracionView.class);

	@ManagedProperty(value = "#{clienteDelegate}")
	private transient IClienteDelegate clienteDelegate;

	@ManagedProperty(value = "#{configDelegate}")
	private transient IConfigDelegate configDelegate;

	public IConfigDelegate getConfigDelegate() {
		return configDelegate;
	}

	public void setConfigDelegate(IConfigDelegate configDelegate) {
		this.configDelegate = configDelegate;
	}

	private transient PrintService[] printServices;
	private transient HttpSession session;
	private AppConfig impresora;
	private AppConfig iva;
	private AppConfig topeStock;
	private List<SelectItem> impresoras;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public GestionConfiguracionView() {
		super();
	}

	@PostConstruct
	public void init() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		impresoras = new ArrayList<>();
		impresora = new AppConfig();
		iva = new AppConfig();
		topeStock = new AppConfig();
		initParametros();
	}

	public void initParametros() {
		impresora = (AppConfig) session.getAttribute(ParameterEnum.IMPRESORA_PREDETERMINADA.name());
		iva = (AppConfig) session.getAttribute(ParameterEnum.IVA.name());
		topeStock = (AppConfig) session.getAttribute(ParameterEnum.TOPE_STOCK.name());
		initImpresoras();
	}

	public void initImpresoras() {
		try {
			printServices = PrinterJob.lookupPrintServices();

			for (PrintService printServiceLook : printServices) {
				impresoras.add(new SelectItem(printServiceLook.getName()));
			}

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_CONSULTA_IMPRESORAS"));
			log.error("=== Consulta de Impresoras: Fallo la consulta de las impresoras", e);
		}
	}

	public void actionGuardarConfigImpresora() {
		try {
			this.configDelegate.update(impresora);

			log.info("=== Actualizacion de configuracion de impresora: Parametro actualizado. Id={}, valor={} === ",
					impresora.getIdAppConfig(), impresora.getValor());
			addInfoMessage(properties.getParameterByKey("MSG_CONFIGURACION_IMPRESORA_ACTUALIZADA"));

			session.setAttribute(ParameterEnum.IMPRESORA_PREDETERMINADA.name(), impresora);

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_ACTUALIZACION_IMPRESORA"));
			log.error(
					"=== Actualizacion de configuracion de impresora: Fallo la actualizacion del parametro {}. Se ha producido un error: {}",
					impresora.getIdAppConfig(), e.getMessage());
		}
	}

	public void actionGuardarConfigIva() {
		try {
			this.configDelegate.update(iva);

			log.info("=== Actualizacion de configuracion de iva: Parametro actualizado. Id={}, valor={} === ",
					iva.getIdAppConfig(), iva.getValor());
			addInfoMessage(properties.getParameterByKey("MSG_CONFIGURACION_IVA_ACTUALIZADA"));

			session.setAttribute(ParameterEnum.IVA.name(), iva);

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_ACTUALIZACION_IVA"));
			log.error(
					"=== Actualizacion de configuracion de IVA: Fallo la actualizacion del parametro {}. Se ha producido un error: {}",
					iva.getIdAppConfig(), e.getMessage());
		}
	}

	public void actionGuardarConfigTopeStock() {
		try {
			this.configDelegate.update(topeStock);

			log.info("=== Actualizacion de configuracion de tope de stock: Parametro actualizado. Id={}, valor={} === ",
					topeStock.getIdAppConfig(), topeStock.getValor());
			addInfoMessage(properties.getParameterByKey("MSG_CONFIGURACION_TOPE_STOCK_ACTUALIZADA"));

			session.setAttribute(ParameterEnum.TOPE_STOCK.name(), topeStock);

		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_ACTUALIZACION_TOPE_STOCK"));
			log.error(
					"=== Actualizacion de configuracion del tope de stock: Fallo la actualizacion del parametro {}. Se ha producido un error: {}",
					topeStock.getIdAppConfig(), e.getMessage());
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
	 * @return the impresoras
	 */
	public List<SelectItem> getImpresoras() {
		return impresoras;
	}

	/**
	 * @param impresoras the impresoras to set
	 */
	public void setImpresoras(List<SelectItem> impresoras) {
		this.impresoras = impresoras;
	}

	/**
	 * @return the printServices
	 */
	public PrintService[] getPrintServices() {
		return printServices;
	}

	/**
	 * @param printServices the printServices to set
	 */
	public void setPrintServices(PrintService[] printServices) {
		this.printServices = printServices;
	}

	/**
	 * @return the impresora
	 */
	public AppConfig getImpresora() {
		return impresora;
	}

	/**
	 * @param impresora the impresora to set
	 */
	public void setImpresora(AppConfig impresora) {
		this.impresora = impresora;
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
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * @return the iva
	 */
	public AppConfig getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(AppConfig iva) {
		this.iva = iva;
	}

	/**
	 * @return the topeStock
	 */
	public AppConfig getTopeStock() {
		return topeStock;
	}

	/**
	 * @param topeStock the topeStock to set
	 */
	public void setTopeStock(AppConfig topeStock) {
		this.topeStock = topeStock;
	}

}