package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;

/**
 * @author Juan Felipe
 */
public interface IFacturaDetalleLogic {

    void saveFacturaDetalle(FacturaDetalle entity) throws EntityException;

    void updateFacturaDetalle(FacturaDetalle entity) throws EntityException;

    List<FacturaDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                        Object[] variablesBetweenDates) throws EntityException;

    List<FacturaDetalle> findByProperty(String propertyName, Object value) throws EntityException;

    List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) throws EntityException;

}
