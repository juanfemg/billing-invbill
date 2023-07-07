package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.AppConfig;

/**
 * @author Juan Felipe
 *
 */
public interface IAppConfigLogic {

	void saveAppConfig(AppConfig entity);

	void updateAppConfig(AppConfig entity);

	AppConfig getAppConfig(String id);

	List<AppConfig> getDataAppConfig();

	List<AppConfig> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	List<AppConfig> findByProperty(String propertyName, Object value);

}
