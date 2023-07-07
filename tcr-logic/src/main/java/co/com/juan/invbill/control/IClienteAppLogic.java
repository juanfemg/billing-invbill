package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.ClienteApp;

/**
 * @author Juan Felipe
 */
public interface IClienteAppLogic {

    List<ClienteApp> getClienteApp();

    void saveClienteApp(ClienteApp entity);

    void updateClienteApp(ClienteApp entity);

    ClienteApp getClienteApp(Integer id);

    List<ClienteApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                    Object[] variablesBetweenDates);

    List<ClienteApp> findByProperty(String propertyName, Object value);

}
