/**
 * 
 */
package co.com.juan.tcr.dto.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.tcr.dataaccess.api.HibernateDaoImpl;
import co.com.juan.tcr.dto.ReporteCompraDiaria;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * ReporteCompraDiaria entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see ReporteCompraDiariaDao
 */

@Scope("singleton")
@Repository("ReporteCompraDiariaDao")
public class ReporteCompraDiariaDao extends HibernateDaoImpl<ReporteCompraDiaria, Integer>
		implements IReporteCompraDiariaDao {

	@Resource
	private SessionFactory sessionFactory;

	public static IReporteCompraDiariaDao getFromApplicationContext(ApplicationContext ctx) {
		return (IReporteCompraDiariaDao) ctx.getBean("ReporteCompraDiariaDao");
	}
}
