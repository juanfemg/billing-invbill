package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IStockProductoLogic;
import co.com.juan.invbill.dao.IStockProductoDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.StockProducto;
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
public class StockProductoLogic implements IStockProductoLogic {

    private static final Logger log = LoggerFactory.getLogger(StockProductoLogic.class);
    private final IStockProductoDao stockProductoDao;

    @Inject
    public StockProductoLogic(IStockProductoDao stockProductoDao) {
        this.stockProductoDao = stockProductoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProducto> getStockProducto() {
        List<StockProducto> list;
        try {
            list = this.stockProductoDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStockProducto(StockProducto entity) {
        try {
            this.checkFields(entity);
            this.stockProductoDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStockProducto(StockProducto entity) {
        try {
            this.checkFields(entity);
            this.stockProductoDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StockProducto getStockProducto(Integer id) {
        StockProducto entity;
        try {
            entity = this.stockProductoDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                              Object[] variablesBetweenDates) {
        List<StockProducto> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.stockProductoDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProducto> findByProperty(String propertyName, Object value) {
        List<StockProducto> list;
        try {
            list = this.stockProductoDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public StockProducto findObjectByProperty(String propertyName, Object value) {
        StockProducto entity;
        try {
            entity = this.stockProductoDao.findObjectByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findMaxObjectByCriteria(String propertyName) {
        Object object;
        try {
            object = this.stockProductoDao.maxByCriteria(propertyName);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findMinObjectByCriteria(String propertyName) {
        Object object;
        try {
            object = this.stockProductoDao.minByCriteria(propertyName);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findAvgObjectByCriteria(String propertyName) {
        Object object;
        try {
            object = this.stockProductoDao.avgByCriteria(propertyName);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    private void checkFields(StockProducto entity) {
        if (entity.getProducto() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_PRODUCTO);
        }

        if ((entity.getProducto() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getProducto().getProducto(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_PRODUCTO);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "StockProducto";
        private static final String FIELD_PRODUCTO = "producto";
    }

}
