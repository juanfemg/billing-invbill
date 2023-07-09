package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IStockProductoDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.StockProducto;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class StockProductoDao extends HibernateDaoImpl<StockProducto, Integer>
        implements IStockProductoDao {

    @Inject
    public StockProductoDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
