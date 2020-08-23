package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.AppConfig;

/**
 * @author Juan Felipe
 *
 */
public interface IAppConfigLogic {

	/**
	 * List AppConfig objects
	 * 
	 */
	public List<AppConfig> getAppConfig();

	/**
	 * Save an existing AppConfig entity
	 * 
	 */
	public void saveAppConfig(AppConfig entity);

	/**
	 * Delete an existing AppConfig entity
	 * 
	 */
	public void deleteAppConfig(AppConfig entity);

	/**
	 * Update an existing AppConfig entity
	 * 
	 */
	public void updateAppConfig(AppConfig entity);

	/**
	 * Find an existing AppConfig entity
	 * 
	 */
	public AppConfig getAppConfig(String id);

	/**
	 * List sort existing AppConfig entity
	 * 
	 */
	public List<AppConfig> findPageAppConfig(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing AppConfig entity
	 * 
	 */
	public Long findTotalNumberAppConfig();

	/**
	 * Get data existing AppConfig entity
	 * 
	 */
	public List<AppConfig> getDataAppConfig();

	/**
	 * Find by Criteria an existing AppConfig entity
	 * 
	 */
	public List<AppConfig> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing AppConfig entity
	 * 
	 */
	public List<AppConfig> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(AppConfig entity);
}
