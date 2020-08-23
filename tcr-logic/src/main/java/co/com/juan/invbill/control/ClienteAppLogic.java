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

import co.com.juan.invbill.dao.IClienteAppDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ClienteApp;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("ClienteAppLogic")
public class ClienteAppLogic implements IClienteAppLogic {

	private static final Logger log = LoggerFactory.getLogger(ClienteAppLogic.class);

	/**
	 * DAO injected by Spring that manages ClienteApp entities
	 * 
	 */
	@Autowired
	private IClienteAppDao clienteAppDao;

	/**
	 * Logic injected by Spring that manages ClienteApp entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ClienteApp> getClienteApp() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<ClienteApp> list = new ArrayList<>();

		try {
			list = clienteAppDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveClienteApp(ClienteApp entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);
			checkSecondaryFields(entity);

			if (getClienteApp(entity.getIdClienteApp()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			clienteAppDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteClienteApp(ClienteApp entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdClienteApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			clienteAppDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateClienteApp(ClienteApp entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);
			checkSecondaryFields(entity);

			clienteAppDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteApp getClienteApp(Integer id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		ClienteApp entity = null;

		try {
			entity = clienteAppDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteApp> findPageClienteApp(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<ClienteApp> entity = null;

		try {
			entity = clienteAppDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberClienteApp() {
		Long entity = null;

		try {
			entity = clienteAppDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteApp> getDataClienteApp() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<ClienteApp> list = new ArrayList<>();

		try {
			list = clienteAppDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteApp> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<ClienteApp> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = clienteAppDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteApp> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<ClienteApp> list = new ArrayList<>();

		try {
			list = clienteAppDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(ClienteApp entity) {
		if (entity.getIdClienteApp() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		if ((entity.getIdClienteApp() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getIdClienteApp().toString(), 11, 0))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
		}

		if (entity.getRazonSocial() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_RAZON_SOCIAL);
		}

		if ((entity.getRazonSocial() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getRazonSocial(), 200))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_RAZON_SOCIAL);
		}

		if (entity.getEstado() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
		}

		if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
		}
	}

	@Override
	public void checkSecondaryFields(ClienteApp entity) {
		if ((entity.getCodVerificacion() != null) && !(Utilities
				.checkNumberAndCheckWithPrecisionAndScale(entity.getCodVerificacion().toString(), 1, 0))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_COD_VERIFICACION);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "ClienteApp";
		private static final String FIELD_ID_ENTITY = "idClienteApp";
		private static final String FIELD_COD_VERIFICACION = "codVerificacion";
		private static final String FIELD_RAZON_SOCIAL = "razonSocial";
		private static final String FIELD_ESTADO = "estado";
	}

}
