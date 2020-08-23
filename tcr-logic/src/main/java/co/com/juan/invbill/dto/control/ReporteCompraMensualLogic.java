package co.com.juan.invbill.dto.control;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dto.ReporteCompraMensual;
import co.com.juan.invbill.dto.dao.IReporteCompraMensualDao;
import co.com.juan.invbill.exceptions.EntityException;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("ReporteCompraMensualLogic")
public class ReporteCompraMensualLogic implements IReporteCompraMensualLogic {

	private static final Logger log = LoggerFactory.getLogger(ReporteCompraMensualLogic.class);
	private static final String REPORTE_COMPRA_MENSUAL = "SPReporteCompraMensual";

	/**
	 * DAO injected by Spring that manages ReporteCompraMensual entities
	 * 
	 */
	@Autowired
	private IReporteCompraMensualDao reporteCompraMensualDao;

	/**
	 * Logic injected by Spring that manages ReporteCompraMensual entities
	 * 
	 */
	@Override
	@Transactional
	public List<ReporteCompraMensual> getReporteCompraMensual(Date date) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<ReporteCompraMensual> list = null;

		try {
			list = reporteCompraMensualDao.findByNamedQuery(REPORTE_COMPRA_MENSUAL, date);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "ReporteCompraMensual";
	}

}
