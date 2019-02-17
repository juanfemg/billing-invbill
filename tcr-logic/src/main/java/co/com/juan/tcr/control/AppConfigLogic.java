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

import co.com.juan.tcr.dao.IAppConfigDao;
import co.com.juan.tcr.exceptions.EntityException;
import co.com.juan.tcr.model.AppConfig;
import co.com.juan.tcr.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("AppConfigLogic")
public class AppConfigLogic implements IAppConfigLogic {

	private static final Logger log = LoggerFactory.getLogger(AppConfigLogic.class);

	/**
	 * DAO injected by Spring that manages AppConfig entities
	 * 
	 */
	@Autowired
	private IAppConfigDao appConfigDao;

	/**
	 * Logic injected by Spring that manages AppConfig entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AppConfig> getAppConfig() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<AppConfig> list = new ArrayList<>();

		try {
			list = appConfigDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveAppConfig(AppConfig entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getAppConfig(entity.getIdAppConfig()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			appConfigDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteAppConfig(AppConfig entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdAppConfig() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			appConfigDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateAppConfig(AppConfig entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			appConfigDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AppConfig getAppConfig(String id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		AppConfig entity = null;

		try {
			entity = appConfigDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppConfig> findPageAppConfig(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<AppConfig> entity = null;

		try {
			entity = appConfigDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberAppConfig() {
		Long entity = null;

		try {
			entity = appConfigDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppConfig> getDataAppConfig() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<AppConfig> list = new ArrayList<>();

		try {
			list = appConfigDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppConfig> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<AppConfig> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = appConfigDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppConfig> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<AppConfig> list = new ArrayList<>();

		try {
			list = appConfigDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(AppConfig entity) {
		if (entity.getIdAppConfig() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		if ((entity.getIdAppConfig() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getIdAppConfig(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
		}

		if (entity.getValor() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_VALOR);
		}

		if ((entity.getValor() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getValor(), 200))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_VALOR);
		}

		if (entity.getDescripcion() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_DESCRIPCION);
		}

		if ((entity.getDescripcion() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getDescripcion(), 200))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_DESCRIPCION);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "AppConfig";
		private static final String FIELD_ID_ENTITY = "idAppConfig";
		private static final String FIELD_VALOR = "valor";
		private static final String FIELD_DESCRIPCION = "descripcion";
	}

}
