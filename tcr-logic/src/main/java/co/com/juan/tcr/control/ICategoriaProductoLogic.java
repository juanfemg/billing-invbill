package co.com.juan.tcr.control;

import java.util.List;

import co.com.juan.tcr.model.CategoriaProducto;

/**
 * @author Juan Felipe
 * 
 */
public interface ICategoriaProductoLogic {

	/**
	 * List CategoriaProducto objects
	 * 
	 */
	public List<CategoriaProducto> getCategoriaProducto();

	/**
	 * Save an existing CategoriaProducto entity
	 * 
	 */
	public void saveCategoriaProducto(CategoriaProducto entity);

	/**
	 * Delete an existing CategoriaProducto entity
	 * 
	 */
	public void deleteCategoriaProducto(CategoriaProducto entity);

	/**
	 * Update an existing CategoriaProducto entity
	 * 
	 */
	public void updateCategoriaProducto(CategoriaProducto entity);

	/**
	 * Find an existing CategoriaProducto entity
	 * 
	 */
	public CategoriaProducto getCategoriaProducto(Integer id);

	/**
	 * List sort existing CategoriaProducto entity
	 * 
	 */
	public List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * List sort existing CategoriaProducto entity
	 * 
	 */
	public List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending);

	/**
	 * Get rows existing CategoriaProducto entity
	 * 
	 */
	public Long findTotalNumberCategoriaProducto();

	/**
	 * Get data existing CategoriaProducto entity
	 * 
	 */
	public List<CategoriaProducto> getDataCategoriaProducto();

	/**
	 * Find by Criteria an existing CategoriaProducto entity
	 * 
	 */
	public List<CategoriaProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing CategoriaProducto entity
	 * 
	 */
	public List<CategoriaProducto> findByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(CategoriaProducto entity);

}
