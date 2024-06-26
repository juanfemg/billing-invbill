package co.com.juan.invbill.delegate.businessdelegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.com.juan.invbill.control.IAppConfigLogic;
import co.com.juan.invbill.control.IAppMenuLogic;
import co.com.juan.invbill.control.ICategoriaProductoLogic;
import co.com.juan.invbill.control.IClienteAppLogic;
import co.com.juan.invbill.control.ICompraCabeceraLogic;
import co.com.juan.invbill.control.ICompraDetalleLogic;
import co.com.juan.invbill.control.IDevolucionCabeceraLogic;
import co.com.juan.invbill.control.IDevolucionDetalleLogic;
import co.com.juan.invbill.control.IFacturaCabeceraLogic;
import co.com.juan.invbill.control.IFacturaDetalleLogic;
import co.com.juan.invbill.control.ILoginAppLogic;
import co.com.juan.invbill.control.IProductoLogic;
import co.com.juan.invbill.control.IProveedorAppLogic;
import co.com.juan.invbill.control.IStockProductoLogic;
import co.com.juan.invbill.control.ITipoPeriodoLogic;
import co.com.juan.invbill.control.ITipoUnidadMedidaLogic;
import co.com.juan.invbill.control.IUsuarioAppLogic;
import co.com.juan.invbill.dto.ReporteCompraDiaria;
import co.com.juan.invbill.dto.ReporteCompraMensual;
import co.com.juan.invbill.dto.ReporteDevolucionDiaria;
import co.com.juan.invbill.dto.ReporteDevolucionMensual;
import co.com.juan.invbill.dto.ReporteVentaDiaria;
import co.com.juan.invbill.dto.ReporteVentaMensual;
import co.com.juan.invbill.dto.control.IReporteCompraDiariaLogic;
import co.com.juan.invbill.dto.control.IReporteCompraMensualLogic;
import co.com.juan.invbill.dto.control.IReporteDevolucionDiariaLogic;
import co.com.juan.invbill.dto.control.IReporteDevolucionMensualLogic;
import co.com.juan.invbill.dto.control.IReporteVentaDiariaLogic;
import co.com.juan.invbill.dto.control.IReporteVentaMensualLogic;
import co.com.juan.invbill.enums.EstadoEnum;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.ClienteApp;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import co.com.juan.invbill.model.CompraDetalle;
import co.com.juan.invbill.model.CompraDetalleId;
import co.com.juan.invbill.model.DevolucionCabecera;
import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;
import co.com.juan.invbill.model.FacturaCabecera;
import co.com.juan.invbill.model.FacturaDetalle;
import co.com.juan.invbill.model.FacturaDetalleId;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.ProveedorApp;
import co.com.juan.invbill.model.RolApp;
import co.com.juan.invbill.model.StockProducto;
import co.com.juan.invbill.model.TipoPeriodo;
import co.com.juan.invbill.model.TipoUnidadMedida;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Encrypt;

/**
 * @author Juan Felipe
 * 
 */
@Scope("singleton")
@Service("BusinessDelegate")
public class BusinessDelegate implements IBusinessDelegate {

	@Autowired
	private ILoginAppLogic loginAppLogic;

	@Autowired
	private IUsuarioAppLogic usuarioAppLogic;

	@Autowired
	private ICategoriaProductoLogic categoriaProductoLogic;

	@Autowired
	private IProductoLogic productoLogic;

	@Autowired
	private IStockProductoLogic stockProductoLogic;

	@Autowired
	private IFacturaCabeceraLogic facturaCabeceraLogic;

	@Autowired
	private IFacturaDetalleLogic facturaDetalleLogic;

	@Autowired
	private IProveedorAppLogic proveedorAppLogic;

	@Autowired
	private ICompraCabeceraLogic compraCabeceraLogic;

	@Autowired
	private ICompraDetalleLogic compraDetalleLogic;

	@Autowired
	private IReporteVentaDiariaLogic reporteVentaDiariaLogic;

	@Autowired
	private IReporteDevolucionDiariaLogic reporteDevolucionDiariaLogic;

	@Autowired
	private IReporteCompraDiariaLogic reporteCompraDiariaLogic;

	@Autowired
	private IDevolucionCabeceraLogic devolucionCabeceraLogic;

