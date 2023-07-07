package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IStockProductoDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.StockProducto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("StockProductoDao")
public class StockProductoDao extends HibernateDaoImpl<StockProducto, Integer>
        implements IStockProductoDao {

}
