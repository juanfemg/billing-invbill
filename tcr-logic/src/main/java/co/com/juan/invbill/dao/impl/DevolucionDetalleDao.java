package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IDevolucionDetalleDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("DevolucionDetalleDao")
public class DevolucionDetalleDao extends HibernateDaoImpl<DevolucionDetalle, DevolucionDetalleId>
        implements IDevolucionDetalleDao {

}
