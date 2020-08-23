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

import co.com.juan.invbill.dao.IDevolucionDetalleDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 * 
 */

@Scope("singleton")
@Service("DevolucionDetalleLogic")
public class DevolucionDetalleLogic implements IDevolucionDetalleLogic {

	private static final Logger log = LoggerFactory.getLogger(DevolucionDetalleLogic.class);

	/**
	 * DAO injected by Spring that manages DevolucionDetalle entities
	 * 
	 */
	@Autowired
	private IDevolucionDetalleDao devolucionDetalleDao;

	/**
	 * Logic injected by Spring that manages DevolucionDetalle entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DevolucionDetalle> getDevolucionDetalle() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<DevolucionDetalle> list = new ArrayList<>();

		try {
			list = devolucionDetalleDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveDevolucionDetalle(DevolucionDetalle entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getDevolucionDetalle(entity.getId()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			devolucionDetalleDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteDevolucionDetalle(DevolucionDetalle entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getId() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			devolucionDetalleDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateDevolucionDetalle(DevolucionDetalle entity) {
		log.debug("updating {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			devolucionDetalleDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public DevolucionDetalle getDevolucionDetalle(DevolucionDetalleId id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		DevolucionDetalle entity = null;

		try {
			entity = devolucionDetalleDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DevolucionDetalle> findPageDevolucionDetalle(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<DevolucionDetalle> entity = null;

		try {
			entity = devolucionDetalleDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberDevolucionDetalle() {
		Long entity = null;

		try {
			entity = devolucionDetalleDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;

	}

	@Override
	@Transactional(readOnly = true)
	public List<DevolucionDetalle> getDataDevolucionDetalle() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<DevolucionDetalle> list = new ArrayList<>();

		try {
			list = devolucionDetalleDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DevolucionDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<DevolucionDetalle> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = devolucionDetalleDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DevolucionDetalle> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<DevolucionDetalle> list = new ArrayList<>();

		try {
			list = devolucionDetalleDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(DevolucionDetalle entity) {
		if (entity.getCantidad() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_CANTIDAD);
		}

		if ((entity.getCantidad() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCantidad().toString(), 11, 0))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_CANTIDAD);
		}

		if (entity.getPrecioVenta() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_PRECIO_VENTA);
		}

		if ((entity.getPrecioVenta() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getPrecioVenta().toString(), 22, 2))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_PRECIO_VENTA);
		}

		if (entity.getValorIva() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_VALOR_IVA);
		}

		if ((entity.getValorIva() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorIva().toString(), 22, 2))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_VALOR_IVA);
		}
	}

	private static class Constant {

		private static final String ENTITY_NAME = "DevolucionDetalle";
		private static final String FIELD_ID_ENTITY = "idDevolucionDetalle";
		private static final String FIELD_CANTIDAD = "cantidad";
		private static final String FIELD_PRECIO_VENTA = "precioVenta";
		private static final String FIELD_VALOR_IVA = "valorIva";
	}

}
