package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.UsuarioApp;

/**
 * @author Juan Felipe
 */
public interface IUsuarioAppLogic {

    void saveUsuarioApp(UsuarioApp entity) throws EntityException;

    void updateUsuarioApp(UsuarioApp entity) throws EntityException;

    UsuarioApp getUsuarioApp(String id) throws EntityException;

}
