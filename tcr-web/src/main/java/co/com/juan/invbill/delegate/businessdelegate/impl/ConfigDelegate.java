package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.IAppConfigLogic;
import co.com.juan.invbill.control.IAppMenuLogic;
import co.com.juan.invbill.control.ITipoPeriodoLogic;
import co.com.juan.invbill.control.ITipoUnidadMedidaLogic;
import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.model.TipoPeriodo;
import co.com.juan.invbill.model.TipoUnidadMedida;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class ConfigDelegate implements IConfigDelegate {

    private final IAppConfigLogic appConfigLogic;
    private final IAppMenuLogic appMenuLogic;
    private final ITipoUnidadMedidaLogic tipoUnidadMedidaLogic;
    private final ITipoPeriodoLogic tipoPeriodoLogic;

    @Inject
    public ConfigDelegate(IAppConfigLogic appConfigLogic, IAppMenuLogic appMenuLogic, ITipoUnidadMedidaLogic tipoUnidadMedidaLogic, ITipoPeriodoLogic tipoPeriodoLogic) {
        this.appConfigLogic = appConfigLogic;
        this.appMenuLogic = appMenuLogic;
        this.tipoUnidadMedidaLogic = tipoUnidadMedidaLogic;
        this.tipoPeriodoLogic = tipoPeriodoLogic;
    }

    @Override
    public void update(AppConfig entity) throws EntityException {
        this.appConfigLogic.updateAppConfig(entity);
    }

    @Override
    public List<AppConfig> getAppConfigs() throws EntityException {
        return this.appConfigLogic.getDataAppConfig();
    }

    @Override
    public List<AppMenu> getAppMenus() throws EntityException {
        return this.appMenuLogic.getDataAppMenu();
    }

    @Override
    public TipoPeriodo findTipoPeriodoByID(Integer id) throws EntityException {
        return this.tipoPeriodoLogic.getTipoPeriodo(id);
    }

    @Override
    public List<TipoUnidadMedida> getTiposUnidadMedida() throws EntityException {
        return this.tipoUnidadMedidaLogic.getTipoUnidadMedida();
    }

    @Override
    public TipoUnidadMedida findTipoUnidadMedidaByID(Integer id) throws EntityException {
        return this.tipoUnidadMedidaLogic.getTipoUnidadMedida(id);
    }

    @Override
    public List<TipoPeriodo> getTiposPeriodo() throws EntityException {
        return this.tipoPeriodoLogic.getTipoPeriodo();
    }

}
