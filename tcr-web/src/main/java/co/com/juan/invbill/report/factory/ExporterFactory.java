package co.com.juan.invbill.report.factory;

import co.com.juan.invbill.report.factory.impl.PdfExporter;
import co.com.juan.invbill.report.factory.impl.PrintServiceExporter;
import co.com.juan.invbill.report.factory.impl.XlsExporter;

/**
 * @author Juan Felipe
 */
public class ExporterFactory {

    public static Exporter getExporter(String exporter) {
        if (exporter.equalsIgnoreCase(ExporterEnum.PDF.name())) {
            return new PdfExporter();
        } else if (exporter.equalsIgnoreCase(ExporterEnum.XLS.name())) {
            return new XlsExporter();
        } else if (exporter.equalsIgnoreCase(ExporterEnum.PRINT_SERVICE.name())) {
            return new PrintServiceExporter();
        }
        return null;
    }

}
