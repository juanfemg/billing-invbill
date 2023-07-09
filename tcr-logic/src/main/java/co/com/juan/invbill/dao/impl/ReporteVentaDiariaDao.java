package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IReporteVentaDiariaDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ReporteVentaDiaria;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ReporteVentaDiariaDao extends HibernateDaoImpl<ReporteVentaDiaria, Integer>
        implements IReporteVentaDiariaDao {

    @Inject
    public ReporteVentaDiariaDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
