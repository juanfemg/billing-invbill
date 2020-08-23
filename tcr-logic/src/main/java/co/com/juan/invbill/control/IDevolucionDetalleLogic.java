package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.DevolucionDetalle;
import co.com.juan.invbill.model.DevolucionDetalleId;

/**
 * @author Juan Felipe
 * 
 */
public interface IDevolucionDetalleLogic {

	/**
	 * List DevolucionDetalle objects
	 * 
	 */
	public List<DevolucionDetalle> getDevolucionDetalle();

	/**
	 * Save an existing DevolucionDetalle entity
	 * 
	 */
	public void saveDevolucionDetalle(DevolucionDetalle entity);

	/**
	 * Delete an existing DevolucionDetalle entity
	 * 
	 */
	public void deleteDevolucionDetalle(DevolucionDetalle entity);

	/**
	 * Update an existing DevolucionDetalle entity
	 * 
	 */
	public void updateDevolucionDetalle(DevolucionDetalle entity);

	/**
	 * Find an existing DevolucionDetalle entity
	 * 
	 */
	public DevolucionDetalle getDevolucionDetalle(DevolucionDetalleId id);

	/**
	 * List sort existing DevolucionDetalle entity
	 * 
	 */
	public List<DevolucionDetalle> findPageDevolucionDetalle(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing DevolucionDetalle entity
	 * 
	 */
	public Long findTotalNumberDevolucionDetalle();

	/**
	 * Get data existing DevolucionDetalle entity
	 * 
	 */
	public List<DevolucionDetalle> getDataDevolucionDetalle();

	/**
	 * Find by Criteria an existing DevolucionDetalle entity
	 * 
	 */
	public List<DevolucionDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing DevolucionDetalle entity
	 * 
	 */
	public List<DevolucionDetalle> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(DevolucionDetalle entity);
}
