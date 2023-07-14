package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.model.UsuarioApp;

/**
 * @author Juan Felipe
 */
public interface IUsuarioDelegate {

    void save(LoginApp entity) throws EntityException;

    LoginApp findLoginByID(String id) throws EntityException;

    void update(LoginApp entity) throws EntityException;

    void save(UsuarioApp entity) throws EntityException;

    UsuarioApp findUsuarioByID(String id) throws EntityException;

    void update(UsuarioApp entity) throws EntityException;

}
