package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IUsuarioAppDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.UsuarioApp;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class UsuarioAppDao extends HibernateDaoImpl<UsuarioApp, String>
        implements IUsuarioAppDao {

    @Inject
    public UsuarioAppDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
