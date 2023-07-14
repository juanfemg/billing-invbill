package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.model.TipoPeriodo;
import co.com.juan.invbill.model.TipoUnidadMedida;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IConfigDelegate {

    List<TipoPeriodo> getTiposPeriodo() throws EntityException;

    List<TipoUnidadMedida> getTiposUnidadMedida() throws EntityException;

    TipoUnidadMedida findTipoUnidadMedidaByID(Integer id) throws EntityException;

    void update(AppConfig entity) throws EntityException;

    List<AppConfig> getAppConfigs() throws EntityException;

    List<AppMenu> getAppMenus() throws EntityException;

    TipoPeriodo findTipoPeriodoByID(Integer id) throws EntityException;

}
