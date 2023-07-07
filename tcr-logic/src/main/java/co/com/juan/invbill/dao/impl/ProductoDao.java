package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IProductoDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.Producto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("ProductoDao")
public class ProductoDao extends HibernateDaoImpl<Producto, Integer> implements
        IProductoDao {

}
