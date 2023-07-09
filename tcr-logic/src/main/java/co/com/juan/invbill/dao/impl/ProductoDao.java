package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IProductoDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.Producto;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class ProductoDao extends HibernateDaoImpl<Producto, Integer> implements
        IProductoDao {

    @Inject
    public ProductoDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
