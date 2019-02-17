package co.com.juan.tcr.report;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lowagie.text.pdf.PdfWriter;

import co.com.juan.tcr.model.AppConfig;
import co.com.juan.tcr.util.ParameterApp;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Controller("ReportController")
public class ReportController implements IReportController {

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);
	private final String SUFFIX_PDF = ".PDF";
	private final String METADATA_AUTHOR = "Juan Felipe Mosquera";
	private PrintService printService;
	private Connection connection;
	private InputStream file;
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;

	@Resource
	private DataSource dataSource;

	@Override
	public boolean getDefaultPrinter() {
		PrintService[] printServices;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		AppConfig appConfig = (AppConfig) session.getAttribute(ParameterApp.IMPRESORA_PREDETERMINADA.toString());

		if (appConfig.getValor() == null)
			return false;
		else {
			printServices = PrinterJob.lookupPrintServices();

			for (PrintService printServiceLook : printServices) {
				if (printServiceLook.getName().equalsIgnoreCase(appConfig.getValor())) {
					printService = printServiceLook;
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public InputStream getReport(String reportName, Map<String, Object> parameters)
			throws JRException, SQLException, IOException {
		InputStream stream;
		File file;
		JRPdfExporter exporter;
		SimplePdfExporterConfiguration configuration;

		try {
			prepareReport(reportName, parameters);

			file = File.createTempFile(reportName, SUFFIX_PDF);

			exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));

			configuration = new SimplePdfExporterConfiguration();
			configuration.setMetadataAuthor(METADATA_AUTHOR);
			configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);

			exporter.setConfiguration(configuration);
			exporter.exportReport();

			stream = new FileInputStream(file);
		} catch (SQLException se) {
			log.error("An error has occurred obtaining the connection", se);
			throw se;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException se) {
					log.error("An error has occurred closing the connection", se);
				}
			}
		}

		return stream;
	}

	@Override
	public void printReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException {
		JRPrintServiceExporter exporter;
		PrintRequestAttributeSet printRequestAttributeSet;
		SimplePrintServiceExporterConfiguration configuration;

		try {
			prepareReport(reportName, parameters);

			printRequestAttributeSet = new HashPrintRequestAttributeSet();
			printRequestAttributeSet.add(new Copies(1));

			exporter = new JRPrintServiceExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

			configuration = new SimplePrintServiceExporterConfiguration();
			configuration.setPrintService(printService);
			configuration.setDisplayPageDialog(Boolean.FALSE);
			configuration.setDisplayPrintDialog(Boolean.FALSE);
			configuration.setPrintRequestAttributeSet(printRequestAttributeSet);

			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} catch (SQLException se) {
			log.error("An error has occurred obtaining the connection", se);
			throw se;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException se) {
					log.error("An error has occurred closing the connection", se);
				}
			}
		}
	}

	@Override
	public void prepareReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException {
		connection = dataSource.getConnection();

		file = ReportController.class.getResourceAsStream("/reports/" + reportName + ".jrxml");

		jasperReport = JasperCompileManager.compileReport(file);
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	}

}
