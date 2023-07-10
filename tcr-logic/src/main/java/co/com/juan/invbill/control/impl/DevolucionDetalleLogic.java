package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IDevolucionDetalleLogic;
import co.com.juan.invbill.dao.IDevolucionDetalleDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class DevolucionDetalleLogic implements IDevolucionDetalleLogic {

    private static final Logger log = LoggerFactory.getLogger(DevolucionDetalleLogic.class);
    private final IDevolucionDetalleDao devolucionDetalleDao;

    @Inject
    public DevolucionDetalleLogic(IDevolucionDetalleDao devolucionDetalleDao) {
        this.devolucionDetalleDao = devolucionDetalleDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDevolucionDetalle(DevolucionDetalle entity)  throws EntityException{
        try {
            this.checkFields(entity);
            this.devolucionDetalleDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDevolucionDetalle(DevolucionDetalle entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.devolucionDetalleDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DevolucionDetalle getDevolucionDetalle(DevolucionDetalleId id) throws EntityException {
        DevolucionDetalle entity;
        try {
            entity = this.devolucionDetalleDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
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
