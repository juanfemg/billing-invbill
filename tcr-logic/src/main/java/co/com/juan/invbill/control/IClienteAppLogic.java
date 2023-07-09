package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ClienteApp;

/**
 * @author Juan Felipe
 */
public interface IClienteAppLogic {

    List<ClienteApp> getClienteApp() throws EntityException;

    void saveClienteApp(ClienteApp entity) throws EntityException;

    void updateClienteApp(ClienteApp entity) throws EntityException;

    ClienteApp getClienteApp(Integer id) throws EntityException;

    List<ClienteApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                    Object[] variablesBetweenDates) throws EntityException;

    List<ClienteApp> findByProperty(String propertyName, Object value) throws EntityException;

}
