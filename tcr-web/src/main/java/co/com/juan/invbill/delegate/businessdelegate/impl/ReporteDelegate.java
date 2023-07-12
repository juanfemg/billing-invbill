package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.*;
import co.com.juan.invbill.delegate.businessdelegate.IReporteDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class ReporteDelegate implements IReporteDelegate {

    private final IReporteVentaDiariaLogic reporteVentaDiariaLogic;
    private final IReporteDevolucionDiariaLogic reporteDevolucionDiariaLogic;
    private final IReporteCompraDiariaLogic reporteCompraDiariaLogic;
    private final IReporteVentaMensualLogic reporteVentaMensualLogic;
    private final IReporteDevolucionMensualLogic reporteDevolucionMensualLogic;
    private final IReporteCompraMensualLogic reporteCompraMensualLogic;

    @Inject
    public ReporteDelegate(IReporteVentaDiariaLogic reporteVentaDiariaLogic, IReporteDevolucionDiariaLogic reporteDevolucionDiariaLogic, IReporteCompraDiariaLogic reporteCompraDiariaLogic, IReporteVentaMensualLogic reporteVentaMensualLogic, IReporteDevolucionMensualLogic reporteDevolucionMensualLogic, IReporteCompraMensualLogic reporteCompraMensualLogic) {
        this.reporteVentaDiariaLogic = reporteVentaDiariaLogic;
        this.reporteDevolucionDiariaLogic = reporteDevolucionDiariaLogic;
        this.reporteCompraDiariaLogic = reporteCompraDiariaLogic;
        this.reporteVentaMensualLogic = reporteVentaMensualLogic;
        this.reporteDevolucionMensualLogic = reporteDevolucionMensualLogic;
        this.reporteCompraMensualLogic = reporteCompraMensualLogic;
    }

    @Override
    public ReporteVentaDiaria getReporteVentaDiaria() throws EntityException {
        return this.reporteVentaDiariaLogic.getReporteVentaDiaria(new Date());
    }

    @Override
    public ReporteDevolucionDiaria getReporteDevolucionDiaria() throws EntityException {
        return this.reporteDevolucionDiariaLogic.getReporteDevolucionDiaria(new Date());
    }

    @Override
    public ReporteCompraDiaria getReporteCompraDiaria() throws EntityException {
        return this.reporteCompraDiariaLogic.getReporteCompraDiaria(new Date());
    }

    @Override
    public List<ReporteVentaMensual> getReporteVentaMensual() throws EntityException {
        return this.reporteVentaMensualLogic.getReporteVentaMensual(new Date());
    }

    @Override
    public List<ReporteDevolucionMensual> getReporteDevolucionMensual() throws EntityException {
        return this.reporteDevolucionMensualLogic.getReporteDevolucionMensual(new Date());
    }

    @Override
    public List<ReporteCompraMensual> getReporteCompraMensual() throws EntityException {
        return this.reporteCompraMensualLogic.getReporteCompraMensual(new Date());
    }
}
