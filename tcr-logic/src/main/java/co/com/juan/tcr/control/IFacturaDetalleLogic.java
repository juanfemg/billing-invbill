package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.FacturaDetalle;
import co.com.juan.tcr.model.FacturaDetalleId;

/**
 * @author Juan Felipe
 * 
 */
public interface IFacturaDetalleLogic {

	/**
	 * List FacturaDetalle objects
	 * 
	 */
	public List<FacturaDetalle> getFacturaDetalle();

	/**
	 * Save an existing FacturaDetalle entity
	 * 
	 */
	public void saveFacturaDetalle(FacturaDetalle entity);

	/**
	 * Delete an existing FacturaDetalle entity
	 * 
	 */
	public void deleteFacturaDetalle(FacturaDetalle entity);

	/**
	 * Update an existing FacturaDetalle entity
	 * 
	 */
	public void updateFacturaDetalle(FacturaDetalle entity);

	/**
	 * Find an existing FacturaDetalle entity
	 * 
	 */
	public FacturaDetalle getFacturaDetalle(FacturaDetalleId id);

	/**
	 * List sort existing FacturaDetalle entity
	 * 
	 */
	public List<FacturaDetalle> findPageFacturaDetalle(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing FacturaDetalle entity
	 * 
	 */
	public Long findTotalNumberFacturaDetalle();

	/**
	 * Get data existing FacturaDetalle entity
	 * 
	 */
	public List<FacturaDetalle> getDataFacturaDetalle();

	/**
	 * Find by Criteria an existing FacturaDetalle entity
	 * 
	 */
	public List<FacturaDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing FacturaDetalle entity
	 * 
	 */
	public List<FacturaDetalle> findByProperty(String propertyName, Object value);

	/**
	 * List FacturaDetalle objects
	 * 
	 */
	public List<FacturaDetalle> getFacturaDetalleDevolucionByIdFactura(Integer idFactura);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(FacturaDetalle entity);

}
