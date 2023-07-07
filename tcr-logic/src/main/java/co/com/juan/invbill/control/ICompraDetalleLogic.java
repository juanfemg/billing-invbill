package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;

/**
 * @author Juan Felipe
 */
public interface ICompraDetalleLogic {

    List<CompraDetalle> getCompraDetalle();

    void saveCompraDetalle(CompraDetalle entity);

    void updateCompraDetalle(CompraDetalle entity);

    CompraDetalle getCompraDetalle(CompraDetalleId id);

    List<CompraDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                       Object[] variablesBetweenDates);

    List<CompraDetalle> findByProperty(String propertyName, Object value);

}
