package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IReporteDevolucionDiariaLogic;
import co.com.juan.invbill.dao.IReporteDevolucionDiariaDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteDevolucionDiaria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class ReporteDevolucionDiariaLogic implements IReporteDevolucionDiariaLogic {

    private static final Logger log = LoggerFactory.getLogger(ReporteDevolucionDiariaLogic.class);
    private static final String REPORTE_DEVOLUCION_DIARIA = "SPReporteDevolucionDiaria";
    private final IReporteDevolucionDiariaDao reporteDevolucionDiariaDao;

    @Inject
    public ReporteDevolucionDiariaLogic(IReporteDevolucionDiariaDao reporteDevolucionDiariaDao) {
        this.reporteDevolucionDiariaDao = reporteDevolucionDiariaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteDevolucionDiaria getReporteDevolucionDiaria(Date date) throws EntityException {
        ReporteDevolucionDiaria entity;
        try {
            entity = this.reporteDevolucionDiariaDao.findObjectByNamedQuery(REPORTE_DEVOLUCION_DIARIA, date);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ReporteDevolucionDiaria";
    }

}
