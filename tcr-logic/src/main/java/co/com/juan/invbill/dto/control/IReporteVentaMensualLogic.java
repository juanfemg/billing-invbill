package co.com.juan.invbill.dto.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.dto.ReporteVentaMensual;

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
