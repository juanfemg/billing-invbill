/**
 *
 */
package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IReporteDevolucionDiariaDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ReporteDevolucionDiaria;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 *
 */
@Repository
public class ReporteDevolucionDiariaDao extends HibernateDaoImpl<ReporteDevolucionDiaria, Integer>
        implements IReporteDevolucionDiariaDao {

    @Inject
    public ReporteDevolucionDiariaDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
