package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ICompraCabeceraDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class CompraCabeceraDao extends HibernateDaoImpl<CompraCabecera, CompraCabeceraId>
        implements ICompraCabeceraDao {

    @Inject
    public CompraCabeceraDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
