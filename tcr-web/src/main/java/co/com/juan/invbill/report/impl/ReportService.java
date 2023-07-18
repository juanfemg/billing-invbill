package co.com.juan.invbill.report.impl;

import co.com.juan.invbill.enums.ParameterEnum;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.report.IReportService;
import co.com.juan.invbill.report.ThrowingConsumer;
import co.com.juan.invbill.report.factory.Exporter;
import co.com.juan.invbill.report.factory.ExporterEnum;
import co.com.juan.invbill.report.factory.ExporterFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.print.PrintService;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Juan Felipe
 */
@Service
public class ReportService implements IReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);
    private static final String COMPILED_FILE_EXT = "jasper";
    private static final String LAYOUT_FILE_EXT = "jrxml";
    private PrintService printService;
    private Connection connection;
    private JasperPrint jasperPrint;
    private File file;

    @Resource
    private DataSource dataSource;

    static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, JRException> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (JRException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @Override
    public PrintService lookUpDefaultPrinter() {
        if (this.printService == null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            AppConfig appConfig = (AppConfig) session.getAttribute(ParameterEnum.IMPRESORA_PREDETERMINADA.name());

            if (appConfig.getValor() != null) {
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                this.printService = Arrays.stream(printServices).filter(printService -> printService.getName().equalsIgnoreCase(appConfig.getValor())).findFirst().orElse(null);
            }
        }

        return this.printService;
    }

    @Override
    public InputStream getReportPdfWithSubReports(String reportName, Map<String, Object> parameters,
                                                  List<String> subReportNames) throws JRException, SQLException, IOException {
        try {
            this.prepareReportWithSubReports(reportName, parameters, subReportNames);
            return this.getReportPdf(reportName, parameters);
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
    public InputStream getReportPdf(String reportName, Map<String, Object> parameters)
            throws JRException, SQLException, IOException {
        try {
            this.prepareReport(reportName, parameters);
            Exporter exporter = ExporterFactory.getExporter(ExporterEnum.PDF.name());

            if (exporter != null) {
                SimplePdfExporterConfiguration configuration = (SimplePdfExporterConfiguration) exporter.getConfiguration();
                exporter.exportReport(configuration, this.jasperPrint, this.file);
                return Files.newInputStream(this.file.toPath());
            }
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

        return null;
    }

    @Override
    public InputStream getReportXls(String reportName, Map<String, Object> parameters)
            throws JRException, SQLException, IOException {
        try {
            this.prepareReport(reportName, parameters);
            Exporter exporter = ExporterFactory.getExporter(ExporterEnum.XLS.name());

            if (exporter != null) {
                SimpleXlsExporterConfiguration configuration = (SimpleXlsExporterConfiguration) exporter.getConfiguration();
                exporter.exportReport(configuration, this.jasperPrint, this.file);
                return Files.newInputStream(file.toPath());
            }
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

        return null;
    }

    @Override
    public void printReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException, IOException {
        try {
            this.prepareReport(reportName, parameters);
            Exporter exporter = ExporterFactory.getExporter(ExporterEnum.PRINT_SERVICE.name());

            if (exporter != null) {
                SimplePrintServiceExporterConfiguration configuration = (SimplePrintServiceExporterConfiguration) exporter.getConfiguration(this.printService);
                exporter.exportReport(configuration, this.jasperPrint);
            }
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

    private void prepareReport(String reportName, Map<String, Object> parameters)
            throws JRException, SQLException, IOException {
        InputStream file = ReportService.class.getResourceAsStream(String.format("/reports/%s.%s", reportName, COMPILED_FILE_EXT));
        this.connection = this.getConnection();
        this.jasperPrint = JasperFillManager.fillReport(file, parameters, this.connection);
        this.file = File.createTempFile(reportName, null);
    }

    private void prepareReportWithSubReports(String reportName, Map<String, Object> parameters,
                                             List<String> subReportNames) throws JRException, SQLException, IOException {
        subReportNames.forEach(throwingConsumerWrapper(subReportName -> {
            InputStream fileSubReport = ReportService.class.getResourceAsStream(String.format("/reports/%s.%s", subReportName, LAYOUT_FILE_EXT));
            if (fileSubReport != null) {
                JasperReport jasperSubReport = JasperCompileManager.compileReport(fileSubReport);
                parameters.put("SUB_REPORT_PARAMETER_" + subReportName.toUpperCase(), jasperSubReport);
            }
        }));
        InputStream file = ReportService.class.getResourceAsStream(String.format("/reports/%s.%s", reportName, LAYOUT_FILE_EXT));
        JasperReport jasperReport = JasperCompileManager.compileReport(file);
        this.connection = this.getConnection();
        this.jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, this.connection);
        this.file = File.createTempFile(reportName, null);
    }

    private Connection getConnection() throws SQLException {
        return this.connection == null ? this.dataSource.getConnection() : this.connection;
    }

}
