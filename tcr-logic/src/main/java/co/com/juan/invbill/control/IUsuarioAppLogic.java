
package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.UsuarioApp;

/**
 * @author Juan Felipe
 *
 */
public interface IUsuarioAppLogic {

	/**
	 * List UsuarioApp objects
	 * 
	 */
	public List<UsuarioApp> getUsuarioApp();

	/**
	 * Save an existing UsuarioApp entity
	 * 
	 */
	public void saveUsuarioApp(UsuarioApp entity);

	/**
	 * Delete an existing UsuarioApp entity
	 * 
	 */
	public void deleteUsuarioApp(UsuarioApp entity);

	/**
	 * Update an existing UsuarioApp entity
	 * 
	 */
	public void updateUsuarioApp(UsuarioApp entity);

	/**
	 * Find an existing UsuarioApp entity
	 * 
	 */
	public UsuarioApp getUsuarioApp(String id);

	/**
	 * List sort existing UsuarioApp entity
	 * 
	 */
	public List<UsuarioApp> findPageUsuarioApp(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing UsuarioApp entity
	 * 
	 */
	public Long findTotalNumberUsuarioApp();

	/**
	 * Get data existing UsuarioApp entity
	 * 
	 */
	public List<UsuarioApp> getDataUsuarioApp();

	/**
	 * Find by Criteria an existing UsuarioApp entity
	 * 
	 */
	public List<UsuarioApp> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing UsuarioApp entity
	 * 
	 */
	public List<UsuarioApp> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(UsuarioApp entity);
}
