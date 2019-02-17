package co.com.juan.tcr.dto.control;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.tcr.dto.ReporteVentaDiaria;
import co.com.juan.tcr.dto.dao.IReporteVentaDiariaDao;
import co.com.juan.tcr.exceptions.EntityException;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("ReporteVentaDiariaLogic")
public class ReporteVentaDiariaLogic implements IReporteVentaDiariaLogic {

	private static final Logger log = LoggerFactory.getLogger(ReporteVentaDiariaLogic.class);
	private static final String REPORTE_VENTA_DIARIA = "SPReporteVentaDiaria";

	/**
	 * DAO injected by Spring that manages ReporteVentaDiaria entities
	 * 
	 */
	@Autowired
	private IReporteVentaDiariaDao reporteVentaDiariaDao;

	/**
	 * Logic injected by Spring that manages ReporteVentaDiaria entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public ReporteVentaDiaria getReporteVentaDiaria(Date date) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		ReporteVentaDiaria entity = null;

		try {
			entity = reporteVentaDiariaDao.findObjectByNamedQuery(REPORTE_VENTA_DIARIA, date);

			if (entity == null) {
				entity = new ReporteVentaDiaria();
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
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "ReporteVentaDiaria";
	}

}
