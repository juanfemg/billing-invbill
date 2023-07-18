package co.com.juan.invbill.report.factory.impl;

import co.com.juan.invbill.report.factory.Exporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.File;

/**
 * @author Juan Felipe
 */
public class PrintServiceExporter implements Exporter {
    @Override
    public SimpleExporterConfiguration getConfiguration(PrintService... printService) {
        if (printService != null) {
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(new Copies(1));
            SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
            configuration.setPrintService(printService[0]);
            configuration.setDisplayPageDialog(Boolean.FALSE);
            configuration.setDisplayPrintDialog(Boolean.FALSE);
            configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
            return configuration;
        }
        return null;
    }

    @Override
    public void exportReport(SimpleExporterConfiguration configuration, JasperPrint jasperPrint, File... file) throws JRException {
        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setConfiguration((SimplePrintServiceExporterConfiguration) configuration);
        exporter.exportReport();
    }

}
