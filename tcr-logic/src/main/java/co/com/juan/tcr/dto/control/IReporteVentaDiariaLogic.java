package co.com.juan.tcr.dto.control;

import java.util.Date;

import co.com.juan.tcr.dto.ReporteVentaDiaria;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteVentaDiariaLogic {

	/**
	 * Get ReporteVentaDiaria objects
	 * 
	 */
	public ReporteVentaDiaria getReporteVentaDiaria(Date date);

}
