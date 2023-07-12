package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.control.*;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class BusinessDelegate implements IBusinessDelegate {

    private final ILoginAppLogic loginAppLogic;
    private final IUsuarioAppLogic usuarioAppLogic;
    private final IFacturaCabeceraLogic facturaCabeceraLogic;
    private final IFacturaDetalleLogic facturaDetalleLogic;
    private final IProveedorAppLogic proveedorAppLogic;
    private final ICompraCabeceraLogic compraCabeceraLogic;
    private final ICompraDetalleLogic compraDetalleLogic;
    private final IDevolucionCabeceraLogic devolucionCabeceraLogic;
    private final IDevolucionDetalleLogic devolucionDetalleLogic;
    private final IAppConfigLogic appConfigLogic;
    private final IAppMenuLogic appMenuLogic;
    private final ITipoUnidadMedidaLogic tipoUnidadMedidaLogic;
    private final ITipoPeriodoLogic tipoPeriodoLogic;
    private final IClienteAppLogic clienteAppLogic;

    @Inject
    public BusinessDelegate(ILoginAppLogic loginAppLogic, IUsuarioAppLogic usuarioAppLogic, IFacturaCabeceraLogic facturaCabeceraLogic, IFacturaDetalleLogic facturaDetalleLogic, IProveedorAppLogic proveedorAppLogic, ICompraCabeceraLogic compraCabeceraLogic, ICompraDetalleLogic compraDetalleLogic, IDevolucionCabeceraLogic devolucionCabeceraLogic, IDevolucionDetalleLogic devolucionDetalleLogic, IAppConfigLogic appConfigLogic, IAppMenuLogic appMenuLogic, ITipoUnidadMedidaLogic tipoUnidadMedidaLogic, ITipoPeriodoLogic tipoPeriodoLogic, IClienteAppLogic clienteAppLogic) {
        this.loginAppLogic = loginAppLogic;
        this.usuarioAppLogic = usuarioAppLogic;
        this.facturaCabeceraLogic = facturaCabeceraLogic;
        this.facturaDetalleLogic = facturaDetalleLogic;
        this.proveedorAppLogic = proveedorAppLogic;
        this.compraCabeceraLogic = compraCabeceraLogic;
        this.compraDetalleLogic = compraDetalleLogic;
        this.devolucionCabeceraLogic = devolucionCabeceraLogic;
        this.devolucionDetalleLogic = devolucionDetalleLogic;
        this.appConfigLogic = appConfigLogic;
        this.appMenuLogic = appMenuLogic;
        this.tipoUnidadMedidaLogic = tipoUnidadMedidaLogic;
        this.tipoPeriodoLogic = tipoPeriodoLogic;
        this.clienteAppLogic = clienteAppLogic;
    }

    @Override
    public void save(LoginApp entity) throws EntityException {
        this.loginAppLogic.saveLoginApp(entity);
    }

    @Override
    public LoginApp findLoginByID(String id) throws EntityException {
        return this.loginAppLogic.getLoginApp(id);
    }

    @Override
    public void update(LoginApp entity) throws EntityException {
        this.loginAppLogic.updateLoginApp(entity);
    }

    @Override
    public void save(UsuarioApp entity) throws EntityException {
        this.usuarioAppLogic.saveUsuarioApp(entity);
    }

    @Override
    public UsuarioApp findUsuarioByID(String id) throws EntityException {
        return this.usuarioAppLogic.getUsuarioApp(id);
    }

    @Override
    public void update(UsuarioApp entity) throws EntityException {
        this.usuarioAppLogic.updateUsuarioApp(entity);
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
    public void update(FacturaCabecera entity) throws EntityException {
        this.facturaCabeceraLogic.updateFacturaCabecera(entity);
    }

    @Override
    public List<FacturaCabecera> getFacturaCabeceras() throws EntityException {
        return this.facturaCabeceraLogic.getDataFacturaCabecera();
    }

    // TODO
    @Override
    public List<FacturaCabecera> getFacturaCabecerasByCriteria(FacturaCabecera entity) throws EntityException {
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
    public void update(FacturaDetalle entity) throws EntityException {
        this.facturaDetalleLogic.updateFacturaDetalle(entity);
    }

    @Override
    public void save(ProveedorApp entity) throws EntityException {
        this.proveedorAppLogic.saveProveedorApp(entity);
    }

    @Override
    public ProveedorApp findProveedorByID(Integer id) throws EntityException {
        return this.proveedorAppLogic.getProveedorApp(id);
    }

    @Override
    public void update(ProveedorApp entity) throws EntityException {
        this.proveedorAppLogic.updateProveedorApp(entity);
    }

    @Override
    public List<ProveedorApp> getProveedores() throws EntityException {
        return this.proveedorAppLogic.getProveedorApp();
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
    public List<CompraCabecera> getCompraCabeceras() throws EntityException {
        return this.compraCabeceraLogic.getDataCompraCabecera();
    }

    // TODO
    @Override
    public List<CompraCabecera> getCompraCabeceras(CompraCabeceraId id) throws EntityException {
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
    public void save(CompraDetalle entity) throws EntityException {
        this.compraDetalleLogic.saveCompraDetalle(entity);
    }

    @Override
    public void update(CompraDetalle entity) throws EntityException {
        this.compraDetalleLogic.updateCompraDetalle(entity);
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

    // TODO
    @Override
    public List<DevolucionCabecera> getDevolucionCabecerasByCriteria(DevolucionCabecera entity) throws EntityException {
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
    public void save(DevolucionDetalle entity) throws EntityException {
        this.devolucionDetalleLogic.saveDevolucionDetalle(entity);
    }

    // TODO
    @Override
    public DevolucionDetalle findDevolucionDetalleByID(Integer idFactura, Integer idProducto) throws EntityException {
        DevolucionDetalleId devolucionDetalleId;

        devolucionDetalleId = new DevolucionDetalleId(idFactura, idProducto);

        return devolucionDetalleLogic.getDevolucionDetalle(devolucionDetalleId);
    }

    @Override
    public void update(DevolucionDetalle entity) throws EntityException {
        this.devolucionDetalleLogic.updateDevolucionDetalle(entity);
    }

    @Override
    public void update(AppConfig entity) throws EntityException {
        this.appConfigLogic.updateAppConfig(entity);
    }

    @Override
    public List<AppConfig> getAppConfigs() throws EntityException {
        return this.appConfigLogic.getDataAppConfig();
    }

    @Override
    public List<AppMenu> getAppMenus() throws EntityException {
        return this.appMenuLogic.getDataAppMenu();
    }

    @Override
    public List<CompraDetalle> findCompraDetalleByProducto(Producto producto) throws EntityException {
        return this.compraDetalleLogic.findByProperty("producto", producto);
    }

    @Override
    public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura) throws EntityException {
        return this.facturaDetalleLogic.getFacturaDetalleDevolucionByIdFactura(idFactura);
    }

    @Override
    public void save(TipoUnidadMedida entity) throws EntityException {
        this.tipoUnidadMedidaLogic.saveTipoUnidadMedida(entity);
    }

    @Override
    public TipoUnidadMedida findTipoUnidadMedidaByID(Integer id) throws EntityException {
        return this.tipoUnidadMedidaLogic.getTipoUnidadMedida(id);
    }

    @Override
    public void update(TipoUnidadMedida entity) throws EntityException {
        this.tipoUnidadMedidaLogic.updateTipoUnidadMedida(entity);
    }

    @Override
    public List<TipoUnidadMedida> getTiposUnidadMedida() throws EntityException {
        return this.tipoUnidadMedidaLogic.getTipoUnidadMedida();
    }

    @Override
    public void save(TipoPeriodo entity) throws EntityException {
        this.tipoPeriodoLogic.saveTipoPeriodo(entity);
    }

    @Override
    public TipoPeriodo findTipoPeriodoByID(Integer id) throws EntityException {
        return this.tipoPeriodoLogic.getTipoPeriodo(id);
    }

    @Override
    public void update(TipoPeriodo entity) throws EntityException {
        this.tipoPeriodoLogic.updateTipoPeriodo(entity);
    }

    @Override
    public List<TipoPeriodo> getTiposPeriodo() throws EntityException {
        return this.tipoPeriodoLogic.getTipoPeriodo();
    }

    @Override
    public void save(ClienteApp entity) throws EntityException {
        this.clienteAppLogic.saveClienteApp(entity);
    }

    @Override
    public ClienteApp findClienteByID(Integer id) throws EntityException {
        return this.clienteAppLogic.getClienteApp(id);
    }

    @Override
    public void update(ClienteApp entity) throws EntityException {
        this.clienteAppLogic.updateClienteApp(entity);
    }

    @Override
    public List<ClienteApp> getClientes() throws EntityException {
        return this.clienteAppLogic.getClienteApp();
    }

}
