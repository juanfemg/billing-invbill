package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IClienteAppDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.ClienteApp;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ClienteAppDao extends HibernateDaoImpl<ClienteApp, Integer> implements IClienteAppDao {

    @Inject
    public ClienteAppDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
