package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IFacturaCabeceraDao;
import co.com.juan.invbill.dataaccess.api.impl.HibernateDaoImpl;
import co.com.juan.invbill.model.FacturaCabecera;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class FacturaCabeceraDao extends HibernateDaoImpl<FacturaCabecera, Integer> implements IFacturaCabeceraDao {

    @Inject
    public FacturaCabeceraDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
