/**
 *
 */
package co.com.juan.invbill.control;

import java.util.Date;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteDevolucionDiaria;

/**
 * @author Juan Felipe
 */
public interface IReporteDevolucionDiariaLogic {

    ReporteDevolucionDiaria getReporteDevolucionDiaria(Date date) throws EntityException;

}
