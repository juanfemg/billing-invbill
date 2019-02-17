package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.CompraDetalle;
import co.com.juan.tcr.model.CompraDetalleId;

/**
 * @author Juan Felipe
 *
 */
public interface ICompraDetalleLogic {

	/**
	 * List CompraDetalle objects
	 * 
	 */
	public List<CompraDetalle> getCompraDetalle();

	/**
	 * Save an existing CompraDetalle entity
	 * 
	 */
	public void saveCompraDetalle(CompraDetalle entity);

	/**
	 * Delete an existing CompraDetalle entity
	 * 
	 */
	public void deleteCompraDetalle(CompraDetalle entity);

	/**
	 * Update an existing CompraDetalle entity
	 * 
	 */
	public void updateCompraDetalle(CompraDetalle entity);

	/**
	 * Find an existing CompraDetalle entity
	 * 
	 */
	public CompraDetalle getCompraDetalle(CompraDetalleId id);

	/**
	 * List sort existing CompraDetalle entity
	 * 
	 */
	public List<CompraDetalle> findPageCompraDetalle(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing CompraDetalle entity
	 * 
	 */
	public Long findTotalNumberCompraDetalle();

	/**
	 * Get data existing CompraDetalle entity
	 * 
	 */
	public List<CompraDetalle> getDataCompraDetalle();

	/**
	 * Find by Criteria an existing CompraDetalle entity
	 * 
	 */
	public List<CompraDetalle> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing CompraDetalle entity
	 * 
	 */
	public List<CompraDetalle> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(CompraDetalle entity);

}
