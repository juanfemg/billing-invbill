package co.com.juan.tcr.delegate.businessdelegate;

import java.util.List;

import co.com.juan.tcr.dto.ReporteCompraDiaria;
import co.com.juan.tcr.dto.ReporteCompraMensual;
import co.com.juan.tcr.dto.ReporteDevolucionDiaria;
import co.com.juan.tcr.dto.ReporteDevolucionMensual;
import co.com.juan.tcr.dto.ReporteVentaDiaria;
import co.com.juan.tcr.dto.ReporteVentaMensual;
import co.com.juan.tcr.model.AppConfig;
import co.com.juan.tcr.model.AppMenu;
import co.com.juan.tcr.model.CategoriaProducto;
import co.com.juan.tcr.model.CompraCabecera;
import co.com.juan.tcr.model.CompraCabeceraId;
import co.com.juan.tcr.model.CompraDetalle;
import co.com.juan.tcr.model.CompraDetalleId;
import co.com.juan.tcr.model.DevolucionCabecera;
import co.com.juan.tcr.model.DevolucionDetalle;
import co.com.juan.tcr.model.FacturaCabecera;
import co.com.juan.tcr.model.FacturaDetalle;
import co.com.juan.tcr.model.FacturaDetalleId;
import co.com.juan.tcr.model.LoginApp;
import co.com.juan.tcr.model.Producto;
import co.com.juan.tcr.model.ProveedorApp;
import co.com.juan.tcr.model.StockProducto;
import co.com.juan.tcr.model.TipoPeriodo;
import co.com.juan.tcr.model.TipoUnidadMedida;
import co.com.juan.tcr.model.UsuarioApp;

/**
 * Use a Business Delegate to reduce coupling between presentation-tier clients
 * and business services. The Business Delegate hides the underlying
 * implementation details of the business service, such as lookup and access
 * details of the EJB architecture.
 * 
 * The Business Delegate acts as a client-side business abstraction; it provides
 * an abstraction for, and thus hides, the implementation of the business
 * services. Using a Business Delegate reduces the coupling between
 * presentation-tier clients and the system's business services. Depending on
 * the implementation strategy, the Business Delegate may shield clients from
 * possible volatility in the implementation of the business service API.
 * Potentially, this reduces the number of changes that must be made to the
 * presentation-tier client code when the business service API or its underlying
 * implementation changes.
 * 
 * However, interface methods in the Business Delegate may still require
 * modification if the underlying business service API changes. Admittedly,
 * though, it is more likely that changes will be made to the business service
 * rather than to the Business Delegate.
 * 
 * Often, developers are skeptical when a design goal such as abstracting the
 * business layer causes additional upfront work in return for future gains.
 * However, using this pattern or its strategies results in only a small amount
 * of additional upfront work and provides considerable benefits. The main
 * benefit is hiding the details of the underlying service. For example, the
 * client can become transparent to naming and lookup services. The Business
 * Delegate also handles the exceptions from the business services, such as
 * java.rmi.Remote exceptions, Java Messages Service (JMS) exceptions and so on.
 * The Business Delegate may intercept such service level exceptions and
 * generate application level exceptions instead. Application level exceptions
 * are easier to handle by the clients, and may be user friendly. The Business
 * Delegate may also transparently perform any retry or recovery operations
 * necessary in the event of a service failure without exposing the client to
 * the problem until it is determined that the problem is not resolvable. These
 * gains present a compelling reason to use the pattern.
 * 
 * Another benefit is that the delegate may cache results and references to
 * remote business services. Caching can significantly improve performance,
 * because it limits unnecessary and potentially costly round trips over the
 * network.
 * 
 * A Business Delegate uses a component called the Lookup Service. The Lookup
 * Service is responsible for hiding the underlying implementation details of
 * the business service lookup code. The Lookup Service may be written as part
 * of the Delegate, but we recommend that it be implemented as a separate
 * component, as outlined in the Service Locator pattern (See "Service Locator"
 * on page 368.)
 * 
 * When the Business Delegate is used with a Session Facade, typically there is
 * a one-to-one relationship between the two. This one-to-one relationship
 * exists because logic that might have been encapsulated in a Business Delegate
 * relating to its interaction with multiple business services (creating a
 * one-to-many relationship) will often be factored back into a Session Facade.
 * 
 * Finally, it should be noted that this pattern could be used to reduce
 * coupling between other tiers, not simply the presentation and the business
 * tiers.
 * 
 * @author Juan Felipe
 * 
 */
public interface IBusinessDelegate {

	public void save(LoginApp entity);