	@Autowired
	private IDevolucionDetalleLogic devolucionDetalleLogic;

	@Autowired
	private IAppConfigLogic appConfigLogic;

	@Autowired
	private IReporteVentaMensualLogic reporteVentaMensualLogic;

	@Autowired
	private IReporteDevolucionMensualLogic reporteDevolucionMensualLogic;

	@Autowired
	private IReporteCompraMensualLogic reporteCompraMensualLogic;

	@Autowired
	private IAppMenuLogic appMenuLogic;

	@Autowired
	private ITipoUnidadMedidaLogic tipoUnidadMedidaLogic;

	@Autowired
	private ITipoPeriodoLogic tipoPeriodoLogic;

	@Autowired
	private IClienteAppLogic clienteAppLogic;

	@Override
	public void save(LoginApp entity) {
		loginAppLogic.saveLoginApp(entity);
	}

	@Override
	public LoginApp findLoginByID(String id) {
		return loginAppLogic.getLoginApp(id);
	}

	@Override
	public void update(LoginApp entity) {
		loginAppLogic.updateLoginApp(entity);
	}

	@Override
	public void save(UsuarioApp entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		if (entity.getPassword() != null) {
			entity.setPassword(new Encrypt().encrypt(entity.getPassword()));
		}

		if (entity.getRolApp() == null) {
			RolApp rolApp = new RolApp("ADMINISTRADOR", EstadoEnum.A);
			rolApp.setIdRolApp(1);
			entity.setRolApp(rolApp);
		}

		usuarioAppLogic.saveUsuarioApp(entity);
	}

	@Override
	public UsuarioApp findUsuarioByID(String id) {
		return usuarioAppLogic.getUsuarioApp(id);
	}

	@Override
	public void update(UsuarioApp entity) {
		usuarioAppLogic.updateUsuarioApp(entity);
	}

	@Override
	public void save(CategoriaProducto entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		categoriaProductoLogic.saveCategoriaProducto(entity);
	}

	@Override
	public CategoriaProducto findCategoriaByID(Integer id) {
		return categoriaProductoLogic.getCategoriaProducto(id);
	}

	@Override
	public void update(CategoriaProducto entity) {
		categoriaProductoLogic.updateCategoriaProducto(entity);
	}

	@Override
	public List<CategoriaProducto> getCategoriasProducto() {
		return categoriaProductoLogic.getCategoriaProducto();
	}

	@Override
	public List<CategoriaProducto> getCategoriasProductoSortByCategoria() {
		return categoriaProductoLogic.findPageCategoriaProducto("categoria", true);
	}

	@Override
	public void save(Producto entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		productoLogic.saveProducto(entity);

		StockProducto stockProducto = new StockProducto();
		stockProducto.setProducto(entity);

		stockProductoLogic.saveStockProducto(stockProducto);
	}

	@Override
	public Producto findProductoByID(Integer id) {
		return productoLogic.getProducto(id);
	}

	@Override
	public void update(Producto entity) {
		productoLogic.updateProducto(entity);
	}

	@Override
	public List<Producto> getProductos() {
		return productoLogic.getProducto();
	}

	@Override
	public void save(StockProducto entity) {
		stockProductoLogic.saveStockProducto(entity);
	}

	@Override
	public StockProducto findStockProductoByID(Integer id) {
		return stockProductoLogic.getStockProducto(id);
	}

	@Override
	public void update(StockProducto entity) {
		stockProductoLogic.updateStockProducto(entity);
	}

	@Override
	public List<StockProducto> getStockProductos() {
		return stockProductoLogic.getStockProducto();
	}

	@Override
	public StockProducto getStockProductoByProducto(Producto producto) {
		return stockProductoLogic.findObjectByProperty("producto", producto);
	}

	@Override
	public Object getMaximoStockProductoByPropertyName(String propertyName) {
		return stockProductoLogic.findMaxObjectByCriteria(propertyName);
	}

	@Override
	public Object getMinimoStockProductoByPropertyName(String propertyName) {
		return stockProductoLogic.findMinObjectByCriteria(propertyName);
	}

	@Override
	public Object getPromedioStockProductoByPropertyName(String propertyName) {
		return stockProductoLogic.findAvgObjectByCriteria(propertyName);
	}

