package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.TipoUnidadMedida;

/**
 * @author Juan Felipe
 */
public interface ITipoUnidadMedidaLogic {

    List<TipoUnidadMedida> getTipoUnidadMedida();

    void saveTipoUnidadMedida(TipoUnidadMedida entity);

    void updateTipoUnidadMedida(TipoUnidadMedida entity);

    TipoUnidadMedida getTipoUnidadMedida(Integer id);

    List<TipoUnidadMedida> findByCriteria(Object[] variables, Object[] variablesBetween,
                                          Object[] variablesBetweenDates);

    List<TipoUnidadMedida> findByProperty(String propertyName, Object value);

}
