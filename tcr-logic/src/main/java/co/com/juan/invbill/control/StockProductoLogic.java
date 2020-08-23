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

import co.com.juan.invbill.dao.IStockProductoDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("StockProductoLogic")
public class StockProductoLogic implements IStockProductoLogic {

	private static final Logger log = LoggerFactory.getLogger(StockProductoLogic.class);

	/**
	 * DAO injected by Spring that manages StockProducto entities
	 * 
	 */
	@Autowired
	private IStockProductoDao stockProductoDao;

	/**
	 * Logic injected by Spring that manages StockProducto entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<StockProducto> getStockProducto() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<StockProducto> list = new ArrayList<>();

		try {
			list = stockProductoDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveStockProducto(StockProducto entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			stockProductoDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteStockProducto(StockProducto entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdStockProducto() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			stockProductoDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateStockProducto(StockProducto entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			stockProductoDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public StockProducto getStockProducto(Integer id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		StockProducto entity = null;

		try {
			entity = stockProductoDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockProducto> findPageStockProducto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<StockProducto> entity = null;

		try {
			entity = stockProductoDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberStockProducto() {
		Long entity = null;

		try {
			entity = stockProductoDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockProducto> getDataStockProducto() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<StockProducto> list = new ArrayList<>();

		try {
			list = stockProductoDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<StockProducto> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = stockProductoDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public StockProducto findObjectByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		StockProducto entity = null;
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			entity = stockProductoDao.findObjectByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockProducto> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<StockProducto> list = new ArrayList<>();

		try {
			list = stockProductoDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public StockProducto findObjectByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		StockProducto entity = null;

		try {
			entity = stockProductoDao.findObjectByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	public void checkFields(StockProducto entity) {
		if (entity.getProducto() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_PRODUCTO);
		}

		if ((entity.getProducto() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getProducto().getProducto(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_PRODUCTO);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Object findMaxObjectByCriteria(String propertyName) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		Object object = null;

		try {
			object = stockProductoDao.maxByCriteria(propertyName);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return object;
	}

	@Override
	@Transactional(readOnly = true)
	public Object findMinObjectByCriteria(String propertyName) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		Object object = null;

		try {
			object = stockProductoDao.minByCriteria(propertyName);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return object;
	}

	@Override
	@Transactional(readOnly = true)
	public Object findAvgObjectByCriteria(String propertyName) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		Object object = null;

		try {
			object = stockProductoDao.avgByCriteria(propertyName);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return object;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "StockProducto";
		private static final String FIELD_ID_ENTITY = "idStockProducto";
		private static final String FIELD_PRODUCTO = "producto";
	}

}
