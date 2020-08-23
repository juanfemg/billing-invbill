package co.com.juan.invbill.control;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dao.IRolAppDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.RolApp;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("RolAppLogic")
public class RolAppLogic implements IRolAppLogic {

	private static final Logger log = LoggerFactory.getLogger(RolAppLogic.class);

	/**
	 * DAO injected by Spring that manages RolApp entities
	 * 
	 */
	@Autowired
	private IRolAppDao rolAppDao;

	/**
	 * Logic injected by Spring that manages RolApp entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<RolApp> getRolApp() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<RolApp> list = new ArrayList<>();

		try {
			list = rolAppDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRolApp(RolApp entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getRolApp(entity.getIdRolApp()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			rolAppDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteRolApp(RolApp entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdRolApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			rolAppDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateRolApp(RolApp entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			rolAppDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public RolApp getRolApp(Integer id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		RolApp entity = null;

		try {
			entity = rolAppDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolApp> findPageRolApp(String sortColumnName, boolean sortAscending, int startRow, int maxResults) {
		List<RolApp> entity = null;

		try {
			entity = rolAppDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberRolApp() {
		Long entity = null;

		try {
			entity = rolAppDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolApp> getDataRolApp() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<RolApp> list = new ArrayList<>();

		try {
			list = rolAppDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolApp> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<RolApp> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = rolAppDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolApp> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<RolApp> list = new ArrayList<>();

		try {
			list = rolAppDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(RolApp entity) {
		if (entity.getIdRolApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		if ((entity.getIdRolApp() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getIdRolApp().toString(), 11, 0))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
		}

		if (entity.getRol() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ROL);
		}

		if ((entity.getRol() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getRol(), 45))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ROL);
		}

		if (entity.getEstado() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
		}

		if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "RolApp";
		private static final String FIELD_ID_ENTITY = "idRolApp";
		private static final String FIELD_ROL = "rol";
		private static final String FIELD_ESTADO = "estado";
	}

}
