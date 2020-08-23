package co.com.juan.invbill.dto.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.dto.ReporteCompraMensual;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteCompraMensualLogic {

	/**
	 * Get ReporteCompraMensual objects
	 * 
	 */
	public List<ReporteCompraMensual> getReporteCompraMensual(Date date);

}
