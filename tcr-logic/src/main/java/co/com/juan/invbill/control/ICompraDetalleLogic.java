package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;

/**
 * @author Juan Felipe
 */
public interface ICompraDetalleLogic {

    void saveCompraDetalle(CompraDetalle entity) throws EntityException;

    void updateCompraDetalle(CompraDetalle entity) throws EntityException;

    List<CompraDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
                                       Object[] variablesBetweenDates) throws EntityException;

    List<CompraDetalle> findByProperty(String propertyName, Object value) throws EntityException;

}
