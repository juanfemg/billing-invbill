package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;

/**
 * @author Juan Felipe
 */
public interface IFacturaDetalleLogic {

    List<FacturaDetalle> getFacturaDetalle();

    void saveFacturaDetalle(FacturaDetalle entity);

    void updateFacturaDetalle(FacturaDetalle entity);

    FacturaDetalle getFacturaDetalle(FacturaDetalleId id);

    List<FacturaDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                        Object[] variablesBetweenDates);

    List<FacturaDetalle> findByProperty(String propertyName, Object value);

    List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura);

}
