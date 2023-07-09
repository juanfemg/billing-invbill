package co.com.juan.invbill.dto.control;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dto.ReporteDevolucionDiaria;
import co.com.juan.invbill.dto.dao.IReporteDevolucionDiariaDao;
import co.com.juan.invbill.exceptions.EntityException;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("ReporteDevolucionDiariaLogic")
public class ReporteDevolucionDiariaLogic implements IReporteDevolucionDiariaLogic {

	private static final Logger log = LoggerFactory.getLogger(ReporteDevolucionDiariaLogic.class);
	private static final String REPORTE_DEVOLUCION_DIARIA = "SPReporteDevolucionDiaria";

	/**
	 * DAO injected by Spring that manages ReporteDevolucionDiaria entities
	 * 
	 */
	@Autowired
	private IReporteDevolucionDiariaDao reporteDevolucionDiariaDao;

	/**
	 * Logic injected by Spring that manages ReporteDevolucionDiaria entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public ReporteDevolucionDiaria getReporteDevolucionDiaria(Date date) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		ReporteDevolucionDiaria entity = null;

		try {
			entity = reporteDevolucionDiariaDao.findObjectByNamedQuery(REPORTE_DEVOLUCION_DIARIA, date);

			if (entity == null) {
				entity = new ReporteDevolucionDiaria();
				entity.setValorTotal(0D);
				entity.setCantidadProductos(0);
			} else {
				if (entity.getValorTotal() == null)
					entity.setValorTotal(0D);

				if (entity.getCantidadProductos() == null)
					entity.setCantidadProductos(0);
			}

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException.FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "ReporteDevolucionDiaria";
	}

}
