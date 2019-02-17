package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.CompraCabecera;
import co.com.juan.tcr.model.CompraCabeceraId;

/**
 * @author Juan Felipe
 *
 */
public interface ICompraCabeceraLogic {

	/**
	 * List CompraCabecera objects
	 * 
	 */
	public List<CompraCabecera> getCompraCabecera();

	/**
	 * Save an existing CompraCabecera entity
	 * 
	 */
	public void saveCompraCabecera(CompraCabecera entity);

	/**
	 * Delete an existing CompraCabecera entity
	 * 
	 */
	public void deleteCompraCabecera(CompraCabecera entity);

	/**
	 * Update an existing CompraCabecera entity
	 * 
	 */
	public void updateCompraCabecera(CompraCabecera entity);

	/**
	 * Find an existing CompraCabecera entity
	 * 
	 */
	public CompraCabecera getCompraCabecera(CompraCabeceraId id);

	/**
	 * List sort existing CompraCabecera entity
	 * 
	 */
	public List<CompraCabecera> findPageCompraCabecera(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing CompraCabecera entity
	 * 
	 */
	public Long findTotalNumberCompraCabecera();

	/**
	 * Get data existing CompraCabecera entity
	 * 
	 */
	public List<CompraCabecera> getDataCompraCabecera();

	/**
	 * Find by Criteria an existing CompraCabecera entity
	 * 
	 */
	public List<CompraCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing CompraCabecera entity
	 * 
	 */
	public List<CompraCabecera> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(CompraCabecera entity);

}
