package co.com.juan.invbill.dataaccess.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Juan Felipe
 * 
 */
public interface Dao<T, P extends Serializable> {

	/**
	 * Persiste la nueva entidad a la base de datos.
	 * 
	 * @param newEntity
	 * @throws DaoException
	 */
	void save(T newEntity) throws DaoException;

	/**
	 * Consulta una entidad previamente guardado en la base de datos.
	 * 
	 * @param id
	 * @return entity or null.
	 */
	T findById(P id);

	/**
	 * Consulta una entidad de nuevo desde la base de datos.
	 * 
	 * @param id
	 * @return
	 */
	T load(P id);

	/**
	 * Actualiza una entidad.
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	void update(T entity) throws DaoException;

	/**
	 * Crea o actualiza una entidad.
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	void saveOrUpdate(T entity) throws DaoException;

	/**
	 * Crea o actualiza una entidad con flush.
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	void saveOrUpdate(T entity, boolean flush) throws DaoException;

	/**
	 * Merge de una entidad.
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	T merge(T entity) throws DaoException;

	/**
	 * Elimina una entidad.
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	void delete(T entity) throws DaoException;

	/**
	 * Elimina todas las entidades de una tabla.
	 * 
	 * @throws DaoException
	 */
	void deleteAll() throws DaoException;

	/**
	 * Elimina los registros de una entidad con una value de una propiedad que
	 * pertenezca a la entidad.
	 * 
	 * @param tableName
	 * @param propertyName
	 * @param value
	 * @throws DaoException
	 */
	void deleteByProperty(String tableName, String propertyName, Object value) throws DaoException;

	/**
	 * Elimina una entidad con un id dado.
	 * 
	 * @param id
	 * @throws DaoException
	 */
	void deleteById(P id) throws DaoException;

	/**
	 * Devuelte todas las entidades dentro de los parametros especificados. Si
	 * firstResult y maxResults son menores de cero, entonces se hace un findAll.
	 * 
	 * @param page
	 * @return
	 */
	List<T> findAllEntries(Paginator page);

	/**
	 * Busca un objeto por una propiedad con valor de tipo string. Este metodo es
	 * util para buscar por columnas que poseen valores unicos.
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	T findEntityByProperty(String property, String value);

	/**
	 * Busca un objeto por una propiedad con valor de tipo string. Este metodo es
	 * util para buscar por columnas que poseen valores unicos.
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	List<T> findByProperty(String propertyName, Object value);

	/**
	 * Busca un objeto por una propiedad con valor de tipo string. Este metodo es
	 * util para buscar por columnas que poseen valores unicos. Este metodo ordena
	 * por una columna de manera ascendente o descendente
	 * 
	 * @param propertyName
	 * @param value
	 * @param sortColumnName
	 * @param sortAscending
	 * @return
	 */
	List<T> findByPropertySort(String propertyName, Object value, String sortColumnName, boolean sortAscending);

	/**
	 * Busca un objeto por una propiedad con valor de tipo string. Este metodo es
	 * util para buscar por columnas que poseen multiples valores.
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	List<T> findByProperty(String propertyName, Collection<?> values);

	/**
	 * Busca un objeto por una propiedad con valor de tipo string. Este metodo es
	 * util para buscar por columnas que poseen valores unicos.
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	T findObjectByProperty(String propertyName, Object value);

	/**
	 * Busca por like todas las entidades que tienen una propertyName con un valor
	 * text. Permite buscar por un texto en una columna de tipo String.
	 * 
	 * @param propertyName : nombre de la propiedad por la cual filtrar.
	 * @param text         : valor a buscar
	 * @param page
	 * @return
	 */
	List<T> findAllByTextFilter(String propertyName, String text, Paginator page);

	/**
	 * Busca por igual todas las entidades que tienen una propertyName con el valor
	 * id. Permite buscar por un identificador en una columna de tipo Long.
	 * 
	 * @param propertyName : nombre de la propiedad por la cual filtrar.
	 * @param id           : valor a buscar
	 * @param page
	 * @return
	 */
	List<T> findAllByLongFilter(String propertyName, Long id, Paginator page);

	/**
	 * Realize una consulta basada en un ejemplo. Ver:
	 * http://docs.jboss.org/hibernate
	 * /core/3.3/reference/en/html/querycriteria.html#querycriteria-examples
	 * 
	 * @param example
	 * @param page
	 * @return
	 */
	List<T> findAllByExample(T example, Paginator page);

