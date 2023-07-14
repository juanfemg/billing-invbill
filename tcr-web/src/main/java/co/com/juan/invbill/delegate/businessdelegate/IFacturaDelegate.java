package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionCabecera;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.FacturaCabecera;
import co.com.juan.invbill.model.FacturaDetalle;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IFacturaDelegate {

    void save(FacturaCabecera entity) throws EntityException;

    FacturaCabecera findFacturaCabeceraByID(Integer id) throws EntityException;

    void update(FacturaCabecera entity) throws EntityException;

    List<FacturaCabecera> getFacturaCabeceras() throws EntityException;

    List<FacturaCabecera> getFacturaCabecerasByIdAndOrFechaCreacion(FacturaCabecera entity) throws EntityException;

    Object getMaximaFacturaCabeceraByPropertyName(String propertyName) throws EntityException;

    Object getMinimaFacturaCabeceraByPropertyName(String propertyName) throws EntityException;

    void save(FacturaDetalle entity) throws EntityException;

    void update(FacturaDetalle entity) throws EntityException;

    List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) throws EntityException;

    void save(DevolucionCabecera entity) throws EntityException;

    DevolucionCabecera findDevolucionCabeceraByID(Integer id) throws EntityException;

    void update(DevolucionCabecera entity) throws EntityException;

    List<DevolucionCabecera> getDevolucionCabeceras() throws EntityException;

    List<DevolucionCabecera> getDevolucionCabecerasByIdAndOrFechaCreacion(DevolucionCabecera entity) throws EntityException;

    void save(DevolucionDetalle entity) throws EntityException;

    DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto) throws EntityException;

    void update(DevolucionDetalle entity) throws EntityException;

}
