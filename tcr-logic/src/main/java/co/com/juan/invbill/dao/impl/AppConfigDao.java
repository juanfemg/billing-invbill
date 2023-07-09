package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IAppConfigDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.AppConfig;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class AppConfigDao extends HibernateDaoImpl<AppConfig, String> implements IAppConfigDao {

    @Inject
    public AppConfigDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
