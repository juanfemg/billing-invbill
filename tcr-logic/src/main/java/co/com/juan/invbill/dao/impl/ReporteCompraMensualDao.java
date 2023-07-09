package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IReporteCompraMensualDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ReporteCompraMensual;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ReporteCompraMensualDao extends HibernateDaoImpl<ReporteCompraMensual, Integer>
        implements IReporteCompraMensualDao {

    @Inject
    public ReporteCompraMensualDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
