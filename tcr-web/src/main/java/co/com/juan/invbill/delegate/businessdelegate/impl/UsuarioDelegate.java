package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.ILoginAppLogic;
import co.com.juan.invbill.control.IUsuarioAppLogic;
import co.com.juan.invbill.delegate.businessdelegate.IUsuarioDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.model.UsuarioApp;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Juan Felipe
 */
@Component
public class UsuarioDelegate implements IUsuarioDelegate {

    private final IUsuarioAppLogic usuarioAppLogic;
    private final ILoginAppLogic loginAppLogic;

    @Inject
    public UsuarioDelegate(IUsuarioAppLogic usuarioAppLogic, ILoginAppLogic loginAppLogic) {
        this.usuarioAppLogic = usuarioAppLogic;
        this.loginAppLogic = loginAppLogic;
    }

    @Override
    public void save(LoginApp entity) throws EntityException {
        this.loginAppLogic.saveLoginApp(entity);
    }

    @Override
    public LoginApp findLoginByID(String id) throws EntityException {
        return this.loginAppLogic.getLoginApp(id);
    }

    @Override
    public void update(LoginApp entity) throws EntityException {
        this.loginAppLogic.updateLoginApp(entity);
    }

    @Override
    public void save(UsuarioApp entity) throws EntityException {
        this.usuarioAppLogic.saveUsuarioApp(entity);
    }

    @Override
    public UsuarioApp findUsuarioByID(String id) throws EntityException {
        return this.usuarioAppLogic.getUsuarioApp(id);
    }

    @Override
    public void update(UsuarioApp entity) throws EntityException {
        this.usuarioAppLogic.updateUsuarioApp(entity);
    }

}
