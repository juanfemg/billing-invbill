package co.com.juan.invbill.presentation.backingbeans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.delegate.businessdelegate.IFacturaDelegate;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.TipoPeriodo;
import co.com.juan.invbill.report.IReportController;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 *
 */
@ManagedBean(name = "reporteVentas")
@ViewScoped
public class ReporteVentasView implements Serializable {

	private static final long serialVersionUID = -8874793963570662678L;
	private static final String FILE_MESSAGES = "bundles.msg_ReporteVentas";
	private static final String REPORTE_VENTAS = "reportVentas";
	private static final String SUB_REPORTE_VENTAS_ANUAL = "chartVentasAnual";
	private static final String SUFFIX_PDF = ".PDF";
	private static final String STREAM_CONTENT_TYPE = "application/pdf";
	private static final String PROPERTY_NAME_FECHA_CREACION = "fechaCreacion";
	private static final Integer NUMERO_MESES_ANIO = new Integer(12);
	private static final Logger log = LoggerFactory.getLogger(ReporteVentasView.class);

	@ManagedProperty(value = "#{businessDelegate}")
	private transient IClienteDelegate businessDelegate;

	@ManagedProperty(value = "#{configDelegate}")
	private IConfigDelegate configDelegate;

	public IConfigDelegate getConfigDelegate() {
		return configDelegate;
	}

	public void setConfigDelegate(IConfigDelegate configDelegate) {
		this.configDelegate = configDelegate;
	}

	public IFacturaDelegate getFacturaDelegate() {
		return facturaDelegate;
	}

	public void setFacturaDelegate(IFacturaDelegate facturaDelegate) {
		this.facturaDelegate = facturaDelegate;
	}

	@ManagedProperty(value = "#{facturaDelegate}")
	private transient IFacturaDelegate facturaDelegate;

	@ManagedProperty(value = "#{ReportController}")
	private transient IReportController reportController;

	private List<TipoPeriodo> tipoPeriodo;
	private TipoPeriodo tipoPeriodoFiltro;
	private List<SelectItem> tiposPeriodo;
	private Date facturaCaberaMaxFechaCreacion;
	private Date facturaCaberaMinFechaCreacion;
	private Integer anio;
	private Integer anioFiltro;
	private List<SelectItem> anios;
	private Calendar calendarMax;
	private Calendar calendarMin;
	private List<SelectItem> semestres;
	private List<SelectItem> trimestres;
	private List<SelectItem> meses;
	private List<Integer> semestresFiltro;
	private List<Integer> trimestresFiltro;
	private List<Integer> mesesFiltro;
	private StreamedContent content;
	private boolean showSemestres;
	private boolean showTrimestres;
	private boolean showMeses;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public ReporteVentasView() {
		super();
	}

	@PostConstruct
	public void init() {
		tipoPeriodo = new ArrayList<>();
		tipoPeriodoFiltro = new TipoPeriodo();
		tiposPeriodo = new ArrayList<>();
		facturaCaberaMaxFechaCreacion = new Date();
		facturaCaberaMinFechaCreacion = new Date();
		anios = new ArrayList<>();
		calendarMax = Calendar.getInstance();
		calendarMin = Calendar.getInstance();
		anio = new Integer(0);
		anioFiltro = new Integer(0);
		semestres = new ArrayList<>();
		trimestres = new ArrayList<>();
		meses = new ArrayList<>();
		semestresFiltro = new ArrayList<>();
		trimestresFiltro = new ArrayList<>();
		mesesFiltro = new ArrayList<>();
		showSemestres = false;
		showTrimestres = false;
		showMeses = false;
		initAnios();
		initTiposPeriodo();
	}

