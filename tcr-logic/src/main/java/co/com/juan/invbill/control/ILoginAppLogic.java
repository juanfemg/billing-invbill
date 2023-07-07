package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.LoginApp;

/**
 * @author Juan Felipe
 */
public interface ILoginAppLogic {

    void saveLoginApp(LoginApp entity);

    void updateLoginApp(LoginApp entity);

    LoginApp getLoginApp(String id);

    List<LoginApp> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

    List<LoginApp> findByProperty(String propertyName, Object value);

}
