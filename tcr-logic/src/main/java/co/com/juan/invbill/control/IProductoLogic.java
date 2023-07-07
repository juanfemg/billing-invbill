package co.com.juan.invbill.control;

import java.util.Collection;
import java.util.List;

import co.com.juan.invbill.model.Producto;

/**
 * @author Juan Felipe
 */
public interface IProductoLogic {

    List<Producto> getProducto();

    void saveProducto(Producto entity);

    void updateProducto(Producto entity);

    Producto getProducto(Integer id);

    List<Producto> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

    List<Producto> findByProperty(String propertyName, Object value);

    List<Producto> findByPropertySort(String propertyName, Object value, String sortColumnName,
                                      boolean sortAscending);

    List<Producto> findByProperty(String propertyName, Collection<?> values);

}