	public void initTiposPeriodo() {
		try {
			tipoPeriodo = this.configDelegate.getTiposPeriodo();

			for (TipoPeriodo tipoPeriodoTemp : tipoPeriodo) {
				tiposPeriodo.add(new SelectItem(tipoPeriodoTemp, tipoPeriodoTemp.getPeriodo()));
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error("=== Consulta de Tipos de Periodos : Fallo la consulta de los periodos" + ". ERROR : "
					+ e.getMessage());
		}
	}

	public void initAnios() {
		try {
			facturaCaberaMaxFechaCreacion = (Date) this.facturaDelegate
					.getMaximaFacturaCabeceraByPropertyName(PROPERTY_NAME_FECHA_CREACION);

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error(
					"=== Consulta de Factura Cabecera : Fallo la consulta del maximo de la fecha de creacion de las facturas"
							+ ". ERROR : " + e.getMessage());
		}

		try {
			facturaCaberaMinFechaCreacion = (Date) this.facturaDelegate
					.getMinimaFacturaCabeceraByPropertyName(PROPERTY_NAME_FECHA_CREACION);

		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			log.error(
					"=== Consulta de Factura Cabecera : Fallo la consulta del minimo de la fecha de creacion de las facturas"
							+ ". ERROR : " + e.getMessage());
		}

		if (facturaCaberaMinFechaCreacion != null && facturaCaberaMaxFechaCreacion != null) {
			calendarMin.setTime(facturaCaberaMinFechaCreacion);
			calendarMax.setTime(facturaCaberaMaxFechaCreacion);
			anio = calendarMin.get(Calendar.YEAR);

			do {
				anios.add(new SelectItem(anio));
				calendarMin.set(Calendar.YEAR, anio++);
			} while (calendarMin.get(Calendar.YEAR) < calendarMax.get(Calendar.YEAR));

		}
	}

	public void initPeriodos() {
		Integer rangoPeriodos = new Integer(0);

		if (tipoPeriodoFiltro != null) {
			rangoPeriodos = NUMERO_MESES_ANIO / tipoPeriodoFiltro.getNumeroMeses();

			if (rangoPeriodos == 2) {
				semestres.clear();
				trimestres.clear();
				meses.clear();
				initSemestres(rangoPeriodos);
			} else if (rangoPeriodos == 4) {
				semestres.clear();
				trimestres.clear();
				meses.clear();
				initTrimestres(rangoPeriodos);
			} else if (rangoPeriodos == 12) {
				semestres.clear();
				trimestres.clear();
				meses.clear();
				initMeses(rangoPeriodos);
			} else {
				showSemestres = false;
				showTrimestres = false;
				showMeses = false;
			}
		}
	}

	public void initSemestres(Integer numeroSemestres) {
		for (int i = 1; i <= numeroSemestres; i++) {
			semestres.add(new SelectItem(i, "Semestre " + i));
		}

		showSemestres = true;
		showTrimestres = false;
		showMeses = false;
		trimestresFiltro.clear();
		mesesFiltro.clear();
	}

	public void initTrimestres(Integer numeroTrimestres) {
		for (int i = 1; i <= numeroTrimestres; i++) {
			trimestres.add(new SelectItem(i, "Trimestre " + i));
		}

		showSemestres = false;
		showTrimestres = true;
		showMeses = false;
		semestresFiltro.clear();
		mesesFiltro.clear();
	}

	public void initMeses(Integer numeroMeses) {
		Calendar calendar = Calendar.getInstance();

		for (int i = 0; i < numeroMeses; i++) {
			calendar.set(Calendar.MONTH, i);
			meses.add(new SelectItem((i + 1),
					calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())));
		}

		showSemestres = false;
		showTrimestres = false;
		showMeses = true;
		semestresFiltro.clear();
		trimestresFiltro.clear();
	}

