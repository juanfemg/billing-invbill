package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ICategoriaProductoLogic;
import co.com.juan.invbill.dao.ICategoriaProductoDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
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
public class CategoriaProductoLogic implements ICategoriaProductoLogic {

    private static final Logger log = LoggerFactory.getLogger(CategoriaProductoLogic.class);
    private final ICategoriaProductoDao categoriaProductoDao;

    @Inject
    public CategoriaProductoLogic(ICategoriaProductoDao categoriaProductoDao) {
        this.categoriaProductoDao = categoriaProductoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProducto> getCategoriaProducto() throws EntityException {
        List<CategoriaProducto> list;
        try {
            list = this.categoriaProductoDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCategoriaProducto(CategoriaProducto entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.categoriaProductoDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCategoriaProducto(CategoriaProducto entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.categoriaProductoDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaProducto getCategoriaProducto(Integer id) throws EntityException {
        CategoriaProducto entity;
        try {
            entity = this.categoriaProductoDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending) throws EntityException {
        List<CategoriaProducto> entity;
        try {
            entity = this.categoriaProductoDao.findPage(sortColumnName, sortAscending);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                                  Object[] variablesBetweenDates) throws EntityException {
        List<CategoriaProducto> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.categoriaProductoDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProducto> findByProperty(String propertyName, Object value) throws EntityException {
        List<CategoriaProducto> list;
        try {
            list = this.categoriaProductoDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(CategoriaProducto entity) {
        if (entity.getCategoria() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_CATEGORIA);
        }

        if ((entity.getCategoria() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getCategoria(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_CATEGORIA);
        }

        if (entity.getEstado() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ESTADO);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "CategoriaProducto";
        private static final String FIELD_CATEGORIA = "categoria";
        private static final String FIELD_ESTADO = "estado";
    }

}
