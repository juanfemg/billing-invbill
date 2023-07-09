package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ProveedorApp;

/**
 * @author Juan Felipe
 */
public interface IProveedorAppLogic {

    List<ProveedorApp> getProveedorApp() throws EntityException;

    void saveProveedorApp(ProveedorApp entity) throws EntityException;

    void updateProveedorApp(ProveedorApp entity) throws EntityException;

    ProveedorApp getProveedorApp(Integer id) throws EntityException;

    List<ProveedorApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                      Object[] variablesBetweenDates) throws EntityException;

    List<ProveedorApp> findByProperty(String propertyName, Object value) throws EntityException;

}
