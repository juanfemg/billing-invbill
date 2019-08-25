package co.com.juan.tcr.control;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.tcr.dao.ITipoPeriodoDao;
import co.com.juan.tcr.exceptions.EntityException;
import co.com.juan.tcr.model.TipoPeriodo;
import co.com.juan.tcr.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */
@Scope("singleton")
@Service("TipoPeriodoLogic")
public class TipoPeriodoLogic implements ITipoPeriodoLogic {

	private static final Logger log = LoggerFactory.getLogger(TipoPeriodoLogic.class);

	/**
	 * DAO injected by Spring that manages TipoPeriodo entities
	 * 
	 */
	@Autowired
	private ITipoPeriodoDao tipoPeriodoDao;

	/**
	 * Logic injected by Spring that manages TipoPeriodo entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TipoPeriodo> getTipoPeriodo() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<TipoPeriodo> list = new ArrayList<>();

		try {
			list = tipoPeriodoDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveTipoPeriodo(TipoPeriodo entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getTipoPeriodo(entity.getIdPeriodo()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			tipoPeriodoDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteTipoPeriodo(TipoPeriodo entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdPeriodo() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			tipoPeriodoDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateTipoPeriodo(TipoPeriodo entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			tipoPeriodoDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TipoPeriodo getTipoPeriodo(Integer id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		TipoPeriodo entity = null;

		try {
			entity = tipoPeriodoDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoPeriodo> findPageTipoPeriodo(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<TipoPeriodo> entity = null;

		try {
			entity = tipoPeriodoDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberTipoPeriodo() {
		Long entity = null;

		try {
			entity = tipoPeriodoDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoPeriodo> getDataTipoPeriodo() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<TipoPeriodo> list = new ArrayList<>();

		try {
			list = tipoPeriodoDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoPeriodo> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<TipoPeriodo> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = tipoPeriodoDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoPeriodo> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<TipoPeriodo> list = new ArrayList<>();

		try {
			list = tipoPeriodoDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(TipoPeriodo entity) {
		if (entity.getPeriodo() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_PERIODO);
		}

		if ((entity.getPeriodo() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getPeriodo(), 45))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_PERIODO);
		}

		if (entity.getNumeroMeses() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_NUMERO_MESES);
		}

		if ((entity.getNumeroMeses() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getNumeroMeses().toString(), 11, 0))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_NUMERO_MESES);
		}

		if (entity.getEstado() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
		}

		if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "TipoPeriodo";
		private static final String FIELD_ID_ENTITY = "idPeriodo";
		private static final String FIELD_PERIODO = "periodo";
		private static final String FIELD_NUMERO_MESES = "numeroMeses";
		private static final String FIELD_ESTADO = "estado";
	}

}
