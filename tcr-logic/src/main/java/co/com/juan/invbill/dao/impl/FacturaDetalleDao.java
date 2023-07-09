package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IFacturaDetalleDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class FacturaDetalleDao extends
        HibernateDaoImpl<FacturaDetalle, FacturaDetalleId> implements
        IFacturaDetalleDao {

    @Inject
    public FacturaDetalleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
