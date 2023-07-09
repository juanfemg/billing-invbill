package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ICompraDetalleLogic;
import co.com.juan.invbill.dao.ICompraDetalleDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;
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
public class CompraDetalleLogic implements ICompraDetalleLogic {

    private static final Logger log = LoggerFactory.getLogger(CompraDetalleLogic.class);
    private final ICompraDetalleDao compraDetalleDao;

    @Inject
    public CompraDetalleLogic(ICompraDetalleDao compraDetalleDao) {
        this.compraDetalleDao = compraDetalleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraDetalle> getCompraDetalle() {
        List<CompraDetalle> list;
        try {
            list = this.compraDetalleDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCompraDetalle(CompraDetalle entity) {
        try {
            this.checkFields(entity);
            this.compraDetalleDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCompraDetalle(CompraDetalle entity) {
        try {
            this.checkFields(entity);
            this.compraDetalleDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CompraDetalle getCompraDetalle(CompraDetalleId id) {
        CompraDetalle entity;
        try {
            entity = this.compraDetalleDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                              Object[] variablesBetweenDates) {
        List<CompraDetalle> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.compraDetalleDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraDetalle> findByProperty(String propertyName, Object value) {
        List<CompraDetalle> list;
        try {
            list = this.compraDetalleDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(CompraDetalle entity) {
        if (entity.getCantidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_CANTIDAD);
        }

        if ((entity.getCantidad() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCantidad().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_CANTIDAD);
        }

        if (entity.getPrecioCompra() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_PRECIO_VENTA);
        }

        if ((entity.getPrecioCompra() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getPrecioCompra().toString(), 22, 2))) {
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

        private static final String ENTITY_NAME = "CompraDetalle";
        private static final String FIELD_CANTIDAD = "cantidad";
        private static final String FIELD_PRECIO_VENTA = "precioVenta";
        private static final String FIELD_VALOR_IVA = "valorIva";
    }

}