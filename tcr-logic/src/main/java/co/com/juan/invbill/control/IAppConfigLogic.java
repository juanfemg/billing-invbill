package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;

/**
 * @author Juan Felipe
 */
public interface IAppConfigLogic {

    void updateAppConfig(AppConfig entity) throws EntityException;

    List<AppConfig> getDataAppConfig() throws EntityException;

}
