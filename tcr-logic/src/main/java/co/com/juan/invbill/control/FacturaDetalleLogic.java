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

import co.com.juan.invbill.dao.IFacturaDetalleDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;
import co.com.juan.invbill.util.Utilities;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("FacturaDetalleLogic")
public class FacturaDetalleLogic implements IFacturaDetalleLogic {

	private static final Logger log = LoggerFactory.getLogger(FacturaDetalleLogic.class);
	private static final String DETALLE_FACTURA_DEVOLUCION = "SPDetalleFacturaDevolucion";

	/**
	 * DAO injected by Spring that manages FacturaDetalle entities
	 * 
	 */
	@Autowired
	private IFacturaDetalleDao facturaDetalleDao;

	/**
	 * Logic injected by Spring that manages FacturaDetalle entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> getFacturaDetalle() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<FacturaDetalle> list = new ArrayList<>();

		try {
			list = facturaDetalleDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveFacturaDetalle(FacturaDetalle entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getFacturaDetalle(entity.getId()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			facturaDetalleDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteFacturaDetalle(FacturaDetalle entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getId() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			facturaDetalleDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateFacturaDetalle(FacturaDetalle entity) {
		log.debug("updating FacturaDetalle instance");

		try {
			checkFields(entity);

			facturaDetalleDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public FacturaDetalle getFacturaDetalle(FacturaDetalleId id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		FacturaDetalle entity = null;

		try {
			entity = facturaDetalleDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> findPageFacturaDetalle(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<FacturaDetalle> entity = null;

		try {
			entity = facturaDetalleDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberFacturaDetalle() {
		Long entity = null;

		try {
			entity = facturaDetalleDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;

	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> getDataFacturaDetalle() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<FacturaDetalle> list = new ArrayList<>();

		try {
			list = facturaDetalleDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<FacturaDetalle> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = facturaDetalleDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<FacturaDetalle> list = new ArrayList<>();

		try {
			list = facturaDetalleDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(FacturaDetalle entity) {
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

	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) {
		log.debug("getting {} instance by IdFactura", Constant.ENTITY_NAME);

		List<FacturaDetalle> list = null;

		try {
			list = facturaDetalleDao.findByNamedQueryWithoutAlias(DETALLE_FACTURA_DEVOLUCION, idFactura);

		} catch (Exception e) {
			log.error("get {} by IdFactura failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	private static class Constant {

		private static final String ENTITY_NAME = "FacturaDetalle";
		private static final String FIELD_ID_ENTITY = "idFacturaDetalle";
		private static final String FIELD_CANTIDAD = "cantidad";
		private static final String FIELD_PRECIO_VENTA = "precioVenta";
		private static final String FIELD_VALOR_IVA = "valorIva";
	}

}