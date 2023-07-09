package co.com.juan.invbill.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteCompraMensual;

/**
 * @author Juan Felipe
 */
public interface IReporteCompraMensualLogic {

    List<ReporteCompraMensual> getReporteCompraMensual(Date date) throws EntityException;

}
