package co.com.juan.invbill.dto.dao;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.dto.ReporteVentaDiaria;

/**
 * @author Juan Felipe
 *
 */

/**
 * A data access object (DAO) providing persistence and search support for
 * ReporteVentaDiaria entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see ReporteVentaDiaria
 */

@Scope("singleton")
@Repository("ReporteVentaDiariaDao")
public class ReporteVentaDiariaDao extends HibernateDaoImpl<ReporteVentaDiaria, Integer>
		implements IReporteVentaDiariaDao {

	@Resource
	private SessionFactory sessionFactory;

	@Inject
	public ReporteVentaDiariaDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public static IReporteVentaDiariaDao getFromApplicationContext(ApplicationContext ctx) {
		return (IReporteVentaDiariaDao) ctx.getBean("ReporteVentaDiariaDao");
	}
}
