package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;

/**
 * @author Juan Felipe
 */
public interface ICompraCabeceraLogic {

    void saveCompraCabecera(CompraCabecera entity) throws EntityException;

    void updateCompraCabecera(CompraCabecera entity) throws EntityException;

    CompraCabecera getCompraCabecera(CompraCabeceraId id) throws EntityException;

    List<CompraCabecera> getDataCompraCabecera() throws EntityException;

    List<CompraCabecera> findByIdFacturaAndOrIdProveedor(String idFacturaCompra, Integer idProveedorApp) throws EntityException;

}
