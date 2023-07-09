package co.com.juan.invbill.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteDevolucionMensual;

/**
 * @author Juan Felipe
 */
public interface IReporteDevolucionMensualLogic {

    List<ReporteDevolucionMensual> getReporteDevolucionMensual(Date date) throws EntityException;

}
