package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IReporteCompraMensualLogic;
import co.com.juan.invbill.dao.IReporteCompraMensualDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteCompraMensual;
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
public class ReporteCompraMensualLogic implements IReporteCompraMensualLogic {

    private static final Logger log = LoggerFactory.getLogger(ReporteCompraMensualLogic.class);
    private static final String REPORTE_COMPRA_MENSUAL = "SPReporteCompraMensual";
    private final IReporteCompraMensualDao reporteCompraMensualDao;

    @Inject
    public ReporteCompraMensualLogic(IReporteCompraMensualDao reporteCompraMensualDao) {
        this.reporteCompraMensualDao = reporteCompraMensualDao;
    }

    @Override
    @Transactional
    public List<ReporteCompraMensual> getReporteCompraMensual(Date date) throws EntityException {
        List<ReporteCompraMensual> list;
        try {
            list = this.reporteCompraMensualDao.findByNamedQuery(REPORTE_COMPRA_MENSUAL, date);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ReporteCompraMensual";
    }

}
