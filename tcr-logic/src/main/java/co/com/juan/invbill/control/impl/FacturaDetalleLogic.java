package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IFacturaDetalleLogic;
import co.com.juan.invbill.dao.IFacturaDetalleDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class FacturaDetalleLogic implements IFacturaDetalleLogic {

    private static final Logger log = LoggerFactory.getLogger(FacturaDetalleLogic.class);
    private static final String DETALLE_FACTURA_DEVOLUCION = "SPDetalleFacturaDevolucion";
    private final IFacturaDetalleDao facturaDetalleDao;

    @Inject
    public FacturaDetalleLogic(IFacturaDetalleDao facturaDetalleDao) {
        this.facturaDetalleDao = facturaDetalleDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFacturaDetalle(FacturaDetalle entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.facturaDetalleDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateFacturaDetalle(FacturaDetalle entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.facturaDetalleDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) throws EntityException {
        List<FacturaDetalle> list;
        try {
            list = this.facturaDetalleDao.findByNamedQueryWithoutAlias(DETALLE_FACTURA_DEVOLUCION, idFactura);
        } catch (DaoException de) {
            log.error("get {} by IdFactura failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(FacturaDetalle entity) {
        if (entity.getCantidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_CANTIDAD);
        }

        if ((entity.getCantidad() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCantidad().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_CANTIDAD);
        }

        if (entity.getPrecioVenta() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_PRECIO_VENTA);
        }

        if ((entity.getPrecioVenta() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getPrecioVenta().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_PRECIO_VENTA);
        }

        if (entity.getValorIva() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_IVA);
        }

        if ((entity.getValorIva() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorIva().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_IVA);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "FacturaDetalle";
        private static final String FIELD_CANTIDAD = "cantidad";
        private static final String FIELD_PRECIO_VENTA = "precioVenta";
        private static final String FIELD_VALOR_IVA = "valorIva";
    }

}