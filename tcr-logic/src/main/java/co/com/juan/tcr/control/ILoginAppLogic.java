package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.LoginApp;

/**
 * @author Juan Felipe
 * 
 */
public interface ILoginAppLogic {

	/**
	 * List LoginApp objects
	 * 
	 */
	public List<LoginApp> getLoginApp();

	/**
	 * Save an existing LoginApp entity
	 * 
	 */
	public void saveLoginApp(LoginApp entity);

	/**
	 * Delete an existing LoginApp entity
	 * 
	 */
	public void deleteLoginApp(LoginApp entity);

	/**
	 * Update an existing LoginApp entity
	 * 
	 */
	public void updateLoginApp(LoginApp entity);

	/**
	 * Find an existing LoginApp entity
	 * 
	 */
	public LoginApp getLoginApp(String id);

	/**
	 * List sort existing LoginApp entity
	 * 
	 */
	public List<LoginApp> findPageLoginApp(String sortColumnName, boolean sortAscending, int startRow, int maxResults);

	/**
	 * Get rows existing LoginApp entity
	 * 
	 */
	public Long findTotalNumberLoginApp();

	/**
	 * Get data existing LoginApp entity
	 * 
	 */
	public List<LoginApp> getDataLoginApp();

	/**
	 * Find by Criteria an existing LoginApp entity
	 * 
	 */
	public List<LoginApp> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing LoginApp entity
	 * 
	 */
	public List<LoginApp> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(LoginApp entity);
}
