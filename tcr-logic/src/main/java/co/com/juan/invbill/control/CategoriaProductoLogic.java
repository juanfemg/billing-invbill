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

import co.com.juan.invbill.dao.ICategoriaProductoDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("CategoriaProductoLogic")
public class CategoriaProductoLogic implements ICategoriaProductoLogic {

	private static final Logger log = LoggerFactory.getLogger(CategoriaProductoLogic.class);

	/**
	 * DAO injected by Spring that manages CategoriaProducto entities
	 * 
	 */
	@Autowired
	private ICategoriaProductoDao categoriaProductoDao;

	/**
	 * Logic injected by Spring that manages CategoriaProducto entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CategoriaProducto> getCategoriaProducto() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<CategoriaProducto> list = new ArrayList<>();

		try {
			list = categoriaProductoDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveCategoriaProducto(CategoriaProducto entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			categoriaProductoDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteCategoriaProducto(CategoriaProducto entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdCategoria() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}
		try {
			categoriaProductoDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateCategoriaProducto(CategoriaProducto entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			categoriaProductoDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CategoriaProducto getCategoriaProducto(Integer id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		CategoriaProducto entity = null;

		try {
			entity = categoriaProductoDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<CategoriaProducto> entity = null;

		try {
			entity = categoriaProductoDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending) {
		List<CategoriaProducto> entity = null;

		try {
			entity = categoriaProductoDao.findPage(sortColumnName, sortAscending);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberCategoriaProducto() {
		Long entity = null;

		try {
			entity = categoriaProductoDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;

	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaProducto> getDataCategoriaProducto() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<CategoriaProducto> list = new ArrayList<>();

		try {
			list = categoriaProductoDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<CategoriaProducto> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = categoriaProductoDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaProducto> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<CategoriaProducto> list = new ArrayList<>();

		try {
			list = categoriaProductoDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(CategoriaProducto entity) {
		if (entity.getCategoria() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_CATEGORIA);
		}

		if ((entity.getCategoria() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getCategoria(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_CATEGORIA);
		}

		if (entity.getEstado() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
		}

		if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "CategoriaProducto";
		private static final String FIELD_ID_ENTITY = "idCategoriaProducto";
		private static final String FIELD_CATEGORIA = "categoria";
		private static final String FIELD_ESTADO = "estado";
	}

}
