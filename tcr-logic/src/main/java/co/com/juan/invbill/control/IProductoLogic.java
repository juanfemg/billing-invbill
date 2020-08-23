package co.com.juan.invbill.control;

import java.util.Collection;
import java.util.List;

import co.com.juan.invbill.model.Producto;

/**
 * @author Juan Felipe
 * 
 */
public interface IProductoLogic {

	/**
	 * List Producto objects
	 * 
	 */
	public List<Producto> getProducto();

	/**
	 * Save an existing Producto entity
	 * 
	 */
	public void saveProducto(Producto entity);

	/**
	 * Delete an existing Producto entity
	 * 
	 */
	public void deleteProducto(Producto entity);

	/**
	 * Update an existing Producto entity
	 * 
	 */
	public void updateProducto(Producto entity);

	/**
	 * Find an existing Producto entity
	 * 
	 */
	public Producto getProducto(Integer id);

	/**
	 * List sort existing Producto entity
	 * 
	 */
	public List<Producto> findPageProducto(String sortColumnName, boolean sortAscending, int startRow, int maxResults);

	/**
	 * Get rows existing Producto entity
	 * 
	 */
	public Long findTotalNumberProducto();

	/**
	 * Get data existing Producto entity
	 * 
	 */
	public List<Producto> getDataProducto();

	/**
	 * Find by Criteria an existing Producto entity
	 * 
	 */
	public List<Producto> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

	/**
	 * Find by Property an existing Producto entity
	 * 
	 */
	public List<Producto> findByProperty(String propertyName, Object value);

	/**
	 * Find by Property an sort existing Producto entity
	 * 
	 */
	public List<Producto> findByPropertySort(String propertyName, Object value, String sortColumnName,
			boolean sortAscending);

	/**
	 * Find by Property an existing Producto entity
	 * 
	 */
	public List<Producto> findByProperty(String propertyName, Collection<?> values);

	/**
	 * Check fields
	 * 
	 */
	public void checkFields(Producto entity);
}