	@Override
	public List<Producto> getProductosByCategoriaProducto(CategoriaProducto categoriaProducto) {
		return productoLogic.findByProperty("categoriaProducto", categoriaProducto);
	}

	@Override
	public List<Producto> getProductosByCategoriaProductoSortByProducto(CategoriaProducto categoriaProducto) {
		return productoLogic.findByPropertySort("categoriaProducto", categoriaProducto, "producto", true);
	}

	@Override
	public List<Producto> getProductosByCategoriasProducto(List<CategoriaProducto> categoriasProducto) {
		return productoLogic.findByProperty("categoriaProducto", categoriasProducto);
	}

	@Override
	public void save(FacturaCabecera entity) {
		facturaCabeceraLogic.saveFacturaCabecera(entity);
	}

	@Override
	public FacturaCabecera findFacturaCabeceraByID(Integer id) {
		return facturaCabeceraLogic.getFacturaCabecera(id);
	}

	@Override
	public void update(FacturaCabecera entity) {
		facturaCabeceraLogic.updateFacturaCabecera(entity);
	}

	@Override
	public List<FacturaCabecera> getFacturaCabeceras() {
		return facturaCabeceraLogic.getDataFacturaCabecera();
	}

	@Override
	public List<FacturaCabecera> getFacturaCabecerasByCriteria(FacturaCabecera entity) {
		List<Object> variables = new ArrayList<>();
		List<Object> variablesBetweenDates = new ArrayList<>();

		if (entity.getIdFactura() != null) {
			variables.add("idFactura");
			variables.add(true);
			variables.add(entity.getIdFactura());
			variables.add("=");
		}

		if (entity.getFechaCreacion() != null) {
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTime(entity.getFechaCreacion());
			calendarStart.add(Calendar.HOUR_OF_DAY, 0);
			calendarStart.add(Calendar.MINUTE, 0);
			calendarStart.add(Calendar.SECOND, 0);

			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTime(entity.getFechaCreacion());
			calendarEnd.add(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.add(Calendar.MINUTE, 59);
			calendarEnd.add(Calendar.SECOND, 59);

			variablesBetweenDates.add("fechaCreacion");
			variablesBetweenDates.add(calendarStart.getTime());
			variablesBetweenDates.add(calendarEnd.getTime());
		}

		return facturaCabeceraLogic.findByCriteria(variables.toArray(), null, variablesBetweenDates.toArray());
	}

	@Override
	public Object getMaximaFacturaCabeceraByPropertyName(String propertyName) {
		return facturaCabeceraLogic.findMaxObjectByCriteria(propertyName);
	}

	@Override
	public Object getMinimaFacturaCabeceraByPropertyName(String propertyName) {
		return facturaCabeceraLogic.findMinObjectByCriteria(propertyName);
	}

	@Override
	public Object getPromedioFacturaCabeceraByPropertyName(String propertyName) {
		return facturaCabeceraLogic.findAvgObjectByCriteria(propertyName);
	}

	@Override
	public void save(FacturaDetalle entity) {
		facturaDetalleLogic.saveFacturaDetalle(entity);
	}

	@Override
	public FacturaDetalle findFacturaDetalleByID(FacturaDetalleId id) {
		return facturaDetalleLogic.getFacturaDetalle(id);
	}

	@Override
	public void update(FacturaDetalle entity) {
		facturaDetalleLogic.updateFacturaDetalle(entity);
	}

	@Override
	public List<FacturaDetalle> getFacturaDetalles() {
		return facturaDetalleLogic.getFacturaDetalle();
	}

	@Override
	public List<FacturaDetalle> getFacturaDetallesByFactura(FacturaCabecera facturaCabecera) {
		return facturaDetalleLogic.findByProperty("facturaCabecera", facturaCabecera);
	}

	@Override
	public void save(ProveedorApp entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		proveedorAppLogic.saveProveedorApp(entity);
	}

	@Override
	public ProveedorApp findProveedorByID(Integer id) {
		return proveedorAppLogic.getProveedorApp(id);
	}

	@Override
	public void update(ProveedorApp entity) {
		proveedorAppLogic.updateProveedorApp(entity);
	}

	@Override
	public List<ProveedorApp> getProveedores() {
		return proveedorAppLogic.getProveedorApp();
	}

	@Override
	public void save(CompraCabecera entity) {
		compraCabeceraLogic.saveCompraCabecera(entity);
	}

	@Override
	public CompraCabecera findCompraCabeceraByID(CompraCabeceraId id) {
		return compraCabeceraLogic.getCompraCabecera(id);
	}

	@Override
	public void update(CompraCabecera entity) {
		compraCabeceraLogic.updateCompraCabecera(entity);
	}

	@Override
	public List<CompraCabecera> getCompraCabeceras() {
		return compraCabeceraLogic.getDataCompraCabecera();
	}

	@Override
	public List<CompraCabecera> getCompraCabeceras(CompraCabeceraId id) {
		List<Object> variables = new ArrayList<>();

		if (!id.getIdFacturaCompra().isEmpty()) {
			variables.add("id.idFacturaCompra");
			variables.add(true);
			variables.add(id.getIdFacturaCompra());
			variables.add("=");
		}

		if (id.getIdProveedorApp() != null) {
			variables.add("id.idProveedorApp");
			variables.add(false);
			variables.add(id.getIdProveedorApp());
			variables.add("=");
		}

		return compraCabeceraLogic.findByCriteria(variables.toArray(), null, null);
	}

	@Override
	public void save(CompraDetalle entity) {
		compraDetalleLogic.saveCompraDetalle(entity);
	}

	@Override
	public CompraDetalle findCompraDetalleByID(CompraDetalleId id) {
		return compraDetalleLogic.getCompraDetalle(id);
	}

	@Override
	public void update(CompraDetalle entity) {
		compraDetalleLogic.updateCompraDetalle(entity);
	}

	@Override
	public List<CompraDetalle> getCompraDetalles() {
		return compraDetalleLogic.getCompraDetalle();
	}

	@Override
	public ReporteVentaDiaria getReporteVentaDiaria() {
		Date date = new Date();

		return reporteVentaDiariaLogic.getReporteVentaDiaria(date);
	}

	@Override
	public ReporteDevolucionDiaria getReporteDevolucionDiaria() {
		Date date = new Date();

		return reporteDevolucionDiariaLogic.getReporteDevolucionDiaria(date);
	}

	@Override
	public ReporteCompraDiaria getReporteCompraDiaria() {
		Date date = new Date();

		return reporteCompraDiariaLogic.getReporteCompraDiaria(date);
	}

	@Override
	public void save(DevolucionCabecera entity) {
		devolucionCabeceraLogic.saveDevolucionCabecera(entity);
	}

	@Override
	public DevolucionCabecera findDevolucionCabeceraByID(Integer id) {
		return devolucionCabeceraLogic.getDevolucionCabecera(id);
	}

	@Override
	public void update(DevolucionCabecera entity) {
		devolucionCabeceraLogic.updateDevolucionCabecera(entity);
	}

	@Override
	public List<DevolucionCabecera> getDevolucionCabeceras() {
		return devolucionCabeceraLogic.getDataDevolucionCabecera();
	}

	@Override
	public List<DevolucionCabecera> getDevolucionCabecerasByCriteria(DevolucionCabecera entity) {
		List<Object> variables = new ArrayList<>();
		List<Object> variablesBetweenDates = new ArrayList<>();

		if (entity.getIdFactura() != null) {
			variables.add("idFactura");
			variables.add(true);
			variables.add(entity.getIdFactura());
			variables.add("=");
		}

		if (entity.getFechaCreacion() != null) {
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTime(entity.getFechaCreacion());
			calendarStart.add(Calendar.HOUR_OF_DAY, 0);
			calendarStart.add(Calendar.MINUTE, 0);
			calendarStart.add(Calendar.SECOND, 0);

			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTime(entity.getFechaCreacion());
			calendarEnd.add(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.add(Calendar.MINUTE, 59);
			calendarEnd.add(Calendar.SECOND, 59);

			variablesBetweenDates.add("fechaCreacion");
			variablesBetweenDates.add(calendarStart.getTime());
			variablesBetweenDates.add(calendarEnd.getTime());
		}

		return devolucionCabeceraLogic.findByCriteria(variables.toArray(), null, variablesBetweenDates.toArray());
	}

	@Override
	public void save(DevolucionDetalle entity) {
		devolucionDetalleLogic.saveDevolucionDetalle(entity);
	}

	@Override
	public DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto) {
		DevolucionDetalleId devolucionDetalleId = null;

		devolucionDetalleId = new DevolucionDetalleId(idFactura, idProducto);

		return devolucionDetalleLogic.getDevolucionDetalle(devolucionDetalleId);
	}

	@Override
	public void update(DevolucionDetalle entity) {
		devolucionDetalleLogic.updateDevolucionDetalle(entity);
	}

	@Override
	public List<DevolucionDetalle> getDevolucionDetalles() {
		return devolucionDetalleLogic.getDevolucionDetalle();
	}

	@Override
	public void save(AppConfig entity) {
		appConfigLogic.saveAppConfig(entity);
	}

	@Override
	public AppConfig findAppConfigByID(String id) {
		return appConfigLogic.getAppConfig(id);
	}

	@Override
	public void update(AppConfig entity) {
		appConfigLogic.updateAppConfig(entity);
	}

	@Override
	public List<AppConfig> getAppConfigs() {
		return appConfigLogic.getDataAppConfig();
	}

	@Override
	public List<ReporteVentaMensual> getReporteVentaMensual() {
		Date date = new Date();

		return reporteVentaMensualLogic.getReporteVentaMensual(date);
	}

	@Override
	public List<ReporteDevolucionMensual> getReporteDevolucionMensual() {
		Date date = new Date();

		return reporteDevolucionMensualLogic.getReporteDevolucionMensual(date);
	}

	@Override
	public List<ReporteCompraMensual> getReporteCompraMensual() {
		Date date = new Date();

		return reporteCompraMensualLogic.getReporteCompraMensual(date);
	}

	@Override
	public List<AppMenu> getAppMenus() {
		return appMenuLogic.getDataAppMenu();
	}

	@Override
	public List<CompraDetalle> findCompraDetalleByProducto(Producto producto) {
		return compraDetalleLogic.findByProperty("producto", producto);
	}

	@Override
	public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) {
		return facturaDetalleLogic.getFacturaDetalleDevolucionByIdFactura(idFactura);
	}

