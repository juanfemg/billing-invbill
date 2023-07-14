package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.IProveedorAppLogic;
import co.com.juan.invbill.delegate.businessdelegate.IProveedorDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ProveedorApp;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class ProveedorDelegate implements IProveedorDelegate {

    private final IProveedorAppLogic proveedorAppLogic;

    @Inject
    public ProveedorDelegate(IProveedorAppLogic proveedorAppLogic) {
        this.proveedorAppLogic = proveedorAppLogic;
    }

    @Override
    public void save(ProveedorApp entity) throws EntityException {
        this.proveedorAppLogic.saveProveedorApp(entity);
    }

    @Override
    public ProveedorApp findProveedorByID(Integer id) throws EntityException {
        return this.proveedorAppLogic.getProveedorApp(id);
    }

    @Override
    public void update(ProveedorApp entity) throws EntityException {
        this.proveedorAppLogic.updateProveedorApp(entity);
    }

    @Override
    public List<ProveedorApp> getProveedores() throws EntityException {
        return this.proveedorAppLogic.getProveedorApp();
    }

}
