package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.RolApp;

/**
 * @author Juan Felipe
 * 
 */
public interface IRolAppLogic {

	/**
	 * List RolApp objects
	 * 
	 */
	public List<RolApp> getRolApp();

	/**
	 * Save an existing RolApp entity
	 * 
	 */
	public void saveRolApp(RolApp entity);

	/**
	 * Delete an existing RolApp entity
	 * 
	 */
	public void deleteRolApp(RolApp entity);

	/**
	 * Update an existing RolApp entity
	 * 
	 */
	public void updateRolApp(RolApp entity);

	/**
	 * Find an existing RolApp entity
	 * 
	 */
	public RolApp getRolApp(Integer id);

	/**
	 * List sort existing RolApp entity
	 * 
	 */
	public List<RolApp> findPageRolApp(String sortColumnName, boolean sortAscending, int startRow, int maxResults);

	/**
	 * Get rows existing RolApp entity
	 * 
	 */
	public Long findTotalNumberRolApp();

	/**
	 * Get data existing RolApp entity
	 * 
	 */
	public List<RolApp> getDataRolApp();

	/**
	 * Find by Criteria an existing RolApp entity
	 * 
	 */
	public List<RolApp> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing RolApp entity
	 * 
	 */
	public List<RolApp> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(RolApp entity);
}
