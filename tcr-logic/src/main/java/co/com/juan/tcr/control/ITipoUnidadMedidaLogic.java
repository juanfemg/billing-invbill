package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.TipoUnidadMedida;

/**
 * @author Juan Felipe
 * 
 */
public interface ITipoUnidadMedidaLogic {

	/**
	 * List TipoUnidadMedida objects
	 * 
	 */
	public List<TipoUnidadMedida> getTipoUnidadMedida();

	/**
	 * Save an existing TipoUnidadMedida entity
	 * 
	 */
	public void saveTipoUnidadMedida(TipoUnidadMedida entity);

	/**
	 * Delete an existing TipoUnidadMedida entity
	 * 
	 */
	public void deleteTipoUnidadMedida(TipoUnidadMedida entity);

	/**
	 * Update an existing TipoUnidadMedida entity
	 * 
	 */
	public void updateTipoUnidadMedida(TipoUnidadMedida entity);

	/**
	 * Find an existing TipoUnidadMedida entity
	 * 
	 */
	public TipoUnidadMedida getTipoUnidadMedida(Integer id);

	/**
	 * List sort existing TipoUnidadMedida entity
	 * 
	 */
	public List<TipoUnidadMedida> findPageTipoUnidadMedida(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing TipoUnidadMedida entity
	 * 
	 */
	public Long findTotalNumberTipoUnidadMedida();

	/**
	 * Get data existing TipoUnidadMedida entity
	 * 
	 */
	public List<TipoUnidadMedida> getDataTipoUnidadMedida();

	/**
	 * Find by Criteria an existing TipoUnidadMedida entity
	 * 
	 */
	public List<TipoUnidadMedida> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing TipoUnidadMedida entity
	 * 
	 */
	public List<TipoUnidadMedida> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(TipoUnidadMedida entity);
}
