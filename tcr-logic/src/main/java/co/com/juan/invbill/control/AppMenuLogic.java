package co.com.juan.invbill.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.invbill.dao.IAppMenuDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.util.SortMenu;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("AppMenuLogic")
public class AppMenuLogic implements IAppMenuLogic {

	private static final Logger log = LoggerFactory.getLogger(AppMenuLogic.class);

	/**
	 * DAO injected by Spring that manages AppMenu entities
	 * 
	 */
	@Autowired
	private IAppMenuDao appMenuDao;

	/**
	 * Logic injected by Spring that manages AppMenu entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AppMenu> getAppMenu() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<AppMenu> list = new ArrayList<>();

		try {
			list = appMenuDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveAppMenu(AppMenu entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getAppMenu(entity.getIdAppMenu()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			appMenuDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteAppMenu(AppMenu entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdAppMenu() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			appMenuDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateAppMenu(AppMenu entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			appMenuDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AppMenu getAppMenu(String id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		AppMenu entity = null;

		try {
			entity = appMenuDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppMenu> findPageAppMenu(String sortColumnName, boolean sortAscending, int startRow, int maxResults) {
		List<AppMenu> entity = null;

		try {
			entity = appMenuDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberAppMenu() {
		Long entity = null;

		try {
			entity = appMenuDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;

	}

	@Override
	@Transactional(readOnly = true)
	public List<AppMenu> getDataAppMenu() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<AppMenu> list = new ArrayList<>();

		try {
			list = appMenuDao.findAll();
			Collections.sort(list, new SortMenu());
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppMenu> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<AppMenu> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = appMenuDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AppMenu> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<AppMenu> list = new ArrayList<>();

		try {
			list = appMenuDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(AppMenu entity) {
		if (entity.getIdAppMenu() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		if ((entity.getIdAppMenu() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getIdAppMenu(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
		}

		if (entity.getValor() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_VALOR);
		}

		if ((entity.getValor() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getValor(), 200))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_VALOR);
		}

		if (entity.getSalida() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_SALIDA);
		}

		if ((entity.getSalida() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getSalida(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_SALIDA);
		}

		if (entity.getIcono() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ICONO);
		}

		if ((entity.getIcono() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getIcono(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ICONO);
		}

		if (entity.getOrden() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ORDEN);
		}

		if ((entity.getOrden() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getOrden(), 8))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ORDEN);
		}

	}

	private static class Constant {

		private static final String ENTITY_NAME = "AppMenu";
		private static final String FIELD_ID_ENTITY = "idAppMenu";
		private static final String FIELD_VALOR = "valor";
		private static final String FIELD_SALIDA = "salida";
		private static final String FIELD_ICONO = "icono";
		private static final String FIELD_ORDEN = "orden";
	}

}
