package co.com.juan.invbill.report.factory.impl;

import co.com.juan.invbill.report.factory.Exporter;
import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;

import javax.print.PrintService;
import java.io.File;

/**
 * @author Juan Felipe
 */
public class PdfExporter implements Exporter {

    private static final String METADATA_AUTHOR = "Juan Felipe Mosquera";

    @Override
    public SimpleExporterConfiguration getConfiguration(PrintService... printService) {
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setMetadataAuthor(METADATA_AUTHOR);
        configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
        return configuration;
    }

    @Override
    public void exportReport(SimpleExporterConfiguration configuration, JasperPrint jasperPrint, File... file) throws JRException {
        if (file != null) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file[0]));
            exporter.setConfiguration((SimplePdfExporterConfiguration) configuration);
            exporter.exportReport();
        }
    }

}
