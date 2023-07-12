package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IProductoLogic;
import co.com.juan.invbill.control.IStockProductoLogic;
import co.com.juan.invbill.dao.IProductoDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class ProductoLogic implements IProductoLogic {

    private static final Logger log = LoggerFactory.getLogger(ProductoLogic.class);
    private final IProductoDao productoDao;
    private final IStockProductoLogic stockProductoLogic;

    @Inject
    public ProductoLogic(IProductoDao productoDao, IStockProductoLogic stockProductoLogic) {
        this.productoDao = productoDao;
        this.stockProductoLogic = stockProductoLogic;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProducto() throws EntityException {
        List<Producto> list;
        try {
            list = this.productoDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProducto(Producto entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.productoDao.save(entity);
            StockProducto stockProducto = this.buildStockProducto(entity);
            this.stockProductoLogic.saveStockProducto(stockProducto);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProducto(Producto entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.productoDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Integer id) throws EntityException {
        Producto entity;
        try {
            entity = this.productoDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByPropertySort(String propertyName, Object value, String sortColumnName,
                                             boolean sortAscending) throws EntityException {
        List<Producto> list;
        try {
            list = this.productoDao.findByPropertySort(propertyName, value, sortColumnName, sortAscending);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByProperty(String propertyName, Collection<?> values) throws EntityException {
        List<Producto> list;
        try {
            list = this.productoDao.findByProperty(propertyName, values);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(Producto entity) {
        if (entity.getProducto() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_DES_PRODUCTO);
        }

        if ((entity.getProducto() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getProducto(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_DES_PRODUCTO);
        }

        if (entity.getEstado() == null) {
            entity.setEstado(StatusEnum.A);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ESTADO);
        }

        if (entity.getCategoriaProducto() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_CATEGORIA);
        }

        if ((entity.getCategoriaProducto() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getCategoriaProducto().getCategoria(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_CATEGORIA);
        }

        if (entity.getTipoUnidadMedida() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_TIPO_UNIDAD_MEDIDA);
        }

        if ((entity.getTipoUnidadMedida() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getTipoUnidadMedida().getUnidad(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_TIPO_UNIDAD_MEDIDA);
        }
    }

    private StockProducto buildStockProducto(Producto producto) {
        return StockProducto.builder()
                .producto(producto)
                .build();
    }

    private static class Constant {

        private static final String ENTITY_NAME = "Producto";
        private static final String FIELD_DES_PRODUCTO = "desProducto";
        private static final String FIELD_ESTADO = "estado";
        private static final String FIELD_CATEGORIA = "categoria";
        private static final String FIELD_TIPO_UNIDAD_MEDIDA = "tipoUnidadMedida";
    }

}
