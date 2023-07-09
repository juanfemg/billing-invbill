package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IReporteVentaMensualDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ReporteVentaMensual;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ReporteVentaMensualDao extends HibernateDaoImpl<ReporteVentaMensual, Integer>
        implements IReporteVentaMensualDao {

    @Inject
    public ReporteVentaMensualDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
