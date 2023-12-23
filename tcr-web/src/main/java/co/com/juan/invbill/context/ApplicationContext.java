package co.com.juan.invbill.context;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;
import co.com.juan.invbill.util.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Named
@ApplicationScoped
public class ApplicationContext extends Bundle {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);
    private final IConfigDelegate configDelegate;
    private List<AppConfig> appConfigs;

    @Inject
    public ApplicationContext(IConfigDelegate configDelegate) {
        this.configDelegate = configDelegate;
    }

    @PostConstruct
    public void init() {
        try {
            appConfigs = this.configDelegate.getAppConfigs();
        } catch (EntityException e) {
            log.error(ERROR_CONSULTA_PARAMETROS, e);
        }
    }

    public List<AppConfig> getAppConfigs() {
        return appConfigs;
    }

    public void setAppConfigs(List<AppConfig> appConfigs) {
        this.appConfigs = appConfigs;
    }

}
