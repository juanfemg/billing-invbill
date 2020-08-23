/**
 * 
 */
package co.com.juan.invbill.dto.control;

import java.util.Date;

import co.com.juan.invbill.dto.ReporteDevolucionDiaria;

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
