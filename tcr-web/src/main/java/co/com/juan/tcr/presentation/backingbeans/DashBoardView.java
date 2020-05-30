package co.com.juan.tcr.presentation.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.tcr.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.tcr.dto.ReporteCompraDiaria;
import co.com.juan.tcr.dto.ReporteCompraMensual;
import co.com.juan.tcr.dto.ReporteDevolucionDiaria;
import co.com.juan.tcr.dto.ReporteDevolucionMensual;
import co.com.juan.tcr.dto.ReporteVentaDiaria;
import co.com.juan.tcr.dto.ReporteVentaMensual;
import co.com.juan.tcr.enums.ReporteMensualEnum;
import co.com.juan.tcr.enums.SessionEnum;
import co.com.juan.tcr.model.LoginApp;
import co.com.juan.tcr.model.ProveedorApp;
import co.com.juan.tcr.model.UsuarioApp;
import co.com.juan.tcr.util.Encrypt;
import co.com.juan.tcr.util.Properties;

/**
 * @author JuanFelipe
 *
 */
@ManagedBean(name = "dashBoard")
@ViewScoped
public class DashBoardView implements Serializable {

	private static final String FILE_MESSAGES = "bundles.msg_Dashboard";
	private static final long serialVersionUID = -4798674428229332395L;
	private static final Logger log = LoggerFactory.getLogger(CrearUsuarioView.class);
	private static final String LABEL_CHART_VENTAS = "Ventas";
	private static final String LABEL_CHART_DEVOLUCIONES = "Devoluciones";
	private static final String LABEL_CHART_COMPRAS = "Compras";
	private static final String EXTENDER_CHART = "customExtender";
	private static final String LABEL_AXIS_X_CHART = "Dia del Mes";
	private static final String LABEL_AXIS_Y_CHART = "Total";

	@ManagedProperty(value = "#{BusinessDelegate}")
	private transient IBusinessDelegate businessDelegate;

	private UsuarioApp usuarioApp;
	private LoginApp loginApp;
	private ReporteVentaDiaria reporteVentaDiaria;
	private ReporteDevolucionDiaria reporteDevolucionDiaria;
	private ReporteCompraDiaria reporteCompraDiaria;
	private List<ReporteVentaMensual> reporteVentaMensuales;
	private List<ReporteDevolucionMensual> reporteDevolucionMensuales;
	private List<ReporteCompraMensual> reporteCompraMensuales;
	private List<ProveedorApp> proveedoresApp;
	private ProveedorApp proveedorApp;
	private BarChartModel barModelVentas;
	private BarChartModel barModelDevoluciones;
	private BarChartModel barModelCompras;
	private BarChartModel barChartModel;
	private List<SelectItem> reporteMensuales;
	private ReporteMensualEnum reporteMensualEnum;
	private String passwordOld;
	private String passwordNew;
	private String passwordReNew;
	private boolean showDialogCambiarPassword;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public DashBoardView() {
		super();
	}

	@PostConstruct
	public void init() {
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		barModelVentas = new BarChartModel();
		barModelDevoluciones = new BarChartModel();
		barModelCompras = new BarChartModel();
		barChartModel = new BarChartModel();
		loginApp = new LoginApp();
		reporteVentaDiaria = new ReporteVentaDiaria();
		reporteDevolucionDiaria = new ReporteDevolucionDiaria();
		reporteCompraDiaria = new ReporteCompraDiaria();
		proveedorApp = new ProveedorApp();
		reporteVentaMensuales = new ArrayList<>();
		reporteDevolucionMensuales = new ArrayList<>();
		reporteCompraMensuales = new ArrayList<>();
		reporteMensuales = new ArrayList<>();
		proveedoresApp = new ArrayList<>();
		showDialogCambiarPassword = false;
		initUsuario(session);
		initReporteVentaDiaria();
		initReporteDevolucionDiaria();
		initReporteCompraDiaria();
		initReporteVentaMensual();
		initReporteDevolucionMensual();
		initReporteCompraMensual();
		initReportesMensuales();
		initBarCharts();
		initProveedores();
	}

	public void initUsuario(HttpSession session) {
		try {
			usuarioApp = businessDelegate.findUsuarioByID(session.getAttribute(SessionEnum.LOGIN.name()).toString());
			loginApp.setUltimoLogin((Date) session.getAttribute(SessionEnum.LAST_LOGIN.name()));
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_USUARIO"));
			log.error("=== Consulta de usuario: Fallo la consulta del usuario {}. Se ha producido un error: {}",
					session.getAttribute("LOGIN"), e.getMessage());
		}
	}

	public void initReporteVentaDiaria() {
		try {
			reporteVentaDiaria = businessDelegate.getReporteVentaDiaria();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_REPORTE_VENTA_DIARIA"));
			log.error(
					"=== Consulta de reporte de venta diaria: Fallo la consulta del reporte. Se ha producido un error: {}",
					e.getMessage());
		}
	}

