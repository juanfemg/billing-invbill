package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.context.ApplicationContext;
import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.enums.ParameterEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.util.Bundle;
import co.com.juan.invbill.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.print.PrinterJob;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Juan Felipe
 */
@Named("gestionConfiguracion")
@ViewScoped
public class GestionConfiguracionView extends Bundle implements Serializable {

    private static final String FILE_MESSAGES = "bundles.msg_ModificacionConfiguracion";
    private static final Logger log = LoggerFactory.getLogger(GestionConfiguracionView.class);
    private final IConfigDelegate configDelegate;
    private final ApplicationContext applicationContext;
    private final Properties properties = new Properties(FILE_MESSAGES);
    private AppConfig impresora;
    private AppConfig iva;
    private AppConfig topeStock;
    private List<SelectItem> impresoras;

    @Inject
    public GestionConfiguracionView(IConfigDelegate configDelegate, ApplicationContext applicationContext) {
        this.configDelegate = configDelegate;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        impresoras = new ArrayList<>();
        impresora = new AppConfig();
        iva = new AppConfig();
        topeStock = new AppConfig();
        this.initParametros();
    }

    private void initParametros() {
        impresora = this.getContextParameter(ParameterEnum.IMPRESORA_PREDETERMINADA.name());
        iva = this.getContextParameter(ParameterEnum.IVA.name());
        topeStock = this.getContextParameter(ParameterEnum.TOPE_STOCK.name());
        this.initImpresoras();
    }

    private AppConfig getContextParameter(String parameter) {
        return this.applicationContext.getAppConfigs().stream()
                .filter(appConfig -> appConfig.getIdAppConfig().equalsIgnoreCase(parameter))
                .findFirst()
                .orElse(null);
    }

    private void setContextParameter(AppConfig value) {
        List<AppConfig> newAppConfigs = this.applicationContext.getAppConfigs().stream()
                .map(appConfig -> !Objects.equals(appConfig.getIdAppConfig(), value.getIdAppConfig()) ? appConfig : value)
                .collect(Collectors.toList());
        this.applicationContext.setAppConfigs(newAppConfigs);
    }

    private void initImpresoras() {
        try {
            Arrays.stream(PrinterJob.lookupPrintServices())
                    .forEach(printService -> impresoras.add(new SelectItem(printService.getName())));
        } catch (Exception e) {
            addErrorMessage(this.properties.getParameterByKey(MSG_ERROR_CONSULTA_IMPRESORAS));
            log.error(ERROR_CONSULTA_IMPRESORAS, e);
        }
    }

    public void actionGuardarConfigImpresora() {
        this.updateConfig(impresora, MSG_CONFIGURACION_IMPRESORA_ACTUALIZADA, MSG_ERROR_ACTUALIZACION_IMPRESORA);
    }

    public void actionGuardarConfigIva() {
        this.updateConfig(iva, MSG_CONFIGURACION_IVA_ACTUALIZADA, MSG_ERROR_ACTUALIZACION_IVA);
    }

    public void actionGuardarConfigTopeStock() {
        this.updateConfig(topeStock, MSG_CONFIGURACION_TOPE_STOCK_ACTUALIZADA, MSG_ERROR_ACTUALIZACION_TOPE_STOCK);
    }

    private void updateConfig(AppConfig config, String msgConfiguracionActualizada, String msgErrorActualizacionConfiguracion) {
        try {
            this.configDelegate.update(config);
            this.setContextParameter(config);
            addInfoMessage(this.properties.getParameterByKey(msgConfiguracionActualizada));
        } catch (EntityException e) {
            addErrorMessage(this.properties.getParameterByKey(msgErrorActualizacionConfiguracion));
            log.error(ERROR_ACTUALIZACION_CONFIGURACION, config.getIdAppConfig(), e.getMessage());
        }
    }

    public List<SelectItem> getImpresoras() {
        return impresoras;
    }

    public void setImpresoras(List<SelectItem> impresoras) {
        this.impresoras = impresoras;
    }

    public AppConfig getImpresora() {
        return impresora;
    }

    public void setImpresora(AppConfig impresora) {
        this.impresora = impresora;
    }

    public AppConfig getIva() {
        return iva;
    }

    public void setIva(AppConfig iva) {
        this.iva = iva;
    }

    public AppConfig getTopeStock() {
        return topeStock;
    }

    public void setTopeStock(AppConfig topeStock) {
        this.topeStock = topeStock;
    }

}