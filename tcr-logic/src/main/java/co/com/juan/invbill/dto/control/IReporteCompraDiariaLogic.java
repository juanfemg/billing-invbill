/**
 * 
 */
package co.com.juan.invbill.dto.control;

import java.util.Date;

import co.com.juan.invbill.dto.ReporteCompraDiaria;

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
