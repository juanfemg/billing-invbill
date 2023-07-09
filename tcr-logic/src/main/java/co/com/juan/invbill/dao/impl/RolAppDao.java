package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IRolAppDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.RolApp;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class RolAppDao extends HibernateDaoImpl<RolApp, Integer> implements
        IRolAppDao {

    @Inject
    public RolAppDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
