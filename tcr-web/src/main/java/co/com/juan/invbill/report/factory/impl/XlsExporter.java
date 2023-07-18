package co.com.juan.invbill.report.factory.impl;

import co.com.juan.invbill.report.factory.Exporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.*;

import javax.print.PrintService;
import java.io.File;

/**
 * @author Juan Felipe
 */
public class XlsExporter implements Exporter {

    @Override
    public SimpleExporterConfiguration getConfiguration(PrintService... printService) {
        SimpleXlsExporterConfiguration configuration = new SimpleXlsExporterConfiguration();
        configuration.setCreateCustomPalette(true);
        configuration.setKeepWorkbookTemplateSheets(true);
        configuration.setOverrideHints(true);
        return configuration;
    }

    @Override
    public void exportReport(SimpleExporterConfiguration configuration, JasperPrint jasperPrint, File... file) throws JRException {
        if (file != null) {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file[0]));
            exporter.setConfiguration((SimpleXlsExporterConfiguration) configuration);
            exporter.exportReport();
        }
    }

}
