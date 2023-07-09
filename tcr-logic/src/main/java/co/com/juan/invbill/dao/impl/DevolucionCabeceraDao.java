package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IDevolucionCabeceraDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.DevolucionCabecera;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class DevolucionCabeceraDao extends HibernateDaoImpl<DevolucionCabecera, Integer>
        implements IDevolucionCabeceraDao {

    @Inject
    public DevolucionCabeceraDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
