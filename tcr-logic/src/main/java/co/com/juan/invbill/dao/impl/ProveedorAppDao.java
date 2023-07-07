package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IProveedorAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.ProveedorApp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("ProveedorAppDao")
public class ProveedorAppDao extends HibernateDaoImpl<ProveedorApp, Integer> implements IProveedorAppDao {

}
