package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.ClienteApp;

/**
 * @author Juan Felipe
 *
 */
public interface IClienteAppLogic {

	/**
	 * List ClienteApp objects
	 * 
	 */
	public List<ClienteApp> getClienteApp();

	/**
	 * Save an existing ClienteApp entity
	 * 
	 */
	public void saveClienteApp(ClienteApp entity);

	/**
	 * Delete an existing ClienteApp entity
	 * 
	 */
	public void deleteClienteApp(ClienteApp entity);

	/**
	 * Update an existing ClienteApp entity
	 * 
	 */
	public void updateClienteApp(ClienteApp entity);

	/**
	 * Find an existing ClienteApp entity
	 * 
	 */
	public ClienteApp getClienteApp(Integer id);

	/**
	 * List sort existing ClienteApp entity
	 * 
	 */
	public List<ClienteApp> findPageClienteApp(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing ClienteApp entity
	 * 
	 */
	public Long findTotalNumberClienteApp();

	/**
	 * Get data existing ClienteApp entity
	 * 
	 */
	public List<ClienteApp> getDataClienteApp();

	/**
	 * Find by Criteria an existing ClienteApp entity
	 * 
	 */
	public List<ClienteApp> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing ClienteApp entity
	 * 
	 */
	public List<ClienteApp> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(ClienteApp entity);

	/**
	 * Check secondary fields
	 * 
	 */
	public void checkSecondaryFields(ClienteApp entity);
}
