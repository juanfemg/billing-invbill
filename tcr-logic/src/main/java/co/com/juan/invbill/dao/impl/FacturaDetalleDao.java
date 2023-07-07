package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IFacturaDetalleDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("FacturaDetalleDao")
public class FacturaDetalleDao extends
        HibernateDaoImpl<FacturaDetalle, FacturaDetalleId> implements
        IFacturaDetalleDao {

}
