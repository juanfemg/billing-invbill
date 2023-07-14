package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.*;
import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class ClienteDelegate implements IClienteDelegate {

    private final IClienteAppLogic clienteAppLogic;

    @Inject
    public ClienteDelegate(IClienteAppLogic clienteAppLogic) {
        this.clienteAppLogic = clienteAppLogic;
    }

    @Override
    public void save(ClienteApp entity) throws EntityException {
        this.clienteAppLogic.saveClienteApp(entity);
    }

    @Override
    public ClienteApp findClienteByID(Integer id) throws EntityException {
        return this.clienteAppLogic.getClienteApp(id);
    }

    @Override
    public List<ClienteApp> getClientes() throws EntityException {
        return this.clienteAppLogic.getClienteApp();
    }

}
