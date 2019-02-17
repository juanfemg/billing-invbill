package co.com.juan.tcr.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.juan.tcr.dao.IProductoDao;
import co.com.juan.tcr.exceptions.EntityException;
import co.com.juan.tcr.model.Producto;
import co.com.juan.tcr.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */
@Scope("singleton")
@Service("ProductoLogic")
public class ProductoLogic implements IProductoLogic {

	private static final Logger log = LoggerFactory.getLogger(ProductoLogic.class);

	/**
	 * DAO injected by Spring that manages Producto entities
	 * 
	 */
	@Autowired
	private IProductoDao productoDao;

	/**
	 * Logic injected by Spring that manages Producto entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Producto> getProducto() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<Producto> list = new ArrayList<>();

		try {
			list = productoDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveProducto(Producto entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			productoDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteProducto(Producto entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getIdProducto() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			productoDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateProducto(Producto entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			productoDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Producto getProducto(Integer id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		Producto entity = null;

		try {
			entity = productoDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findPageProducto(String sortColumnName, boolean sortAscending, int startRow, int maxResults) {
		List<Producto> entity = null;

		try {
			entity = productoDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberProducto() {
		Long entity = null;

		try {
			entity = productoDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> getDataProducto() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<Producto> list = new ArrayList<>();

		try {
			list = productoDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<Producto> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = productoDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<Producto> list = new ArrayList<>();

		try {
			list = productoDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByProperty(String propertyName, Collection<?> values) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<Producto> list = new ArrayList<>();

		try {
			list = productoDao.findByProperty(propertyName, values);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(Producto entity) {
		if (entity.getProducto() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_DES_PRODUCTO);
		}

		if ((entity.getProducto() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getProducto(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_DES_PRODUCTO);
		}

		if (entity.getEstado() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
		}

		if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
		}

		if (entity.getCategoriaProducto() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_CATEGORIA);
		}

		if ((entity.getCategoriaProducto() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getCategoriaProducto().getCategoria(), 50))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_CATEGORIA);
		}

		if (entity.getTipoUnidadMedida() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_TIPO_UNIDAD_MEDIDA);
		}

		if ((entity.getTipoUnidadMedida() != null)
				&& !(Utilities.checkWordAndCheckWithlength(entity.getTipoUnidadMedida().getUnidad(), 45))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_TIPO_UNIDAD_MEDIDA);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "Producto";
		private static final String FIELD_ID_ENTITY = "idProducto";
		private static final String FIELD_DES_PRODUCTO = "desProducto";
		private static final String FIELD_ESTADO = "estado";
		private static final String FIELD_CATEGORIA = "categoria";
		private static final String FIELD_TIPO_UNIDAD_MEDIDA = "tipoUnidadMedida";
	}

}
