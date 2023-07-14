package co.com.juan.invbill.control;

import java.util.Date;
import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionCabecera;

/**
 * @author Juan Felipe
 */
public interface IDevolucionCabeceraLogic {

    void saveDevolucionCabecera(DevolucionCabecera entity) throws EntityException;

    void updateDevolucionCabecera(DevolucionCabecera entity) throws EntityException;

    DevolucionCabecera getDevolucionCabecera(Integer id) throws EntityException;

    List<DevolucionCabecera> getDataDevolucionCabecera() throws EntityException;

    List<DevolucionCabecera> findByIdAndOrFechaCreacion(Integer idFactura, Date fechaCreacion) throws EntityException;

}
