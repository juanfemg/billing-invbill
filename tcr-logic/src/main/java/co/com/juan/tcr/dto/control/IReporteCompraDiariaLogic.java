/**
 * 
 */
package co.com.juan.tcr.dto.control;

import java.util.Date;

import co.com.juan.tcr.dto.ReporteCompraDiaria;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteCompraDiariaLogic {

	/**
	 * Get ReporteCompraDiaria objects
	 * 
	 */
	public ReporteCompraDiaria getReporteCompraDiaria(Date date);

}
