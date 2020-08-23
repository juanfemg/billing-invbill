package co.com.juan.invbill.dto.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.dto.ReporteDevolucionMensual;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * ReporteDevolucionMensual entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see ReporteDevolucionMensual
 */

@Scope("singleton")
@Repository("ReporteDevolucionMensualDao")
public class ReporteDevolucionMensualDao extends HibernateDaoImpl<ReporteDevolucionMensual, Integer>
		implements IReporteDevolucionMensualDao {

	@Resource
	private SessionFactory sessionFactory;

	public static IReporteDevolucionMensualDao getFromApplicationContext(ApplicationContext ctx) {
		return (IReporteDevolucionMensualDao) ctx.getBean("ReporteDevolucionMensualDao");
	}
}
