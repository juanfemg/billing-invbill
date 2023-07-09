/**
 *
 */
package co.com.juan.invbill.control;

import java.util.Date;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteCompraDiaria;

/**
 * @author Juan Felipe
 *
 */
public interface IReporteCompraDiariaLogic {

    ReporteCompraDiaria getReporteCompraDiaria(Date date) throws EntityException;

}
