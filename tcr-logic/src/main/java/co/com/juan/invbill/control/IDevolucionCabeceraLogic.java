package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.DevolucionCabecera;

/**
 * @author Juan Felipe
 * 
 */
public interface IDevolucionCabeceraLogic {

	/**
	 * List DevolucionCabecera objects
	 * 
	 */
	public List<DevolucionCabecera> getDevolucionCabecera();

	/**
	 * Save an existing DevolucionCabecera entity
	 * 
	 */
	public void saveDevolucionCabecera(DevolucionCabecera entity);

	/**
	 * Delete an existing DevolucionCabecera entity
	 * 
	 */
	public void deleteDevolucionCabecera(DevolucionCabecera entity);

	/**
	 * Update an existing DevolucionCabecera entity
	 * 
	 */
	public void updateDevolucionCabecera(DevolucionCabecera entity);

	/**
	 * Find an existing DevolucionCabecera entity
	 * 
	 */
	public DevolucionCabecera getDevolucionCabecera(Integer id);

	/**
	 * List sort existing DevolucionCabecera entity
	 * 
	 */
	public List<DevolucionCabecera> findPageDevolucionCabecera(String sortColumnName, boolean sortAscending,
			int startRow, int maxResults);

	/**
	 * Get rows existing DevolucionCabecera entity
	 * 
	 */
	public Long findTotalNumberDevolucionCabecera();

	/**
	 * Get data existing DevolucionCabecera entity
	 * 
	 */
	public List<DevolucionCabecera> getDataDevolucionCabecera();

	/**
	 * Find by Criteria an existing DevolucionCabecera entity
	 * 
	 */
	public List<DevolucionCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing DevolucionCabecera entity
	 * 
	 */
	public List<DevolucionCabecera> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(DevolucionCabecera entity);

}