	@Override
	public void save(TipoUnidadMedida entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		tipoUnidadMedidaLogic.saveTipoUnidadMedida(entity);
	}

	@Override
	public TipoUnidadMedida findTipoUnidadMedidaByID(Integer id) {
		return tipoUnidadMedidaLogic.getTipoUnidadMedida(id);
	}

	@Override
	public void update(TipoUnidadMedida entity) {
		tipoUnidadMedidaLogic.updateTipoUnidadMedida(entity);
	}

	@Override
	public List<TipoUnidadMedida> getTiposUnidadMedida() {
		return tipoUnidadMedidaLogic.getTipoUnidadMedida();
	}

	@Override
	public void save(TipoPeriodo entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		tipoPeriodoLogic.saveTipoPeriodo(entity);
	}

	@Override
	public TipoPeriodo findTipoPeriodoByID(Integer id) {
		return tipoPeriodoLogic.getTipoPeriodo(id);
	}

	@Override
	public void update(TipoPeriodo entity) {
		tipoPeriodoLogic.updateTipoPeriodo(entity);
	}

	@Override
	public List<TipoPeriodo> getTiposPeriodo() {
		return tipoPeriodoLogic.getTipoPeriodo();
	}

	@Override
	public void save(ClienteApp entity) {
		if (entity.getEstado() == null) {
			entity.setEstado(EstadoEnum.A);
		}

		clienteAppLogic.saveClienteApp(entity);
	}

	@Override
	public ClienteApp findClienteByID(Integer id) {
		return clienteAppLogic.getClienteApp(id);
	}

	@Override
	public void update(ClienteApp entity) {
		clienteAppLogic.updateClienteApp(entity);
	}

	@Override
	public List<ClienteApp> getClientes() {
		return clienteAppLogic.getClienteApp();
	}

	@Override
	public List<Producto> getReporteProductosByCriteria(Producto entity) {
		List<Object> variables = new ArrayList<>();

		if (entity.getIdProducto() != null) {
			variables.add("idPrducto");
			variables.add(true);
			variables.add(entity.getIdProducto());
			variables.add("in");
		}

		return productoLogic.findByCriteria(variables.toArray(), null, null);
	}

}
