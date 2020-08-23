package co.com.juan.invbill.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.ProveedorApp;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * ProveedorApp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see ProveedorApp
 */

@Scope("singleton")
@Repository("ProveedorAppDao")
public class ProveedorAppDao extends HibernateDaoImpl<ProveedorApp, Integer> implements IProveedorAppDao {

	@Resource
	private SessionFactory sessionFactory;

	public static IProveedorAppDao getFromApplicationContext(ApplicationContext ctx) {
		return (IProveedorAppDao) ctx.getBean("ProveedorAppDao");
	}

}
