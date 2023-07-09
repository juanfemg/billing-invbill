package co.com.juan.invbill.control;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteVentaMensual;

import java.util.Date;
import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IReporteVentaMensualLogic {

    List<ReporteVentaMensual> getReporteVentaMensual(Date date) throws EntityException;

}
