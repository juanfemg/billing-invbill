package co.com.juan.invbill.control;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteVentaDiaria;

import java.util.Date;

/**
 * @author Juan Felipe
 */
public interface IReporteVentaDiariaLogic {

    ReporteVentaDiaria getReporteVentaDiaria(Date date) throws EntityException;

}
