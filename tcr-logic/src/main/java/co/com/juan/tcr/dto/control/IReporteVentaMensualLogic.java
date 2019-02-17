package co.com.juan.tcr.dto.control;

import java.util.Date;
import java.util.List;

import co.com.juan.tcr.dto.ReporteVentaMensual;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteVentaMensualLogic {

	/**
	 * Get ReporteVentaMensual objects
	 * 
	 */
	public List<ReporteVentaMensual> getReporteVentaMensual(Date date);

}
