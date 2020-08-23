package co.com.juan.invbill.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.Producto;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * Producto entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see Producto
 */

@Scope("singleton")
@Repository("ProductoDao")
public class ProductoDao extends HibernateDaoImpl<Producto, Integer> implements
		IProductoDao {

	@Resource
	private SessionFactory sessionFactory;

	public static IProductoDao getFromApplicationContext(ApplicationContext ctx) {
		return (IProductoDao) ctx.getBean("ProductoDao");
	}
}
