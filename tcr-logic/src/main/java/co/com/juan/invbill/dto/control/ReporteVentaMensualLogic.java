package co.com.juan.invbill.dto.control;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dto.ReporteVentaMensual;
import co.com.juan.invbill.dto.dao.IReporteVentaMensualDao;
import co.com.juan.invbill.exceptions.EntityException;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("ReporteVentaMensualLogic")
public class ReporteVentaMensualLogic implements IReporteVentaMensualLogic {

	private static final Logger log = LoggerFactory.getLogger(ReporteVentaMensualLogic.class);
	private static final String REPORTE_VENTA_MENSUAL = "SPReporteVentaMensual";

	/**
	 * DAO injected by Spring that manages ReporteVentaMensual entities
	 * 
	 */
	@Autowired
	private IReporteVentaMensualDao reporteVentaMensualDao;

	/**
	 * Logic injected by Spring that manages ReporteVentaMensual entities
	 * 
	 */
	@Override
	@Transactional
	public List<ReporteVentaMensual> getReporteVentaMensual(Date date) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<ReporteVentaMensual> list = null;

		try {
			list = reporteVentaMensualDao.findByNamedQuery(REPORTE_VENTA_MENSUAL, date);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "ReporteVentaMensual";
	}

}
