package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IClienteDelegate {

    void save(ClienteApp entity) throws EntityException;

    ClienteApp findClienteByID(Integer id) throws EntityException;

    List<ClienteApp> getClientes() throws EntityException;

}
