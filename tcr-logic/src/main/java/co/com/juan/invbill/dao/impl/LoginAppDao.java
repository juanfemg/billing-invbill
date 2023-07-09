package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ILoginAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.LoginApp;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 *
 */
@Repository
public class LoginAppDao extends HibernateDaoImpl<LoginApp, String> implements
        ILoginAppDao {

    @Inject
    public LoginAppDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
