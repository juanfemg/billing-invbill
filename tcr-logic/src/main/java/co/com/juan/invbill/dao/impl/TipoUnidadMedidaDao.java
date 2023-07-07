package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ITipoUnidadMedidaDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.TipoUnidadMedida;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("TipoUnidadMedidaDao")
public class TipoUnidadMedidaDao extends HibernateDaoImpl<TipoUnidadMedida, Integer> implements ITipoUnidadMedidaDao {

}