	/**
	 * Execute a named query.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedQuery(String queryName);

	/**
	 * Execute a named query, binding one value to a "?" parameter in the query
	 * string.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param value     the value of the parameter
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedQuery(String queryName, Object value);

	/**
	 * Execute a named query binding a number of values to "?" parameters in the
	 * query string.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param values    the values of the parameters
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedQuery(String queryName, Object... values);

	/**
	 * Execute a named query.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @return a {@link Object} containing the result of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	T findObjectByNamedQuery(String queryName);

	/**
	 * Execute a named query, binding one value to a "?" parameter in the query
	 * string.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param value     the value of the parameter
	 * @return a {@link Object} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	T findObjectByNamedQuery(String queryName, Object value);

	/**
	 * Execute a named query binding a number of values to "?" parameters in the
	 * query string.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param values    the values of the parameters
	 * @return a {@link Object} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	T findObjectByNamedQuery(String queryName, Object... values);

	/**
	 * Execute a named query. Execute a named query without aliases.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedQueryWithoutAlias(String queryName);

	/**
	 * Execute a named query, binding one value to a "?" parameter in the query
	 * string. Execute a named query without aliases.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param value     the value of the parameter
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedQueryWithoutAlias(String queryName, Object value);

	/**
	 * Execute a named query binding a number of values to "?" parameters in the
	 * query string. Execute a named query without aliases.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param values    the values of the parameters
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedQueryWithoutAlias(String queryName, Object... values);

	/**
	 * Execute a named query. Execute a named query without aliases.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @return a {@link Object} containing the result of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	T findObjectByNamedQueryWithoutAlias(String queryName);

	/**
	 * Execute a named query, binding one value to a "?" parameter in the query
	 * string. Execute a named query without aliases.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param value     the value of the parameter
	 * @return a {@link Object} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	T findObjectByNamedQueryWithoutAlias(String queryName, Object value);

	/**
	 * Execute a named query binding a number of values to "?" parameters in the
	 * query string. Execute a named query without aliases.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param values    the values of the parameters
	 * @return a {@link Object} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	T findObjectByNamedQueryWithoutAlias(String queryName, Object... values);

	/**
	 * Execute a named query.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> find(String queryString);

	/**
	 * Execute a named query, binding one value to a "?" parameter in the query
	 * string.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param value     the value of the parameter
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> find(String queryString, Object value);

	/**
	 * Execute a named query binding a number of values to "?" parameters in the
	 * query string.
	 * <p>
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName the name of a Hibernate query in a mapping file
	 * @param values    the values of the parameters
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> find(String queryString, Object... values);

	/**
	 * Execute an HQL query, binding one value to a ":" named parameter in the query
	 * string.
	 * 
	 * @param queryString a query expressed in Hibernate's query language
	 * @param paramName   the name of the parameter
	 * @param value       the value of the parameter
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedParam(String queryString, String paramName, Object value);

	/**
	 * Execute an HQL query, binding a number of values to ":" named parameters in
	 * the query string.
	 * 
	 * @param queryString a query expressed in Hibernate's query language
	 * @param paramNames  the names of the parameters
	 * @param values      the values of the parameters
	 * @return a {@link List} containing the results of the query execution
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values);

	/**
	 * Devuelve la cantidad de registros.
	 * 
	 * @return Long
	 */
	Long count();

	/**
	 * Devuelve la cantidad de registros segun la condicion ingresada.
	 * 
	 * @return int
	 */
	int countByCriteria(DetachedCriteria criteria);

	/**
	 * Devuelve el valor maximo segun la condicion ingresada.
	 * 
	 * @return
	 */
	Object maxByCriteria(String propertyName);

	/**
	 * Devuelve el valor minimo segun la condicion ingresada.
	 * 
	 * @return
	 */
	Object minByCriteria(String propertyName);

	/**
	 * Devuelve el promedio segun la condicion ingresada.
	 * 
	 * @return
	 */
	Object avgByCriteria(String propertyName);

	/**
	 * Devuelve la cantidad de registros asociados al metodo findAllByTextFilter.
	 * 
	 * @param propertyName
	 * @param text
	 * @return
	 */
	int countByTextFilter(String propertyName, String text);

	/**
	 * Devuelve la cantidad de registros asociados al metodo findAllByLongFilter.
	 * 
	 * @param propertyName
	 * @param id
	 * @return
	 */
	int countByLongFilter(String propertyName, Long id);

	/**
	 * Devuelve la cantidad de registros filtrada por un example.
	 * 
	 * @param example
	 * @return
	 */
	Long countByExample(T example);

	/**
	 * Consulta todas las entidades
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 
	 * @param sortColumnName
	 * @param sortAscending
	 * @param startRow
	 * @param maxResults
	 * @return
	 */
	List<T> findPage(String sortColumnName, boolean sortAscending, int startRow, int maxResults);

	/**
	 * 
	 * @param sortColumnName
	 * @param sortAscending
	 * @return
	 */
	List<T> findPage(String sortColumnName, boolean sortAscending);

	/**
	 * 
	 * @param whereCondition
	 * @return
	 */
	List<T> findByCriteria(String whereCondition);

	/**
	 * 
	 * @param whereCondition
	 * @return
	 */
	T findObjectByCriteria(String whereCondition);

}
