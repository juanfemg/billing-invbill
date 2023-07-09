package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IProveedorAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.ProveedorApp;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ProveedorAppDao extends HibernateDaoImpl<ProveedorApp, Integer> implements IProveedorAppDao {

    @Inject
    public ProveedorAppDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
