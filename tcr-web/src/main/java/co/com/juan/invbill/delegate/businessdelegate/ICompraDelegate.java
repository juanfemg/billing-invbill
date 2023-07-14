package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.Producto;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface ICompraDelegate {

    void save(CompraCabecera entity) throws EntityException;

    CompraCabecera findCompraCabeceraByID(CompraCabeceraId id) throws EntityException;

    List<CompraCabecera> getCompraCabecerasByIdFacturaAndOrIdProveedor() throws EntityException;

    List<CompraCabecera> getCompraCabecerasByIdFacturaAndOrIdProveedor(CompraCabeceraId id) throws EntityException;

    void save(CompraDetalle entity) throws EntityException;

    List<CompraDetalle> findCompraDetalleByProducto(Producto producto) throws EntityException;

}
