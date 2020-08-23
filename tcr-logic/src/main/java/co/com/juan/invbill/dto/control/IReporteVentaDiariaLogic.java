package co.com.juan.invbill.dto.control;

import java.util.Date;

import co.com.juan.invbill.dto.ReporteVentaDiaria;

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
