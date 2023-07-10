package co.com.juan.invbill.control;

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

}
