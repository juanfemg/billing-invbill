package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IReporteDelegate {

    ReporteVentaDiaria getReporteVentaDiaria() throws EntityException;

    ReporteDevolucionDiaria getReporteDevolucionDiaria() throws EntityException;

    ReporteCompraDiaria getReporteCompraDiaria() throws EntityException;

    List<ReporteVentaMensual> getReporteVentaMensual() throws EntityException;

    List<ReporteDevolucionMensual> getReporteDevolucionMensual() throws EntityException;

    List<ReporteCompraMensual> getReporteCompraMensual() throws EntityException;
}
