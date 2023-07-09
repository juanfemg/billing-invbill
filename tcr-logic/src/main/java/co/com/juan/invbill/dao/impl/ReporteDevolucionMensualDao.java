package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IReporteDevolucionMensualDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ReporteDevolucionMensual;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ReporteDevolucionMensualDao extends HibernateDaoImpl<ReporteDevolucionMensual, Integer>
        implements IReporteDevolucionMensualDao {

    @Inject
    public ReporteDevolucionMensualDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
