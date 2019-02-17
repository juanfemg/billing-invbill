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

import co.com.juan.tcr.dao.ICompraDetalleDao;
import co.com.juan.tcr.exceptions.EntityException;
import co.com.juan.tcr.model.CompraDetalle;
import co.com.juan.tcr.model.CompraDetalleId;
import co.com.juan.tcr.util.Utilities;

/**
 * @author Juan Felipe
 *
 */

@Scope("singleton")
@Service("CompraDetalleLogic")
public class CompraDetalleLogic implements ICompraDetalleLogic {

	private static final Logger log = LoggerFactory.getLogger(CompraDetalleLogic.class);

	/**
	 * DAO injected by Spring that manages CompraDetalle entities
	 * 
	 */
	@Autowired
	private ICompraDetalleDao compraDetalleDao;

	/**
	 * Logic injected by Spring that manages CompraDetalle entities
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CompraDetalle> getCompraDetalle() {
		log.debug("finding all {} instances", Constant.ENTITY_NAME);

		List<CompraDetalle> list = new ArrayList<>();

		try {
			list = compraDetalleDao.findAll();
		} catch (Exception e) {
			log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveCompraDetalle(CompraDetalle entity) {
		log.debug("saving {} instance", Constant.ENTITY_NAME);

		try {
			checkFields(entity);

			if (getCompraDetalle(entity.getId()) != null) {
				throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
			}

			compraDetalleDao.save(entity);

			log.debug("save {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new SavingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteCompraDetalle(CompraDetalle entity) {
		log.debug("deleting {} instance", Constant.ENTITY_NAME);

		if (entity == null) {
			throw new EntityException().new NullEntityExcepcion(Constant.ENTITY_NAME);
		}

		if (entity.getId() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
		}

		try {
			compraDetalleDao.delete(entity);

			log.debug("delete {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("delete {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new DeletingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateCompraDetalle(CompraDetalle entity) {
		log.debug("updating CompraDetalle instance");

		try {
			checkFields(entity);

			compraDetalleDao.update(entity);

			log.debug("update {} successful", Constant.ENTITY_NAME);
		} catch (Exception e) {
			log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CompraDetalle getCompraDetalle(CompraDetalleId id) {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		CompraDetalle entity = null;

		try {
			entity = compraDetalleDao.findById(id);

		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CompraDetalle> findPageCompraDetalle(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) {
		List<CompraDetalle> entity = null;

		try {
			entity = compraDetalleDao.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findTotalNumberCompraDetalle() {
		Long entity = null;

		try {
			entity = compraDetalleDao.count();
		} catch (Exception e) {
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return entity;

	}

	@Override
	@Transactional(readOnly = true)
	public List<CompraDetalle> getDataCompraDetalle() {
		log.debug("getting {} instance", Constant.ENTITY_NAME);

		List<CompraDetalle> list = new ArrayList<>();

		try {
			list = compraDetalleDao.findAll();
		} catch (Exception e) {
			log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CompraDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

		List<CompraDetalle> list = new ArrayList<>();
		String where;

		try {
			where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
			list = compraDetalleDao.findByCriteria(where);
		} catch (Exception e) {
			log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CompraDetalle> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance", Constant.ENTITY_NAME);

		List<CompraDetalle> list = new ArrayList<>();

		try {
			list = compraDetalleDao.findByProperty(propertyName, value);
		} catch (Exception e) {
			log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
			throw new EntityException().new FindingException(Constant.ENTITY_NAME);
		}

		return list;
	}

	@Override
	public void checkFields(CompraDetalle entity) {
		if (entity.getCantidad() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_CANTIDAD);
		}

		if ((entity.getCantidad() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCantidad().toString(), 11, 0))) {
			throw new EntityException().new NotValidFormatException(Constant.FIELD_CANTIDAD);
		}

		if (entity.getPrecioCompra() == null) {
			throw new EntityException().new EmptyFieldException(Constant.FIELD_PRECIO_VENTA);
		}

		if ((entity.getPrecioCompra() != null)
				&& !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getPrecioCompra().toString(), 22, 2))) {
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

		private static final String ENTITY_NAME = "CompraDetalle";
		private static final String FIELD_ID_ENTITY = "idCompraDetalle";
		private static final String FIELD_CANTIDAD = "cantidad";
		private static final String FIELD_PRECIO_VENTA = "precioVenta";
		private static final String FIELD_VALOR_IVA = "valorIva";
	}

}