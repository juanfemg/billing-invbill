package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IAppMenuDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.AppMenu;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("AppMenuDao")
public class AppMenuDao extends HibernateDaoImpl<AppMenu, String> implements IAppMenuDao {

}
