package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.TipoUnidadMedida;

/**
 * @author Juan Felipe
 */
public interface ITipoUnidadMedidaLogic {

    List<TipoUnidadMedida> getTipoUnidadMedida() throws EntityException;

    void saveTipoUnidadMedida(TipoUnidadMedida entity) throws EntityException;

    void updateTipoUnidadMedida(TipoUnidadMedida entity) throws EntityException;

    TipoUnidadMedida getTipoUnidadMedida(Integer id) throws EntityException;

}
