package co.com.juan.invbill.dto.control;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dto.ReporteDevolucionMensual;
import co.com.juan.invbill.dto.dao.IReporteDevolucionMensualDao;
import co.com.juan.invbill.exceptions.EntityException;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("ReporteDevolucionMensualLogic")
public class ReporteDevolucionMensualLogic implements IReporteDevolucionMensualLogic {

	private static final Logger log = LoggerFactory.getLogger(ReporteDevolucionMensualLogic.class);
	private static final String REPORTE_DEVOLUCION_MENSUAL = "SPReporteDevolucionMensual";

	/**
	 * DAO injected by Spring that manages ReporteDevolucionMensual entities
	 * 
	 */
	@Autowired
	private IReporteDevolucionMensualDao reporteDevolucionMensualDao;

	/**
	 * Logic injected by Spring that manages ReporteDevolucionMensual entities
	 * 
	 */
	@Override
	@Transactional
	public List<ReporteDevolucionMensual> getReporteDevolucionMensual(Date date) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<ReporteDevolucionMensual> list = null;

		try {
			list = reporteDevolucionMensualDao.findByNamedQuery(REPORTE_DEVOLUCION_MENSUAL, date);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException.FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "ReporteDevolucionMensual";
	}

}
