package co.com.juan.invbill.report.factory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleExporterConfiguration;

import javax.print.PrintService;
import java.io.File;

/**
 * @author Juan Felipe
 */
public interface Exporter {

    SimpleExporterConfiguration getConfiguration(PrintService... printService);

    void exportReport(SimpleExporterConfiguration configuration, JasperPrint jasperPrint, File... file) throws JRException;

}