	public void actionGenerarReporte() {
		InputStream stream;
		List<String> subReports;
		Map<String, Integer> rangoSemestre1 = new HashMap<>();
		Map<String, Integer> rangoSemestre2 = new HashMap<>();
		Map<String, Integer> rangoTrimestre1 = new HashMap<>();
		Map<String, Integer> rangoTrimestre2 = new HashMap<>();
		Map<String, Integer> rangoTrimestre3 = new HashMap<>();
		Map<String, Integer> rangoTrimestre4 = new HashMap<>();

		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("ANIO", anioFiltro);

			if (semestresFiltro.isEmpty() && trimestresFiltro.isEmpty() && mesesFiltro.isEmpty()) {
				filterEmpty(semestresFiltro, 2);
				filterEmpty(trimestresFiltro, 4);
				filterEmpty(mesesFiltro, 12);
			} else if (mesesFiltro.isEmpty()) {
				mesesFiltro.add(0);
			}

			parameters.put("RANGO_SEMESTRE_1", filterNestedTypeMap(rangoSemestre1, semestresFiltro, 1, 5));
			parameters.put("RANGO_SEMESTRE_2", filterNestedTypeMap(rangoSemestre2, semestresFiltro, 2, 5));
			parameters.put("RANGO_TRIMESTRE_1", filterNestedTypeMap(rangoTrimestre1, trimestresFiltro, 1, 2));
			parameters.put("RANGO_TRIMESTRE_2", filterNestedTypeMap(rangoTrimestre2, trimestresFiltro, 2, 2));
			parameters.put("RANGO_TRIMESTRE_3", filterNestedTypeMap(rangoTrimestre3, trimestresFiltro, 3, 2));
			parameters.put("RANGO_TRIMESTRE_4", filterNestedTypeMap(rangoTrimestre4, trimestresFiltro, 4, 2));
			parameters.put("MESES", mesesFiltro);
			parameters.put("LOGO_APP", ImageIO.read(getClass().getResource("/images/logoApp.png")));

			subReports = new ArrayList<>();
			subReports.add(SUB_REPORTE_VENTAS_ANUAL);

			stream = reportController.getReportWithSubReportsPdf(REPORTE_VENTAS, parameters, subReports);

			if (stream != null) {
				content = new DefaultStreamedContent(stream, STREAM_CONTENT_TYPE, REPORTE_VENTAS.concat(SUFFIX_PDF));
			}

			mesesFiltro.clear();
			semestresFiltro.clear();
			trimestresFiltro.clear();

			addInfoMessage(properties.getParameterByKey("MSG_REPORTE_GENERADO"));
		} catch (Exception e) {
			addErrorMessage(properties.getParameterByKey("MSG_ERROR_GENERACION_REPORTE"));
			log.error("=== Generacion Reporte Productos : Fallo la generacion del reporte", e);
		}
	}

	public List<Integer> filterEmpty(List<Integer> periodo, Integer cantidadPeriodos) {
		if (periodo.isEmpty()) {
			for (int count = 1; count <= cantidadPeriodos; count++) {
				periodo.add(count);
			}
		}
		return periodo;

	}

	public Map<String, Integer> filterNestedTypeMap(Map<String, Integer> hash, List<Integer> periodos, Integer periodo,
			Integer factor) {
		Object maxValue = hash.get("MAX");
		Object minValue = hash.get("MIN");

		if (maxValue == null && minValue == null) {
			if (periodos.contains(periodo)) {
				hash.put("MIN", (periodo + (factor * (periodo) - factor)));
				hash.put("MAX", (periodo * (factor + 1)));
			}
		}

		return hash;
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

	public void addWarnMessage(String summary, String clientId) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
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

	/**
	 * @return the tipoPeriodo
	 */
	public List<TipoPeriodo> getTipoPeriodo() {
		return tipoPeriodo;
	}

	/**
	 * @param tipoPeriodo the tipoPeriodo to set
	 */
	public void setTipoPeriodo(List<TipoPeriodo> tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	/**
	 * @return the tiposPeriodo
	 */
	public List<SelectItem> getTiposPeriodo() {
		return tiposPeriodo;
	}

	/**
	 * @param tiposPeriodo the tiposPeriodo to set
	 */
	public void setTiposPeriodo(List<SelectItem> tiposPeriodo) {
		this.tiposPeriodo = tiposPeriodo;
	}

	/**
	 * @return the facturaCaberaMaxFechaCreacion
	 */
	public Date getFacturaCaberaMaxFechaCreacion() {
		return facturaCaberaMaxFechaCreacion;
	}

	/**
	 * @param facturaCaberaMaxFechaCreacion the facturaCaberaMaxFechaCreacion to set
	 */
	public void setFacturaCaberaMaxFechaCreacion(Date facturaCaberaMaxFechaCreacion) {
		this.facturaCaberaMaxFechaCreacion = facturaCaberaMaxFechaCreacion;
	}

	/**
	 * @return the facturaCaberaMinFechaCreacion
	 */
	public Date getFacturaCaberaMinFechaCreacion() {
		return facturaCaberaMinFechaCreacion;
	}

	/**
	 * @param facturaCaberaMinFechaCreacion the facturaCaberaMinFechaCreacion to set
	 */
	public void setFacturaCaberaMinFechaCreacion(Date facturaCaberaMinFechaCreacion) {
		this.facturaCaberaMinFechaCreacion = facturaCaberaMinFechaCreacion;
	}

	/**
	 * @return the anios
	 */
	public List<SelectItem> getAnios() {
		return anios;
	}

	/**
	 * @param anios the anios to set
	 */
	public void setAnios(List<SelectItem> anios) {
		this.anios = anios;
	}

	/**
	 * @return the calendarMax
	 */
	public Calendar getCalendarMax() {
		return calendarMax;
	}

	/**
	 * @param calendarMax the calendarMax to set
	 */
	public void setCalendarMax(Calendar calendarMax) {
		this.calendarMax = calendarMax;
	}

	/**
	 * @return the calendarMin
	 */
	public Calendar getCalendarMin() {
		return calendarMin;
	}

	/**
	 * @param calendarMin the calendarMin to set
	 */
	public void setCalendarMin(Calendar calendarMin) {
		this.calendarMin = calendarMin;
	}

	/**
	 * @return the anio
	 */
	public Integer getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	/**
	 * @return the tipoPeriodoFiltro
	 */
	public TipoPeriodo getTipoPeriodoFiltro() {
		return tipoPeriodoFiltro;
	}

	/**
	 * @param tipoPeriodoFiltro the tipoPeriodoFiltro to set
	 */
	public void setTipoPeriodoFiltro(TipoPeriodo tipoPeriodoFiltro) {
		this.tipoPeriodoFiltro = tipoPeriodoFiltro;
	}

	/**
	 * @return the anioFiltro
	 */
	public Integer getAnioFiltro() {
		return anioFiltro;
	}

	/**
	 * @param anioFiltro the anioFiltro to set
	 */
	public void setAnioFiltro(Integer anioFiltro) {
		this.anioFiltro = anioFiltro;
	}

	/**
	 * @return the showSemestres
	 */
	public boolean isShowSemestres() {
		return showSemestres;
	}

	/**
	 * @param showSemestres the showSemestres to set
	 */
	public void setShowSemestres(boolean showSemestres) {
		this.showSemestres = showSemestres;
	}

	/**
	 * @return the showTrimestres
	 */
	public boolean isShowTrimestres() {
		return showTrimestres;
	}

	/**
	 * @param showTrimestres the showTrimestres to set
	 */
	public void setShowTrimestres(boolean showTrimestres) {
		this.showTrimestres = showTrimestres;
	}

	/**
	 * @return the showMeses
	 */
	public boolean isShowMeses() {
		return showMeses;
	}

	/**
	 * @param showMeses the showMeses to set
	 */
	public void setShowMeses(boolean showMeses) {
		this.showMeses = showMeses;
	}

	/**
	 * @return the semestres
	 */
	public List<SelectItem> getSemestres() {
		return semestres;
	}

	/**
	 * @param semestres the semestres to set
	 */
	public void setSemestres(List<SelectItem> semestres) {
		this.semestres = semestres;
	}

	/**
	 * @return the trimestres
	 */
	public List<SelectItem> getTrimestres() {
		return trimestres;
	}

	/**
	 * @param trimestres the trimestres to set
	 */
	public void setTrimestres(List<SelectItem> trimestres) {
		this.trimestres = trimestres;
	}

	/**
	 * @return the meses
	 */
	public List<SelectItem> getMeses() {
		return meses;
	}

	/**
	 * @param meses the meses to set
	 */
	public void setMeses(List<SelectItem> meses) {
		this.meses = meses;
	}

	/**
	 * @return the semestresFiltro
	 */
	public List<Integer> getSemestresFiltro() {
		return semestresFiltro;
	}

	/**
	 * @param semestresFiltro the semestresFiltro to set
	 */
	public void setSemestresFiltro(List<Integer> semestresFiltro) {
		this.semestresFiltro = semestresFiltro;
	}

	/**
	 * @return the trimestresFiltro
	 */
	public List<Integer> getTrimestresFiltro() {
		return trimestresFiltro;
	}

	/**
	 * @param trimestresFiltro the trimestresFiltro to set
	 */
	public void setTrimestresFiltro(List<Integer> trimestresFiltro) {
		this.trimestresFiltro = trimestresFiltro;
	}

	/**
	 * @return the mesesFiltro
	 */
	public List<Integer> getMesesFiltro() {
		return mesesFiltro;
	}

	/**
	 * @param mesesFiltro the mesesFiltro to set
	 */
	public void setMesesFiltro(List<Integer> mesesFiltro) {
		this.mesesFiltro = mesesFiltro;
	}

	/**
	 * @return the content
	 */
	public StreamedContent getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(StreamedContent content) {
		this.content = content;
	}

}
