package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ProveedorApp;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IProveedorDelegate {

    void save(ProveedorApp entity) throws EntityException;

    ProveedorApp findProveedorByID(Integer id) throws EntityException;

    void update(ProveedorApp entity) throws EntityException;

    List<ProveedorApp> getProveedores() throws EntityException;

}
