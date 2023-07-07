package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IClienteAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.ClienteApp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("ClienteAppDao")
public class ClienteAppDao extends HibernateDaoImpl<ClienteApp, Integer> implements IClienteAppDao {

}
