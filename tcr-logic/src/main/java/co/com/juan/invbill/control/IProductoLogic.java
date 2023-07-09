package co.com.juan.invbill.control;

import java.util.Collection;
import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.Producto;

/**
 * @author Juan Felipe
 */
public interface IProductoLogic {

    List<Producto> getProducto() throws EntityException;

    void saveProducto(Producto entity) throws EntityException;

    void updateProducto(Producto entity) throws EntityException;

    Producto getProducto(Integer id) throws EntityException;

    List<Producto> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates) throws EntityException;

    List<Producto> findByProperty(String propertyName, Object value) throws EntityException;

    List<Producto> findByPropertySort(String propertyName, Object value, String sortColumnName,
                                      boolean sortAscending) throws EntityException;

    List<Producto> findByProperty(String propertyName, Collection<?> values) throws EntityException;

}
