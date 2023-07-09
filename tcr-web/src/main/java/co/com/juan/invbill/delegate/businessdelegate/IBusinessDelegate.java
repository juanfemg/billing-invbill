package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.model.*;

import java.util.List;

/**
 * @author Juan Felipe
 */
public interface IBusinessDelegate {

    void save(LoginApp entity);

    LoginApp findLoginByID(String id);

    void update(LoginApp entity);

    void save(UsuarioApp entity);

    UsuarioApp findUsuarioByID(String id);

    void update(UsuarioApp entity);

    void save(CategoriaProducto entity);

    CategoriaProducto findCategoriaByID(Integer id);

    void update(CategoriaProducto entity);

    List<CategoriaProducto> getCategoriasProductoSortByCategoria();

    void save(Producto entity);

    Producto findProductoByID(Integer id);

    void update(Producto entity);

    List<Producto> getProductos();

    void save(StockProducto entity);

    void update(StockProducto entity);

    List<StockProducto> getStockProductos();

    StockProducto getStockProductoByProducto(Producto producto);

    Object getMaximoStockProductoByPropertyName(String propertyName);

    List<Producto> getProductosByCategoriaProductoSortByProducto(CategoriaProducto categoriaProducto);

    List<Producto> getProductosByCategoriasProducto(List<CategoriaProducto> categoriasProducto);

    void save(FacturaCabecera entity);

    FacturaCabecera findFacturaCabeceraByID(Integer id);

    void update(FacturaCabecera entity);

    List<FacturaCabecera> getFacturaCabeceras();

    List<FacturaCabecera> getFacturaCabecerasByCriteria(FacturaCabecera entity);

    Object getMaximaFacturaCabeceraByPropertyName(String propertyName);

    Object getMinimaFacturaCabeceraByPropertyName(String propertyName);

    void save(FacturaDetalle entity);

    void update(FacturaDetalle entity);

    void save(ProveedorApp entity);

    ProveedorApp findProveedorByID(Integer id);

    void update(ProveedorApp entity);

    List<ProveedorApp> getProveedores();

    void save(CompraCabecera entity);

    CompraCabecera findCompraCabeceraByID(CompraCabeceraId id);

    void update(CompraCabecera entity);

    List<CompraCabecera> getCompraCabeceras();

    List<CompraCabecera> getCompraCabeceras(CompraCabeceraId id);

    void save(CompraDetalle entity);

    void update(CompraDetalle entity);

    ReporteVentaDiaria getReporteVentaDiaria();

    ReporteDevolucionDiaria getReporteDevolucionDiaria();

    ReporteCompraDiaria getReporteCompraDiaria();

    void save(DevolucionCabecera entity);

    DevolucionCabecera findDevolucionCabeceraByID(Integer id);

    void update(DevolucionCabecera entity);

    List<DevolucionCabecera> getDevolucionCabeceras();

    List<DevolucionCabecera> getDevolucionCabecerasByCriteria(DevolucionCabecera entity);

    void save(DevolucionDetalle entity);

    DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto);

    void update(DevolucionDetalle entity);

    void update(AppConfig entity);

    List<AppConfig> getAppConfigs();

    List<ReporteVentaMensual> getReporteVentaMensual();

    List<ReporteDevolucionMensual> getReporteDevolucionMensual();

    List<ReporteCompraMensual> getReporteCompraMensual();

    List<AppMenu> getAppMenus();

    List<CompraDetalle> findCompraDetalleByProducto(Producto producto);

    List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura);

    void save(TipoUnidadMedida entity);

    TipoUnidadMedida findTipoUnidadMedidaByID(Integer id);

    void update(TipoUnidadMedida entity);

    List<TipoUnidadMedida> getTiposUnidadMedida();

    void save(TipoPeriodo entity);

    TipoPeriodo findTipoPeriodoByID(Integer id);

    void update(TipoPeriodo entity);

    List<TipoPeriodo> getTiposPeriodo();

    void save(ClienteApp entity);

    ClienteApp findClienteByID(Integer id);

    void update(ClienteApp entity);

    List<ClienteApp> getClientes();

}
