package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IAppMenuDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.AppMenu;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class AppMenuDao extends HibernateDaoImpl<AppMenu, String> implements IAppMenuDao {

    @Inject
    public AppMenuDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
