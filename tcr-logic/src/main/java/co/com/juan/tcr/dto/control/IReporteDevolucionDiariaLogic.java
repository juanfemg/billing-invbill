/**
 * 
 */
package co.com.juan.tcr.dto.control;

import java.util.Date;

import co.com.juan.tcr.dto.ReporteDevolucionDiaria;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteDevolucionDiariaLogic {

	/**
	 * Get ReporteDevolucionDiaria objects
	 * 
	 */
	public ReporteDevolucionDiaria getReporteDevolucionDiaria(Date date);

}
