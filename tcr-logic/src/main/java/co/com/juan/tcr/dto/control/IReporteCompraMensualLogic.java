package co.com.juan.tcr.dto.control;

import java.util.Date;
import java.util.List;

import co.com.juan.tcr.dto.ReporteCompraMensual;

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
