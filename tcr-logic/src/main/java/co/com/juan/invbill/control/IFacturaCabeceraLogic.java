package co.com.juan.invbill.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.FacturaCabecera;

/**
 * @author Juan Felipe
 */
public interface IFacturaCabeceraLogic {

    void saveFacturaCabecera(FacturaCabecera entity) throws EntityException;

    void updateFacturaCabecera(FacturaCabecera entity) throws EntityException;

    FacturaCabecera getFacturaCabecera(Integer id) throws EntityException;

    List<FacturaCabecera> getDataFacturaCabecera() throws EntityException;

    List<FacturaCabecera> findByIdAndOrFechaCreacion(Integer idFactura, Date fechaCreacion) throws EntityException;

    Object findMaxObjectByCriteria(String propertyName) throws EntityException;

    Object findMinObjectByCriteria(String propertyName) throws EntityException;

}
