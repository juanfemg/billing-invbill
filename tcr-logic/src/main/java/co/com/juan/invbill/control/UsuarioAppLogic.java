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

import co.com.juan.invbill.dao.IUsuarioAppDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("UsuarioAppLogic")
public class UsuarioAppLogic implements IUsuarioAppLogic {

	private static final Logger log = LoggerFactory.getLogger(UsuarioAppLogic.class);

	/**
	 * DAO injected by Spring that manages UsuarioApp entities
	 * 
	 */
	@Autowired
	private IUsuarioAppDao usuarioAppDao;

	/**
	 * Logic injected by Spring that manages UsuarioApp entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioApp> getUsuarioApp() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<UsuarioApp> list = new ArrayList<>();

		try {
			list = usuarioAppDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveUsuarioApp(UsuarioApp entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getUsuarioApp(entity.getIdUsuarioApp()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			usuarioAppDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteUsuarioApp(UsuarioApp entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdUsuarioApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			usuarioAppDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateUsuarioApp(UsuarioApp entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			usuarioAppDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioApp getUsuarioApp(String id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		UsuarioApp entity = null;

		try {
			entity = usuarioAppDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioApp> findPageUsuarioApp(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<UsuarioApp> entity = null;

		try {
			entity = usuarioAppDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberUsuarioApp() {
		Long entity = null;

		try {
			entity = usuarioAppDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioApp> getDataUsuarioApp() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<UsuarioApp> list = new ArrayList<>();

		try {
			list = usuarioAppDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioApp> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<UsuarioApp> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = usuarioAppDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioApp> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<UsuarioApp> list = new ArrayList<>();

		try {
			list = usuarioAppDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(UsuarioApp entity) {
		if (entity.getIdUsuarioApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		if ((entity.getIdUsuarioApp() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getIdUsuarioApp(), 20))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
		}

		if (entity.getNombre() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_NOMBRE);
		}

		if ((entity.getNombre() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getNombre(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_NOMBRE);
		}

		if (entity.getPassword() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_PASSW);
		}

		if ((entity.getPassword() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getPassword(), 45))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_PASSW);
		}

		if (entity.getEstado() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
		}

		if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
		}

		if (entity.getRolApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ROL);
		}

		if ((entity.getRolApp() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getRolApp().getRol(), 45))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ROL);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "UsuarioApp";
		private static final String FIELD_ID_ENTITY = "idUsuarioApp";
		private static final String FIELD_NOMBRE = "nombre";
		private static final String FIELD_PASSW = "password";
		private static final String FIELD_ESTADO = "estado";
		private static final String FIELD_ROL = "rol";
	}

}
