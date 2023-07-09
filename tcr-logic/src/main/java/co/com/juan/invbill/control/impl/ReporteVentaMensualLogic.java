package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IReporteVentaMensualLogic;
import co.com.juan.invbill.dao.IReporteVentaMensualDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteVentaMensual;
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
public class ReporteVentaMensualLogic implements IReporteVentaMensualLogic {

    private static final Logger log = LoggerFactory.getLogger(ReporteVentaMensualLogic.class);
    private static final String REPORTE_VENTA_MENSUAL = "SPReporteVentaMensual";
    private final IReporteVentaMensualDao reporteVentaMensualDao;

    @Inject
    public ReporteVentaMensualLogic(IReporteVentaMensualDao reporteVentaMensualDao) {
        this.reporteVentaMensualDao = reporteVentaMensualDao;
    }

    @Override
    @Transactional
    public List<ReporteVentaMensual> getReporteVentaMensual(Date date) throws EntityException {
        List<ReporteVentaMensual> list;
        try {
            list = this.reporteVentaMensualDao.findByNamedQuery(REPORTE_VENTA_MENSUAL, date);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ReporteVentaMensual";
    }

}
