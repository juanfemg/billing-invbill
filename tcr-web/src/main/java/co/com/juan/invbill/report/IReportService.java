package co.com.juan.invbill.report;

import net.sf.jasperreports.engine.JRException;

import javax.print.PrintService;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Juan Felipe
 */
public interface IReportService {

    PrintService lookUpDefaultPrinter();

    InputStream getReportPdf(String reportName, Map<String, Object> parameters)
            throws JRException, SQLException, IOException;

    InputStream getReportXls(String reportName, Map<String, Object> parameters)
            throws JRException, SQLException, IOException;

    InputStream getReportPdfWithSubReports(String reportName, Map<String, Object> parameters,
                                           List<String> subReportNames) throws JRException, SQLException, IOException;

    void printReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException, IOException;

}
