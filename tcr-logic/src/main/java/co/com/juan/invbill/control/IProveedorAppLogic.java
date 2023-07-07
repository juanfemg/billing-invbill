package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.ProveedorApp;

/**
 * @author Juan Felipe
 */
public interface IProveedorAppLogic {

    List<ProveedorApp> getProveedorApp();

    void saveProveedorApp(ProveedorApp entity);

    void updateProveedorApp(ProveedorApp entity);

    ProveedorApp getProveedorApp(Integer id);

    List<ProveedorApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                      Object[] variablesBetweenDates);

    List<ProveedorApp> findByProperty(String propertyName, Object value);

}
