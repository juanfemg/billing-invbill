package co.com.juan.invbill.dto.control;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dto.ReporteCompraDiaria;
import co.com.juan.invbill.dto.dao.IReporteCompraDiariaDao;
import co.com.juan.invbill.exceptions.EntityException;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("ReporteCompraDiariaLogic")
public class ReporteCompraDiariaLogic implements IReporteCompraDiariaLogic {

	private static final Logger log = LoggerFactory.getLogger(ReporteCompraDiariaLogic.class);
	private static final String REPORTE_COMPRA_DIARIA = "SPReporteCompraDiaria";

	/**
	 * DAO injected by Spring that manages ReporteCompraDiaria entities
	 * 
	 */
	@Autowired
	private IReporteCompraDiariaDao reporteCompraDiariaDao;

	/**
	 * Logic injected by Spring that manages ReporteCompraDiaria entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public ReporteCompraDiaria getReporteCompraDiaria(Date date) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		ReporteCompraDiaria entity = null;

		try {
			entity = reporteCompraDiariaDao.findObjectByNamedQuery(REPORTE_COMPRA_DIARIA, date);

			if (entity == null) {
				entity = new ReporteCompraDiaria();
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

		private static final String ENTITY_NAME = "ReporteCompraDiaria";
	}

}
