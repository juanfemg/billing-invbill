package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IAppConfigDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.AppConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("AppConfigDao")
public class AppConfigDao extends HibernateDaoImpl<AppConfig, String> implements IAppConfigDao {

}
