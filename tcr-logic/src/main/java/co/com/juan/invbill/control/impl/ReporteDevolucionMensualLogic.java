package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IReporteDevolucionMensualLogic;
import co.com.juan.invbill.dao.IReporteDevolucionMensualDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteDevolucionMensual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class ReporteDevolucionMensualLogic implements IReporteDevolucionMensualLogic {

    private static final Logger log = LoggerFactory.getLogger(ReporteDevolucionMensualLogic.class);
    private static final String REPORTE_DEVOLUCION_MENSUAL = "SPReporteDevolucionMensual";
    private final IReporteDevolucionMensualDao reporteDevolucionMensualDao;

    @Inject
    public ReporteDevolucionMensualLogic(IReporteDevolucionMensualDao reporteDevolucionMensualDao) {
        this.reporteDevolucionMensualDao = reporteDevolucionMensualDao;
    }

    @Override
    @Transactional
    public List<ReporteDevolucionMensual> getReporteDevolucionMensual(Date date) throws EntityException {
        List<ReporteDevolucionMensual> list;
        try {
            list = this.reporteDevolucionMensualDao.findByNamedQuery(REPORTE_DEVOLUCION_MENSUAL, date);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ReporteDevolucionMensual";
    }

}
