package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.AppMenu;

/**
 * @author Juan Felipe
 *
 */
public interface IAppMenuLogic {

	/**
	 * List AppMenu objects
	 * 
	 */
	public List<AppMenu> getAppMenu();

	/**
	 * Save an existing AppMenu entity
	 * 
	 */
	public void saveAppMenu(AppMenu entity);

	/**
	 * Delete an existing AppMenu entity
	 * 
	 */
	public void deleteAppMenu(AppMenu entity);

	/**
	 * Update an existing AppMenu entity
	 * 
	 */
	public void updateAppMenu(AppMenu entity);

	/**
	 * Find an existing AppMenu entity
	 * 
	 */
	public AppMenu getAppMenu(String id);

	/**
	 * List sort existing AppMenu entity
	 * 
	 */
	public List<AppMenu> findPageAppMenu(String sortColumnName, boolean sortAscending, int startRow, int maxResults);

	/**
	 * Get rows existing AppMenu entity
	 * 
	 */
	public Long findTotalNumberAppMenu();

	/**
	 * Get data existing AppMenu entity
	 * 
	 */
	public List<AppMenu> getDataAppMenu();

	/**
	 * Find by Criteria an existing AppMenu entity
	 * 
	 */
	public List<AppMenu> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing AppMenu entity
	 * 
	 */
	public List<AppMenu> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(AppMenu entity);
}
