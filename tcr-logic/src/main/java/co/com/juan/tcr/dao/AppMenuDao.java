package co.com.juan.tcr.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.tcr.dataaccess.api.HibernateDaoImpl;
import co.com.juan.tcr.model.AppMenu;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * AppMenu entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see AppMenu
 */

@Scope("singleton")
@Repository("AppMenuDao")
public class AppMenuDao extends HibernateDaoImpl<AppMenu, String> implements IAppMenuDao {

	@Resource
	private SessionFactory sessionFactory;

	public static IAppMenuDao getFromApplicationContext(ApplicationContext ctx) {
		return (IAppMenuDao) ctx.getBean("AppMenuDao");
	}
}