	public void initReporteDevolucionDiaria() {
		try {
			reporteDevolucionDiaria = businessDelegate.getReporteDevolucionDiaria();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_REPORTE_DEVOLUCION_DIARIA"));
			log.error(
					"=== Consulta de reporte de devolucion diaria: Fallo la consulta del reporte. Se ha producido un error: {}",
					e.getMessage());
		}
	}

	public void initReporteCompraDiaria() {
		try {
			reporteCompraDiaria = businessDelegate.getReporteCompraDiaria();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_REPORTE_COMPRA_DIARIA"));
			log.error(
					"=== Consulta de reporte de compra diaria: Fallo la consulta del reporte. Se ha producido un error: {}",
					e.getMessage());
		}
	}

	public void initReporteVentaMensual() {
		try {
			reporteVentaMensuales = businessDelegate.getReporteVentaMensual();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_REPORTE_VENTA_MENSUAL"));
			log.error(
					"=== Consulta de reporte de venta mensual: Fallo la consulta del reporte. Se ha producido un error: {}",
					e.getMessage());
		}
	}

	public void initReporteDevolucionMensual() {
		try {
			reporteDevolucionMensuales = businessDelegate.getReporteDevolucionMensual();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_REPORTE_DEVOLUCION_MENSUAL"));
			log.error(
					"=== Consulta de reporte de devolucion mensual: Fallo la consulta del reporte. Se ha producido un error: {}",
					e.getMessage());
		}
	}

	public void initReporteCompraMensual() {
		try {
			reporteCompraMensuales = businessDelegate.getReporteCompraMensual();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_REPORTE_COMPRA_MENSUAL"));
			log.error(
					"=== Consulta de reporte de compra mensual: Fallo la consulta del reporte. Se ha producido un error: {}",
					e.getMessage());
		}
	}

	public void initReportesMensuales() {
		for (ReporteMensualEnum reporteMensualEnumTemp : ReporteMensualEnum.values()) {
			reporteMensuales.add(new SelectItem(reporteMensualEnumTemp, reporteMensualEnumTemp.getReporte()));
		}
	}

	public void initProveedores() {
		try {
			proveedoresApp = businessDelegate.getProveedores();
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_CONSULTA_PROVEEDORES"));
			log.error("=== Consulta de Proveedores: Fallo la consulta de los proveedores", e);
		}
	}

	public void graficarReporteMensual() {
		if (reporteMensualEnum.equals(ReporteMensualEnum.VENTAS)) {
			barChartModel = barModelVentas;
		} else if (reporteMensualEnum.equals(ReporteMensualEnum.DEVOLUCIONES)) {
			barChartModel = barModelDevoluciones;
		} else {
			barChartModel = barModelCompras;
		}
	}

	public void initBarCharts() {
		ChartSeries ventas = configurationBarChart(LABEL_CHART_VENTAS, reporteVentaMensuales);
		ChartSeries devoluciones = configurationBarChart(LABEL_CHART_DEVOLUCIONES, reporteDevolucionMensuales);
		ChartSeries compras = configurationBarChart(LABEL_CHART_COMPRAS, reporteCompraMensuales);

		barModelVentas = configurationBarModel(ventas, ReporteMensualEnum.VENTAS, "ne", false, "F15A29",
				EXTENDER_CHART);
		barModelDevoluciones = configurationBarModel(devoluciones, ReporteMensualEnum.DEVOLUCIONES, "ne", false,
				"8DC63F", EXTENDER_CHART);
		barModelCompras = configurationBarModel(compras, ReporteMensualEnum.COMPRAS, "ne", false, "C1E1F4",
				EXTENDER_CHART);
	}

	public ChartSeries configurationBarChart(String labelChart, List<?> reporteMensuales) {
		ChartSeries chartSeries = new ChartSeries();
		chartSeries.setLabel(labelChart);

		if (!reporteMensuales.isEmpty()) {
			if (reporteMensuales.get(0) instanceof ReporteVentaMensual) {
				for (Object reporteVentaMensual : reporteMensuales) {
					chartSeries.set(((ReporteVentaMensual) reporteVentaMensual).getDiaMes(),
							((ReporteVentaMensual) reporteVentaMensual).getValorTotal());
				}
			} else if (reporteMensuales.get(0) instanceof ReporteDevolucionMensual) {
				for (Object reporteDevolucionMensual : reporteMensuales) {
					chartSeries.set(((ReporteDevolucionMensual) reporteDevolucionMensual).getDiaMes(),
							((ReporteDevolucionMensual) reporteDevolucionMensual).getValorTotal());
				}
			} else if (reporteMensuales.get(0) instanceof ReporteCompraMensual) {
				for (Object reporteCompraMensual : reporteMensuales) {
					chartSeries.set(((ReporteCompraMensual) reporteCompraMensual).getDiaMes(),
							((ReporteCompraMensual) reporteCompraMensual).getValorTotal());
				}
			}
		}
		return chartSeries;

	}

