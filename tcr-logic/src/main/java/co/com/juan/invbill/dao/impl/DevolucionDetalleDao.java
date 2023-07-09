package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IDevolucionDetalleDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class DevolucionDetalleDao extends HibernateDaoImpl<DevolucionDetalle, DevolucionDetalleId>
        implements IDevolucionDetalleDao {

    @Inject
    public DevolucionDetalleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
