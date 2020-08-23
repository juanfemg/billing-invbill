package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.ProveedorApp;

/**
 * @author Juan Felipe
 *
 */
public interface IProveedorAppLogic {

	/**
	 * List ProveedorApp objects
	 * 
	 */
	public List<ProveedorApp> getProveedorApp();

	/**
	 * Save an existing ProveedorApp entity
	 * 
	 */
	public void saveProveedorApp(ProveedorApp entity);

	/**
	 * Delete an existing ProveedorApp entity
	 * 
	 */
	public void deleteProveedorApp(ProveedorApp entity);

	/**
	 * Update an existing ProveedorApp entity
	 * 
	 */
	public void updateProveedorApp(ProveedorApp entity);

	/**
	 * Find an existing ProveedorApp entity
	 * 
	 */
	public ProveedorApp getProveedorApp(Integer id);

	/**
	 * List sort existing ProveedorApp entity
	 * 
	 */
	public List<ProveedorApp> findPageProveedorApp(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing ProveedorApp entity
	 * 
	 */
	public Long findTotalNumberProveedorApp();

	/**
	 * Get data existing ProveedorApp entity
	 * 
	 */
	public List<ProveedorApp> getDataProveedorApp();

	/**
	 * Find by Criteria an existing ProveedorApp entity
	 * 
	 */
	public List<ProveedorApp> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing ProveedorApp entity
	 * 
	 */
	public List<ProveedorApp> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(ProveedorApp entity);

	/**
	 * Check secondary fields
	 * 
	 */
	public void checkSecondaryFields(ProveedorApp entity);
}
