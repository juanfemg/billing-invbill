package co.com.juan.invbill.report;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Juan Felipe
 * 
 */
public interface IReportController {

	public boolean getDefaultPrinter();

	public void prepareReport(String reportName, Map<String, Object> parameters, boolean compiled)
			throws JRException, SQLException;

	public InputStream getReportPdf(String reportName, Map<String, Object> parameters)
			throws JRException, SQLException, IOException;

	public InputStream getReportXls(String reportName, Map<String, Object> parameters)
			throws JRException, SQLException, IOException;

	public InputStream getReportWithSubReportsPdf(String reportName, Map<String, Object> parameters,
			List<String> subReportNames) throws JRException, SQLException, IOException;

	public InputStream getReportWithSubReportsXls(String reportName, Map<String, Object> parameters,
			List<String> subReportNames) throws JRException, SQLException, IOException;

	public void printReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException;

	public void printReportWithSubReports(String reportName, Map<String, Object> parameters,
			List<String> subReportNames) throws JRException, SQLException;
}
