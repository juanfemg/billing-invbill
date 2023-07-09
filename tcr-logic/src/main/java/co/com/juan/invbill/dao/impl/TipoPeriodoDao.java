package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ITipoPeriodoDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.TipoPeriodo;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class TipoPeriodoDao extends HibernateDaoImpl<TipoPeriodo, Integer> implements ITipoPeriodoDao {

    @Inject
    public TipoPeriodoDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
