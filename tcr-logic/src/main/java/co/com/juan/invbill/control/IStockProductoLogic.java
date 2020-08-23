package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.StockProducto;

/**
 * @author Juan Felipe
 * 
 */
public interface IStockProductoLogic {

	/**
	 * List StockProducto objects
	 * 
	 */
	public List<StockProducto> getStockProducto();

	/**
	 * Save an existing StockProducto entity
	 * 
	 */
	public void saveStockProducto(StockProducto entity);

	/**
	 * Delete an existing StockProducto entity
	 * 
	 */
	public void deleteStockProducto(StockProducto entity);

	/**
	 * Update an existing StockProducto entity
	 * 
	 */
	public void updateStockProducto(StockProducto entity);

	/**
	 * Find an existing StockProducto entity
	 * 
	 */
	public StockProducto getStockProducto(Integer id);

	/**
	 * List sort existing StockProducto entity
	 * 
	 */
	public List<StockProducto> findPageStockProducto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults);

	/**
	 * Get rows existing StockProducto entity
	 * 
	 */
	public Long findTotalNumberStockProducto();

	/**
	 * Get data existing StockProducto entity
	 * 
	 */
	public List<StockProducto> getDataStockProducto();

	/**
	 * Find by Criteria an existing StockProducto list
	 * 
	 */
	public List<StockProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Criteria an existing StockProducto entity
	 * 
	 */
	public StockProducto findObjectByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing StockProducto entity
	 * 
	 */
	public List<StockProducto> findByProperty(String propertyName, Object value);

	/**
	 * Find object by Property an existing StockProducto entity
	 * 
	 */
	public StockProducto findObjectByProperty(String propertyName, Object value);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(StockProducto entity);

	/**
	 * Find by Criteria with Projection MAX an existing object
	 * 
	 */
	public Object findMaxObjectByCriteria(String propertyName);

	/**
	 * Find by Criteria with Projection MIN an existing object
	 * 
	 */
	public Object findMinObjectByCriteria(String propertyName);

	/**
	 * Find by Criteria with Projection AVG an existing object
	 * 
	 */
	public Object findAvgObjectByCriteria(String propertyName);

}