	public LoginApp findLoginByID(String id);

	public void update(LoginApp entity);

	public void save(UsuarioApp entity);

	public UsuarioApp findUsuarioByID(String id);

	public void update(UsuarioApp entity);

	public void save(CategoriaProducto entity);

	public CategoriaProducto findCategoriaByID(Integer id);

	public void update(CategoriaProducto entity);

	public List<CategoriaProducto> getCategoriasProducto();

	public List<CategoriaProducto> getCategoriasProductoSortByCategoria();

	public void save(Producto entity);

	public Producto findProductoByID(Integer id);

	public void update(Producto entity);

	public List<Producto> getProductos();

	public void save(StockProducto entity);

	public StockProducto findStockProductoByID(Integer id);

	public void update(StockProducto entity);

	public List<StockProducto> getStockProductos();

	public StockProducto getStockProductoByProducto(Producto producto);

	public Object getMaximoStockProductoByPropertyName(String propertyName);

	public Object getMinimoStockProductoByPropertyName(String propertyName);

	public Object getPromedioStockProductoByPropertyName(String propertyName);

	public List<Producto> getProductosByCategoriaProducto(CategoriaProducto categoriaProducto);

	public List<Producto> getProductosByCategoriaProductoSortByProducto(CategoriaProducto categoriaProducto);

	public List<Producto> getProductosByCategoriasProducto(List<CategoriaProducto> categoriasProducto);

	public void save(FacturaCabecera entity);

	public FacturaCabecera findFacturaCabeceraByID(Integer id);

	public void update(FacturaCabecera entity);

	public List<FacturaCabecera> getFacturaCabeceras();

	public Object getMaximaFacturaCabeceraByPropertyName(String propertyName);

	public Object getMinimaFacturaCabeceraByPropertyName(String propertyName);

	public Object getPromedioFacturaCabeceraByPropertyName(String propertyName);

	public void save(FacturaDetalle entity);

	public FacturaDetalle findFacturaDetalleByID(FacturaDetalleId id);

	public void update(FacturaDetalle entity);

	public List<FacturaDetalle> getFacturaDetalles();

	public List<FacturaDetalle> getFacturaDetallesByFactura(FacturaCabecera facturaCabecera);

	public void save(ProveedorApp entity);

	public ProveedorApp findProveedorByID(Integer id);

	public void update(ProveedorApp entity);

	public List<ProveedorApp> getProveedores();

	public void save(CompraCabecera entity);

	public CompraCabecera findCompraCabeceraByID(CompraCabeceraId id);

	public void update(CompraCabecera entity);

	public List<CompraCabecera> getCompraCabeceras();

	public List<CompraCabecera> getCompraCabeceras(CompraCabeceraId id);

	public void save(CompraDetalle entity);

	public CompraDetalle findCompraDetalleByID(CompraDetalleId id);

	public void update(CompraDetalle entity);

	public List<CompraDetalle> getCompraDetalles();

	public ReporteVentaDiaria getReporteVentaDiaria();

	public ReporteDevolucionDiaria getReporteDevolucionDiaria();

	public ReporteCompraDiaria getReporteCompraDiaria();

	public void save(DevolucionCabecera entity);

	public DevolucionCabecera findDevolucionCabeceraByID(Integer id);

	public void update(DevolucionCabecera entity);

	public List<DevolucionCabecera> getDevolucionCabeceras();

	public void save(DevolucionDetalle entity);

	public DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto);

	public void update(DevolucionDetalle entity);

	public List<DevolucionDetalle> getDevolucionDetalles();

	public void save(AppConfig entity);

	public AppConfig findAppConfigByID(String id);

	public void update(AppConfig entity);

	public List<AppConfig> getAppConfigs();

	public List<ReporteVentaMensual> getReporteVentaMensual();

	public List<ReporteDevolucionMensual> getReporteDevolucionMensual();

	public List<ReporteCompraMensual> getReporteCompraMensual();

	public List<AppMenu> getAppMenus();

	public List<CompraDetalle> findCompraDetalleByProducto(Producto producto);

	public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura);

	public void save(TipoUnidadMedida entity);

	public TipoUnidadMedida findTipoUnidadMedidaByID(Integer id);

	public void update(TipoUnidadMedida entity);

	public List<TipoUnidadMedida> getTiposUnidadMedida();

	public void save(TipoPeriodo entity);

	public TipoPeriodo findTipoPeriodoByID(Integer id);

	public void update(TipoPeriodo entity);

	public List<TipoPeriodo> getTiposPeriodo();

}
