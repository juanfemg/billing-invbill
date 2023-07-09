package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IReporteVentaDiariaLogic;
import co.com.juan.invbill.dao.IReporteVentaDiariaDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ReporteVentaDiaria;
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
public class ReporteVentaDiariaLogic implements IReporteVentaDiariaLogic {

    private static final Logger log = LoggerFactory.getLogger(ReporteVentaDiariaLogic.class);
    private static final String REPORTE_VENTA_DIARIA = "SPReporteVentaDiaria";
    private final IReporteVentaDiariaDao reporteVentaDiariaDao;

    @Inject
    public ReporteVentaDiariaLogic(IReporteVentaDiariaDao reporteVentaDiariaDao) {
        this.reporteVentaDiariaDao = reporteVentaDiariaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteVentaDiaria getReporteVentaDiaria(Date date) throws EntityException {
        ReporteVentaDiaria entity;
        try {
            entity = this.reporteVentaDiariaDao.findObjectByNamedQuery(REPORTE_VENTA_DIARIA, date);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ReporteVentaDiaria";
    }

}
