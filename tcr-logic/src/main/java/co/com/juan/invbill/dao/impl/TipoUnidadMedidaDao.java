package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ITipoUnidadMedidaDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.TipoUnidadMedida;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Repository
public class TipoUnidadMedidaDao extends HibernateDaoImpl<TipoUnidadMedida, Integer> implements ITipoUnidadMedidaDao {

    @Inject
    public TipoUnidadMedidaDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
