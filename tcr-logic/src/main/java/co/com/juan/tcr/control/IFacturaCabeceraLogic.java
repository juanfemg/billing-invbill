package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.FacturaCabecera;

/**
 * @author Juan Felipe
 * 
 */
public interface IFacturaCabeceraLogic {

	/**
	 * List FacturaCabecera objects
	 * 
	 */
	public List<FacturaCabecera> getFacturaCabecera();

	/**
	 * Save an existing FacturaCabecera entity
	 * 
	 */
	public void saveFacturaCabecera(FacturaCabecera entity);

	/**
	 * Delete an existing FacturaCabecera entity
	 * 
	 */
	public void deleteFacturaCabecera(FacturaCabecera entity);

	/**
	 * Update an existing FacturaCabecera entity
	 * 
	 */
	public void updateFacturaCabecera(FacturaCabecera entity);

	/**
	 * Find an existing FacturaCabecera entity
	 * 
	 */
	public FacturaCabecera getFacturaCabecera(Integer id);

	/**
	 * List sort existing FacturaCabecera entity
	 * 
	 */
	public List<FacturaCabecera> findPageFacturaCabecera(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing FacturaCabecera entity
	 * 
	 */
	public Long findTotalNumberFacturaCabecera();

	/**
	 * Get data existing FacturaCabecera entity
	 * 
	 */
	public List<FacturaCabecera> getDataFacturaCabecera();

	/**
	 * Find by Criteria an existing FacturaCabecera entity
	 * 
	 */
	public List<FacturaCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing FacturaCabecera entity
	 * 
	 */
	public List<FacturaCabecera> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(FacturaCabecera entity);
}