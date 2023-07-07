package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ICompraDetalleDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("CompraDetalleDao")
public class CompraDetalleDao extends
        HibernateDaoImpl<CompraDetalle, CompraDetalleId> implements
        ICompraDetalleDao {

}
