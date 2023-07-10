package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.LoginApp;

/**
 * @author Juan Felipe
 */
public interface ILoginAppLogic {

    void saveLoginApp(LoginApp entity) throws EntityException;

    void updateLoginApp(LoginApp entity) throws EntityException;

    LoginApp getLoginApp(String id) throws EntityException;

}
