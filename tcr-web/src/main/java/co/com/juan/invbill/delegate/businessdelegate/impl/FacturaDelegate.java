package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.IDevolucionCabeceraLogic;
import co.com.juan.invbill.control.IDevolucionDetalleLogic;
import co.com.juan.invbill.control.IFacturaCabeceraLogic;
import co.com.juan.invbill.control.IFacturaDetalleLogic;
import co.com.juan.invbill.delegate.businessdelegate.IFacturaDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class FacturaDelegate implements IFacturaDelegate {

    private final IFacturaCabeceraLogic facturaCabeceraLogic;
    private final IFacturaDetalleLogic facturaDetalleLogic;
    private final IDevolucionCabeceraLogic devolucionCabeceraLogic;
    private final IDevolucionDetalleLogic devolucionDetalleLogic;

    @Inject
    public FacturaDelegate(IFacturaCabeceraLogic facturaCabeceraLogic, IFacturaDetalleLogic facturaDetalleLogic, IDevolucionCabeceraLogic devolucionCabeceraLogic, IDevolucionDetalleLogic devolucionDetalleLogic) {
        this.facturaCabeceraLogic = facturaCabeceraLogic;
        this.facturaDetalleLogic = facturaDetalleLogic;
        this.devolucionCabeceraLogic = devolucionCabeceraLogic;
        this.devolucionDetalleLogic = devolucionDetalleLogic;
    }

    @Override
    public void save(FacturaCabecera entity) throws EntityException {
        this.facturaCabeceraLogic.saveFacturaCabecera(entity);
    }

    @Override
    public FacturaCabecera findFacturaCabeceraByID(Integer id) throws EntityException {
        return this.facturaCabeceraLogic.getFacturaCabecera(id);
    }

    @Override
    public List<FacturaCabecera> getFacturaCabeceras() throws EntityException {
        return this.facturaCabeceraLogic.getDataFacturaCabecera();
    }

    @Override
    public List<FacturaCabecera> getFacturaCabecerasByIdAndOrFechaCreacion(FacturaCabecera entity) throws EntityException {
        return this.facturaCabeceraLogic.findByIdAndOrFechaCreacion(entity.getIdFactura(), entity.getFechaCreacion());
    }

    @Override
    public Object getMaximaFacturaCabeceraByPropertyName(String propertyName) throws EntityException {
        return this.facturaCabeceraLogic.findMaxObjectByCriteria(propertyName);
    }

    @Override
    public Object getMinimaFacturaCabeceraByPropertyName(String propertyName) throws EntityException {
        return this.facturaCabeceraLogic.findMinObjectByCriteria(propertyName);
    }

    @Override
    public void save(FacturaDetalle entity) throws EntityException {
        this.facturaDetalleLogic.saveFacturaDetalle(entity);
    }

    @Override
    public void save(DevolucionCabecera entity) throws EntityException {
        this.devolucionCabeceraLogic.saveDevolucionCabecera(entity);
    }

    @Override
    public DevolucionCabecera findDevolucionCabeceraByID(Integer id) throws EntityException {
        return this.devolucionCabeceraLogic.getDevolucionCabecera(id);
    }

    @Override
    public void update(DevolucionCabecera entity) throws EntityException {
        this.devolucionCabeceraLogic.updateDevolucionCabecera(entity);
    }

    @Override
    public List<DevolucionCabecera> getDevolucionCabeceras() throws EntityException {
        return this.devolucionCabeceraLogic.getDataDevolucionCabecera();
    }

    @Override
    public List<DevolucionCabecera> getDevolucionCabecerasByIdAndOrFechaCreacion(DevolucionCabecera entity) throws EntityException {
        return this.devolucionCabeceraLogic.findByIdAndOrFechaCreacion(entity.getIdFactura(), entity.getFechaCreacion());
    }

    @Override
    public void save(DevolucionDetalle entity) throws EntityException {
        this.devolucionDetalleLogic.saveDevolucionDetalle(entity);
    }

    @Override
    public DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto) throws EntityException {
        return this.devolucionDetalleLogic.getDevolucionDetalle(new DevolucionDetalleId(idFactura, idProducto));
    }

    @Override
    public void update(DevolucionDetalle entity) throws EntityException {
        this.devolucionDetalleLogic.updateDevolucionDetalle(entity);
    }

    @Override
    public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) throws EntityException {
        return this.facturaDetalleLogic.getFacturaDetalleDevolucionByIdFactura(idFactura);
    }

}
