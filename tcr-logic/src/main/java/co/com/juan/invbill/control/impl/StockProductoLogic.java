package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IStockProductoLogic;
import co.com.juan.invbill.dao.IStockProductoDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.StockProducto;
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
@Service("StockProductoLogic")
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
        log.debug("finding all {} instances", Constant.ENTITY_NAME);

        List<StockProducto> list;

        try {
            list = stockProductoDao.findAll();
        } catch (Exception e) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.GettingException(EntityException.ALL + Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStockProducto(StockProducto entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            stockProductoDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStockProducto(StockProducto entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            stockProductoDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StockProducto getStockProducto(Integer id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        StockProducto entity;

        try {
            entity = stockProductoDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                              Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<StockProducto> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = stockProductoDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProducto> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<StockProducto> list;

        try {
            list = stockProductoDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public StockProducto findObjectByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        StockProducto entity;

        try {
            entity = stockProductoDao.findObjectByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
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

    @Override
    @Transactional(readOnly = true)
    public Object findMaxObjectByCriteria(String propertyName) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        Object object;

        try {
            object = stockProductoDao.maxByCriteria(propertyName);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findMinObjectByCriteria(String propertyName) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        Object object;

        try {
            object = stockProductoDao.minByCriteria(propertyName);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findAvgObjectByCriteria(String propertyName) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        Object object;

        try {
            object = stockProductoDao.avgByCriteria(propertyName);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "StockProducto";
        private static final String FIELD_PRODUCTO = "producto";
    }

}
