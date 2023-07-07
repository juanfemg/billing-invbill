package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IRolAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.RolApp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("RolAppDao")
public class RolAppDao extends HibernateDaoImpl<RolApp, Integer> implements
        IRolAppDao {

}
