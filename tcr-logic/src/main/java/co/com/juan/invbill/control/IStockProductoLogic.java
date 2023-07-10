package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.StockProducto;

/**
 * @author Juan Felipe
 */
public interface IStockProductoLogic {

    List<StockProducto> getStockProducto() throws EntityException;

    void saveStockProducto(StockProducto entity) throws EntityException;

    void updateStockProducto(StockProducto entity) throws EntityException;

    StockProducto findObjectByProperty(String propertyName, Object value) throws EntityException;

    Object findMaxObjectByCriteria(String propertyName) throws EntityException;

}
