package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IDevolucionCabeceraDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.DevolucionCabecera;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("DevolucionCabeceraDao")
public class DevolucionCabeceraDao extends HibernateDaoImpl<DevolucionCabecera, Integer>
        implements IDevolucionCabeceraDao {

}
