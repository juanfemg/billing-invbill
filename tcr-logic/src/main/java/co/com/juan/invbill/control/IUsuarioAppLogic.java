package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.UsuarioApp;

/**
 * @author Juan Felipe
 */
public interface IUsuarioAppLogic {

    void saveUsuarioApp(UsuarioApp entity);

    void updateUsuarioApp(UsuarioApp entity);

    UsuarioApp getUsuarioApp(String id);

    List<UsuarioApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                    Object[] variablesBetweenDates);

    List<UsuarioApp> findByProperty(String propertyName, Object value);

}
