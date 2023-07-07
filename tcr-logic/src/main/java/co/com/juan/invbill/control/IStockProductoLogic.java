package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.StockProducto;

/**
 * @author Juan Felipe
 */
public interface IStockProductoLogic {

    List<StockProducto> getStockProducto();

    void saveStockProducto(StockProducto entity);

    void updateStockProducto(StockProducto entity);

    StockProducto getStockProducto(Integer id);

    List<StockProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                       Object[] variablesBetweenDates);

    List<StockProducto> findByProperty(String propertyName, Object value);

    StockProducto findObjectByProperty(String propertyName, Object value);

    Object findMaxObjectByCriteria(String propertyName);

    Object findMinObjectByCriteria(String propertyName);

    Object findAvgObjectByCriteria(String propertyName);

}