	public BarChartModel configurationBarModel(ChartSeries series, ReporteMensualEnum title, String legendPosition,
			boolean showPointLabels, String seriesColors, String extender) {
		BarChartModel barChartModelTemp = new BarChartModel();

		barChartModelTemp.addSeries(series);
		barChartModelTemp.setTitle(title.getReporte());
		barChartModelTemp.setZoom(true);
		barChartModelTemp.setLegendPosition(legendPosition);
		barChartModelTemp.setShowPointLabels(showPointLabels);
		barChartModelTemp.setSeriesColors(seriesColors);
		barChartModelTemp.setExtender(extender);

		Axis xAxis = barChartModelTemp.getAxis(AxisType.X);
		xAxis.setLabel(LABEL_AXIS_X_CHART);

		Axis yAxis = barChartModelTemp.getAxis(AxisType.Y);
		yAxis.setLabel(LABEL_AXIS_Y_CHART);
		yAxis.setMin(0);

		return barChartModelTemp;
	}

	public void actionCambiarPass() {
		showDialogCambiarPassword = true;
	}

	public void actionCancelar() {
		showDialogCambiarPassword = false;
	}

	public void actionActualizarPassword() {
		try {
			passwordOld = new Encrypt().encrypt(passwordOld);
			if (passwordOld.equals(usuarioApp.getPassword())) {
				usuarioApp.setPassword(new Encrypt().encrypt(passwordNew));
				businessDelegate.update(usuarioApp);
				showDialogCambiarPassword = false;
				log.info("=== Actualizacion de usuario: Usuario actualizado. Id={}, descripcion={} === ",
						usuarioApp.getIdUsuarioApp(), usuarioApp.getNombre());
				addInfoMessage(properties.getParametroString("MSG_USUARIO_ACTUALIZADO"));
			} else {
				addErrorMessage(properties.getParametroString("MSG_ERROR_PASSWORD_INCORRECTO"));
			}
		} catch (Exception e) {
			addErrorMessage(properties.getParametroString("MSG_ERROR_ACTUALIZACION_USUARIO"));
			log.error(
					"=== Actualizacion de usuario: Fallo la actualizacion del usuario {}. Se ha producido un error: {}",
					usuarioApp.getIdUsuarioApp(), e.getMessage());
		}
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
	 * @return the usuarioApp
	 */
	public UsuarioApp getUsuarioApp() {
		return usuarioApp;
	}

	/**
	 * @param usuarioApp the usuarioApp to set
	 */
	public void setUsuarioApp(UsuarioApp usuarioApp) {
		this.usuarioApp = usuarioApp;
	}

	/**
	 * @return the loginApp
	 */
	public LoginApp getLoginApp() {
		return loginApp;
	}

	/**
	 * @param loginApp the loginApp to set
	 */
	public void setLoginApp(LoginApp loginApp) {
		this.loginApp = loginApp;
	}

	/**
	 * @return the reporteVentaDiaria
	 */
	public ReporteVentaDiaria getReporteVentaDiaria() {
		return reporteVentaDiaria;
	}

	/**
	 * @param reporteVentaDiaria the reporteVentaDiaria to set
	 */
	public void setReporteVentaDiaria(ReporteVentaDiaria reporteVentaDiaria) {
		this.reporteVentaDiaria = reporteVentaDiaria;
	}

	/**
	 * @return the reporteDevolucionDiaria
	 */
	public ReporteDevolucionDiaria getReporteDevolucionDiaria() {
		return reporteDevolucionDiaria;
	}

	/**
	 * @param reporteDevolucionDiaria the reporteDevolucionDiaria to set
	 */
	public void setReporteDevolucionDiaria(ReporteDevolucionDiaria reporteDevolucionDiaria) {
		this.reporteDevolucionDiaria = reporteDevolucionDiaria;
	}

	/**
	 * @return the reporteCompraDiaria
	 */
	public ReporteCompraDiaria getReporteCompraDiaria() {
		return reporteCompraDiaria;
	}

	/**
	 * @param reporteCompraDiaria the reporteCompraDiaria to set
	 */
	public void setReporteCompraDiaria(ReporteCompraDiaria reporteCompraDiaria) {
		this.reporteCompraDiaria = reporteCompraDiaria;
	}

	/**
	 * @return the reporteVentaMensuales
	 */
	public List<ReporteVentaMensual> getReporteVentaMensuales() {
		return reporteVentaMensuales;
	}

	/**
	 * @param reporteVentaMensuales the reporteVentaMensuales to set
	 */
	public void setReporteVentaMensuales(List<ReporteVentaMensual> reporteVentaMensuales) {
		this.reporteVentaMensuales = reporteVentaMensuales;
	}

	/**
	 * @return the reporteDevolucionMensuales
	 */
	public List<ReporteDevolucionMensual> getReporteDevolucionMensuales() {
		return reporteDevolucionMensuales;
	}

	/**
	 * @param reporteDevolucionMensuales the reporteDevolucionMensuales to set
	 */
	public void setReporteDevolucionMensuales(List<ReporteDevolucionMensual> reporteDevolucionMensuales) {
		this.reporteDevolucionMensuales = reporteDevolucionMensuales;
	}

	/**
	 * @return the reporteCompraMensuales
	 */
	public List<ReporteCompraMensual> getReporteCompraMensuales() {
		return reporteCompraMensuales;
	}

	/**
	 * @param reporteCompraMensuales the reporteCompraMensuales to set
	 */
	public void setReporteCompraMensuales(List<ReporteCompraMensual> reporteCompraMensuales) {
		this.reporteCompraMensuales = reporteCompraMensuales;
	}

	/**
	 * @return the barModelVentas
	 */
	public BarChartModel getBarModelVentas() {
		return barModelVentas;
	}

	/**
	 * @param barModelVentas the barModelVentas to set
	 */
	public void setBarModelVentas(BarChartModel barModelVentas) {
		this.barModelVentas = barModelVentas;
	}

	/**
	 * @return the barModelDevoluciones
	 */
	public BarChartModel getBarModelDevoluciones() {
		return barModelDevoluciones;
	}

	/**
	 * @param barModelDevoluciones the barModelDevoluciones to set
	 */
	public void setBarModelDevoluciones(BarChartModel barModelDevoluciones) {
		this.barModelDevoluciones = barModelDevoluciones;
	}

	/**
	 * @return the barModelCompras
	 */
	public BarChartModel getBarModelCompras() {
		return barModelCompras;
	}

	/**
	 * @param barModelCompras the barModelCompras to set
	 */
	public void setBarModelCompras(BarChartModel barModelCompras) {
		this.barModelCompras = barModelCompras;
	}

	/**
	 * @return the reporteMensuales
	 */
	public List<SelectItem> getReporteMensuales() {
		return reporteMensuales;
	}

	/**
	 * @param reporteMensuales the reporteMensuales to set
	 */
	public void setReporteMensuales(List<SelectItem> reporteMensuales) {
		this.reporteMensuales = reporteMensuales;
	}

	/**
	 * @return the reporteMensualEnum
	 */
	public ReporteMensualEnum getReporteMensualEnum() {
		return reporteMensualEnum;
	}

	/**
	 * @param reporteMensualEnum the reporteMensualEnum to set
	 */
	public void setReporteMensualEnum(ReporteMensualEnum reporteMensualEnum) {
		this.reporteMensualEnum = reporteMensualEnum;
	}

	/**
	 * @return the barChartModel
	 */
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	/**
	 * @param barChartModel the barChartModel to set
	 */
	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
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
	 * @return the showDialogCambiarPassword
	 */
	public boolean isShowDialogCambiarPassword() {
		return showDialogCambiarPassword;
	}

	/**
	 * @param showDialogCambiarPassword the showDialogCambiarPassword to set
	 */
	public void setShowDialogCambiarPassword(boolean showDialogCambiarPassword) {
		this.showDialogCambiarPassword = showDialogCambiarPassword;
	}

	/**
	 * @return the passwordOld
	 */
	public String getPasswordOld() {
		return passwordOld;
	}

	/**
	 * @param passwordOld the passwordOld to set
	 */
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	/**
	 * @return the passwordNew
	 */
	public String getPasswordNew() {
		return passwordNew;
	}

	/**
	 * @param passwordNew the passwordNew to set
	 */
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	/**
	 * @return the passwordReNew
	 */
	public String getPasswordReNew() {
		return passwordReNew;
	}

	/**
	 * @param passwordReNew the passwordReNew to set
	 */
	public void setPasswordReNew(String passwordReNew) {
		this.passwordReNew = passwordReNew;
	}

	/**
	 * @return the proveedoresApp
	 */
	public List<ProveedorApp> getProveedoresApp() {
		return proveedoresApp;
	}

	/**
	 * @param proveedoresApp the proveedoresApp to set
	 */
	public void setProveedoresApp(List<ProveedorApp> proveedoresApp) {
		this.proveedoresApp = proveedoresApp;
	}

	/**
	 * @return the proveedorApp
	 */
	public ProveedorApp getProveedorApp() {
		return proveedorApp;
	}

	/**
	 * @param proveedorApp the proveedorApp to set
	 */
	public void setProveedorApp(ProveedorApp proveedorApp) {
		this.proveedorApp = proveedorApp;
	}

}
