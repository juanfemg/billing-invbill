/**
 *
 */
package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IReporteCompraDiariaDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ReporteCompraDiaria;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 *
 */
@Repository
public class ReporteCompraDiariaDao extends HibernateDaoImpl<ReporteCompraDiaria, Integer>
        implements IReporteCompraDiariaDao {

    @Inject
    public ReporteCompraDiariaDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
