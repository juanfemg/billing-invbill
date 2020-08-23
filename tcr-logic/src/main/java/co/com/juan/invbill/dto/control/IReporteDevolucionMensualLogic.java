package co.com.juan.invbill.dto.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.dto.ReporteDevolucionMensual;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteDevolucionMensualLogic {

	/**
	 * Get ReporteDevolucionMensual objects
	 * 
	 */
	public List<ReporteDevolucionMensual> getReporteDevolucionMensual(Date date);

}
