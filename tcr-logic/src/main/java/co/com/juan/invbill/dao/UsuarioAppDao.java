package co.com.juan.invbill.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.UsuarioApp;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * UsuarioApp entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see UsuarioApp
 */

@Scope("singleton")
@Repository("UsuarioAppDao")
public class UsuarioAppDao extends HibernateDaoImpl<UsuarioApp, String>
		implements IUsuarioAppDao {

	@Resource
	private SessionFactory sessionFactory;

	public static IUsuarioAppDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (IUsuarioAppDao) ctx.getBean("UsuarioAppDao");
	}
}
