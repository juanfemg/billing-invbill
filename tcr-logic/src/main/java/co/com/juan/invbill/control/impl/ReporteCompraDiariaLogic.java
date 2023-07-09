package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IReporteCompraDiariaLogic;
import co.com.juan.invbill.dao.IReporteCompraDiariaDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteCompraDiaria;
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
public class ReporteCompraDiariaLogic implements IReporteCompraDiariaLogic {

    private static final Logger log = LoggerFactory.getLogger(ReporteCompraDiariaLogic.class);
    private static final String REPORTE_COMPRA_DIARIA = "SPReporteCompraDiaria";
    private final IReporteCompraDiariaDao reporteCompraDiariaDao;

    @Inject
    public ReporteCompraDiariaLogic(IReporteCompraDiariaDao reporteCompraDiariaDao) {
        this.reporteCompraDiariaDao = reporteCompraDiariaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteCompraDiaria getReporteCompraDiaria(Date date) throws EntityException {
        ReporteCompraDiaria entity;
        try {
            entity = this.reporteCompraDiariaDao.findObjectByNamedQuery(REPORTE_COMPRA_DIARIA, date);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ReporteCompraDiaria";
    }

}
