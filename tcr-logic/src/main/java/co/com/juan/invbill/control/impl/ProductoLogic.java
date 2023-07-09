package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IProductoLogic;
import co.com.juan.invbill.dao.IProductoDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Service("ProductoLogic")
public class ProductoLogic implements IProductoLogic {

    private static final Logger log = LoggerFactory.getLogger(ProductoLogic.class);
    private final IProductoDao productoDao;

    @Inject
    public ProductoLogic(IProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProducto() {
        log.debug("finding all {} instances", Constant.ENTITY_NAME);

        List<Producto> list;

        try {
            list = productoDao.findAll();
        } catch (Exception e) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.GettingException(EntityException.ALL + Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProducto(Producto entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            productoDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProducto(Producto entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            productoDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Integer id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        Producto entity;

        try {
            entity = productoDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                         Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<Producto> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = productoDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<Producto> list;

        try {
            list = productoDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByPropertySort(String propertyName, Object value, String sortColumnName,
                                             boolean sortAscending) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<Producto> list;

        try {
            list = productoDao.findByPropertySort(propertyName, value, sortColumnName, sortAscending);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByProperty(String propertyName, Collection<?> values) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<Producto> list;

        try {
            list = productoDao.findByProperty(propertyName, values);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(Producto entity) {
        if (entity.getProducto() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_DES_PRODUCTO);
        }

        if ((entity.getProducto() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getProducto(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_DES_PRODUCTO);
        }

        if (entity.getEstado() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ESTADO);
        }

        if (entity.getCategoriaProducto() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_CATEGORIA);
        }

        if ((entity.getCategoriaProducto() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getCategoriaProducto().getCategoria(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_CATEGORIA);
        }

        if (entity.getTipoUnidadMedida() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_TIPO_UNIDAD_MEDIDA);
        }

        if ((entity.getTipoUnidadMedida() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getTipoUnidadMedida().getUnidad(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_TIPO_UNIDAD_MEDIDA);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "Producto";
        private static final String FIELD_DES_PRODUCTO = "desProducto";
        private static final String FIELD_ESTADO = "estado";
        private static final String FIELD_CATEGORIA = "categoria";
        private static final String FIELD_TIPO_UNIDAD_MEDIDA = "tipoUnidadMedida";
    }

}
