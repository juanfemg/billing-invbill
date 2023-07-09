package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;

/**
 * @author Juan Felipe
 */
public interface IDevolucionDetalleLogic {

    void saveDevolucionDetalle(DevolucionDetalle entity) throws EntityException;

    void updateDevolucionDetalle(DevolucionDetalle entity) throws EntityException;

    DevolucionDetalle getDevolucionDetalle(DevolucionDetalleId id) throws EntityException;

    List<DevolucionDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                           Object[] variablesBetweenDates) throws EntityException;

    List<DevolucionDetalle> findByProperty(String propertyName, Object value) throws EntityException;

}
