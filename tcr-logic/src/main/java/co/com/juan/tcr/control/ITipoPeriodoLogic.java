package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.TipoPeriodo;

/**
 * @author Juan Felipe
 * 
 */
public interface ITipoPeriodoLogic {

	/**
	 * List TipoPeriodo objects
	 * 
	 */
	public List<TipoPeriodo> getTipoPeriodo();

	/**
	 * Save an existing TipoPeriodo entity
	 * 
	 */
	public void saveTipoPeriodo(TipoPeriodo entity);

	/**
	 * Delete an existing TipoPeriodo entity
	 * 
	 */
	public void deleteTipoPeriodo(TipoPeriodo entity);

	/**
	 * Update an existing TipoPeriodo entity
	 * 
	 */
	public void updateTipoPeriodo(TipoPeriodo entity);

	/**
	 * Find an existing TipoPeriodo entity
	 * 
	 */
	public TipoPeriodo getTipoPeriodo(Integer id);

	/**
	 * List sort existing TipoPeriodo entity
	 * 
	 */
	public List<TipoPeriodo> findPageTipoPeriodo(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing TipoPeriodo entity
	 * 
	 */
	public Long findTotalNumberTipoPeriodo();

	/**
	 * Get data existing TipoPeriodo entity
	 * 
	 */
	public List<TipoPeriodo> getDataTipoPeriodo();

	/**
	 * Find by Criteria an existing TipoPeriodo entity
	 * 
	 */
	public List<TipoPeriodo> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing TipoPeriodo entity
	 * 
	 */
	public List<TipoPeriodo> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(TipoPeriodo entity);
}
