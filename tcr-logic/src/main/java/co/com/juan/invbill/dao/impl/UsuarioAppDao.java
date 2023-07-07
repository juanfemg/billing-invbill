package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IUsuarioAppDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.UsuarioApp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("UsuarioAppDao")
public class UsuarioAppDao extends HibernateDaoImpl<UsuarioApp, String>
        implements IUsuarioAppDao {

}
