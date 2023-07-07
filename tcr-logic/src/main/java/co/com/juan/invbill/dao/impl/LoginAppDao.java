package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ILoginAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.LoginApp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 *
 */
@Scope("singleton")
@Repository("LoginAppDao")
public class LoginAppDao extends HibernateDaoImpl<LoginApp, String> implements
        ILoginAppDao {

}
