package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IDevolucionDetalleLogic;
import co.com.juan.invbill.dao.IDevolucionDetalleDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */

@Scope("singleton")
@Service("DevolucionDetalleLogic")
public class DevolucionDetalleLogic implements IDevolucionDetalleLogic {

    private static final Logger log = LoggerFactory.getLogger(DevolucionDetalleLogic.class);
    private final IDevolucionDetalleDao devolucionDetalleDao;

    @Inject
    public DevolucionDetalleLogic(IDevolucionDetalleDao devolucionDetalleDao) {
        this.devolucionDetalleDao = devolucionDetalleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevolucionDetalle> getDevolucionDetalle() {
        log.debug("finding all {} instances", Constant.ENTITY_NAME);

        List<DevolucionDetalle> list;

        try {
            list = devolucionDetalleDao.findAll();
        } catch (Exception e) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.GettingException(EntityException.ALL + Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDevolucionDetalle(DevolucionDetalle entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            devolucionDetalleDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DevolucionDetalle getDevolucionDetalle(DevolucionDetalleId id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        DevolucionDetalle entity;

        try {
            entity = devolucionDetalleDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevolucionDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                                  Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<DevolucionDetalle> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = devolucionDetalleDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevolucionDetalle> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<DevolucionDetalle> list;

        try {
            list = devolucionDetalleDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(DevolucionDetalle entity) {
        if (entity.getCantidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_CANTIDAD);
        }

        if ((entity.getCantidad() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCantidad().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_CANTIDAD);
        }

        if (entity.getPrecioVenta() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_PRECIO_VENTA);
        }

        if ((entity.getPrecioVenta() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getPrecioVenta().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_PRECIO_VENTA);
        }

        if (entity.getValorIva() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_VALOR_IVA);
        }

        if ((entity.getValorIva() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorIva().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_VALOR_IVA);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "DevolucionDetalle";
        private static final String FIELD_CANTIDAD = "cantidad";
        private static final String FIELD_PRECIO_VENTA = "precioVenta";
        private static final String FIELD_VALOR_IVA = "valorIva";
    }

}
