package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ICategoriaProductoDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.CategoriaProducto;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class CategoriaProductoDao extends
        HibernateDaoImpl<CategoriaProducto, Integer> implements
        ICategoriaProductoDao {

    @Inject
    public CategoriaProductoDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
