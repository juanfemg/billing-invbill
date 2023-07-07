package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IFacturaDetalleLogic;
import co.com.juan.invbill.dao.IFacturaDetalleDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Juan Felipe
 */

@Scope("singleton")
@Service("FacturaDetalleLogic")
public class FacturaDetalleLogic implements IFacturaDetalleLogic {

    private static final Logger log = LoggerFactory.getLogger(FacturaDetalleLogic.class);
    private static final String DETALLE_FACTURA_DEVOLUCION = "SPDetalleFacturaDevolucion";

    @Autowired
    private IFacturaDetalleDao facturaDetalleDao;

    @Override
    @Transactional(readOnly = true)
    public List<FacturaDetalle> getFacturaDetalle() {
        log.debug("finding all {} instances", Constant.ENTITY_NAME);

        List<FacturaDetalle> list;

        try {
            list = facturaDetalleDao.findAll();
        } catch (Exception e) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
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

        FacturaDetalle entity;

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
    public List<FacturaDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                               Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<FacturaDetalle> list;
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

        List<FacturaDetalle> list;

        try {
            list = facturaDetalleDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(FacturaDetalle entity) {
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

        List<FacturaDetalle> list;

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
        private static final String FIELD_CANTIDAD = "cantidad";
        private static final String FIELD_PRECIO_VENTA = "precioVenta";
        private static final String FIELD_VALOR_IVA = "valorIva";
    }

}