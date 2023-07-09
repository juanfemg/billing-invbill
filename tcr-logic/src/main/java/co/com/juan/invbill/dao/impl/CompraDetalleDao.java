package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ICompraDetalleDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class CompraDetalleDao extends
        HibernateDaoImpl<CompraDetalle, CompraDetalleId> implements
        ICompraDetalleDao {

    @Inject
    public CompraDetalleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
