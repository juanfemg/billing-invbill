package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IBusinessDelegate {

    void save(LoginApp entity) throws EntityException;

    LoginApp findLoginByID(String id) throws EntityException;

    void update(LoginApp entity) throws EntityException;

    void save(UsuarioApp entity) throws EntityException;

    UsuarioApp findUsuarioByID(String id) throws EntityException;

    void update(UsuarioApp entity) throws EntityException;

    void save(FacturaCabecera entity) throws EntityException;

    FacturaCabecera findFacturaCabeceraByID(Integer id) throws EntityException;

    void update(FacturaCabecera entity) throws EntityException;

    List<FacturaCabecera> getFacturaCabeceras() throws EntityException;

    List<FacturaCabecera> getFacturaCabecerasByCriteria(FacturaCabecera entity) throws EntityException;

    Object getMaximaFacturaCabeceraByPropertyName(String propertyName) throws EntityException;

    Object getMinimaFacturaCabeceraByPropertyName(String propertyName) throws EntityException;

    void save(FacturaDetalle entity) throws EntityException;

    void update(FacturaDetalle entity) throws EntityException;

    void save(ProveedorApp entity) throws EntityException;

    ProveedorApp findProveedorByID(Integer id) throws EntityException;

    void update(ProveedorApp entity) throws EntityException;

    List<ProveedorApp> getProveedores() throws EntityException;

    void save(CompraCabecera entity) throws EntityException;

    CompraCabecera findCompraCabeceraByID(CompraCabeceraId id) throws EntityException;

    void update(CompraCabecera entity) throws EntityException;

    List<CompraCabecera> getCompraCabeceras() throws EntityException;

    List<CompraCabecera> getCompraCabeceras(CompraCabeceraId id) throws EntityException;

    void save(CompraDetalle entity) throws EntityException;

    void update(CompraDetalle entity) throws EntityException;

    void save(DevolucionCabecera entity) throws EntityException;

    DevolucionCabecera findDevolucionCabeceraByID(Integer id) throws EntityException;

    void update(DevolucionCabecera entity) throws EntityException;

    List<DevolucionCabecera> getDevolucionCabeceras() throws EntityException;

    List<DevolucionCabecera> getDevolucionCabecerasByCriteria(DevolucionCabecera entity) throws EntityException;

    void save(DevolucionDetalle entity) throws EntityException;

    DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto) throws EntityException;

    void update(DevolucionDetalle entity) throws EntityException;

    void update(AppConfig entity) throws EntityException;

    List<AppConfig> getAppConfigs() throws EntityException;

    List<AppMenu> getAppMenus() throws EntityException;

    List<CompraDetalle> findCompraDetalleByProducto(Producto producto) throws EntityException;

    List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) throws EntityException;

    void save(TipoUnidadMedida entity) throws EntityException;

    TipoUnidadMedida findTipoUnidadMedidaByID(Integer id) throws EntityException;

    void update(TipoUnidadMedida entity) throws EntityException;

    List<TipoUnidadMedida> getTiposUnidadMedida() throws EntityException;

    void save(TipoPeriodo entity) throws EntityException;

    TipoPeriodo findTipoPeriodoByID(Integer id) throws EntityException;

    void update(TipoPeriodo entity) throws EntityException;

    List<TipoPeriodo> getTiposPeriodo() throws EntityException;

    void save(ClienteApp entity) throws EntityException;

    ClienteApp findClienteByID(Integer id) throws EntityException;

    void update(ClienteApp entity) throws EntityException;

    List<ClienteApp> getClientes() throws EntityException;

}
