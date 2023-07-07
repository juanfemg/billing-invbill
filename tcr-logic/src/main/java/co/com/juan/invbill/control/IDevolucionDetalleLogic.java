package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;

/**
 * @author Juan Felipe
 */
public interface IDevolucionDetalleLogic {

    List<DevolucionDetalle> getDevolucionDetalle();

    void saveDevolucionDetalle(DevolucionDetalle entity);

    void updateDevolucionDetalle(DevolucionDetalle entity);

    DevolucionDetalle getDevolucionDetalle(DevolucionDetalleId id);

    List<DevolucionDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                           Object[] variablesBetweenDates);

    List<DevolucionDetalle> findByProperty(String propertyName, Object value);

}
