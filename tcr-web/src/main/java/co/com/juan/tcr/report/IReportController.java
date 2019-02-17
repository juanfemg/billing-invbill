package co.com.juan.tcr.report;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Juan Felipe
 * 
 */
public interface IReportController {

	public boolean getDefaultPrinter();

	public void prepareReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException;

	public InputStream getReport(String reportName, Map<String, Object> parameters)
			throws JRException, SQLException, IOException;

	public void printReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException;
}
