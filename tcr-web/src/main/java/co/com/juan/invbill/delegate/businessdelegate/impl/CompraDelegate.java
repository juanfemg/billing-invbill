package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.ICompraCabeceraLogic;
import co.com.juan.invbill.control.ICompraDetalleLogic;
import co.com.juan.invbill.delegate.businessdelegate.ICompraDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.Producto;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class CompraDelegate implements ICompraDelegate {

    private final ICompraCabeceraLogic compraCabeceraLogic;
    private final ICompraDetalleLogic compraDetalleLogic;

    @Inject
    public CompraDelegate(ICompraCabeceraLogic compraCabeceraLogic, ICompraDetalleLogic compraDetalleLogic) {
        this.compraCabeceraLogic = compraCabeceraLogic;
        this.compraDetalleLogic = compraDetalleLogic;
    }

    @Override
    public void save(CompraCabecera entity) throws EntityException {
        this.compraCabeceraLogic.saveCompraCabecera(entity);
    }

    @Override
    public CompraCabecera findCompraCabeceraByID(CompraCabeceraId id) throws EntityException {
        return this.compraCabeceraLogic.getCompraCabecera(id);
    }

    @Override
    public void update(CompraCabecera entity) throws EntityException {
        this.compraCabeceraLogic.updateCompraCabecera(entity);
    }

    @Override
    public List<CompraCabecera> getCompraCabecerasByIdFacturaAndOrIdProveedor() throws EntityException {
        return this.compraCabeceraLogic.getDataCompraCabecera();
    }

    @Override
    public List<CompraCabecera> getCompraCabecerasByIdFacturaAndOrIdProveedor(CompraCabeceraId id) throws EntityException {
        return this.compraCabeceraLogic.findByIdFacturaAndOrIdProveedor(id.getIdFacturaCompra(), id.getIdProveedorApp());
    }

    @Override
    public void save(CompraDetalle entity) throws EntityException {
        this.compraDetalleLogic.saveCompraDetalle(entity);
    }

    @Override
    public void update(CompraDetalle entity) throws EntityException {
        this.compraDetalleLogic.updateCompraDetalle(entity);
    }

    @Override
    public List<CompraDetalle> findCompraDetalleByProducto(Producto producto) throws EntityException {
        return this.compraDetalleLogic.findByProperty("producto", producto);
    }

}
