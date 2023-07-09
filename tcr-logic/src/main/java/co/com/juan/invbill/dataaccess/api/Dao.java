package co.com.juan.invbill.dataaccess.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Juan Felipe
 */
public interface Dao<T, P extends Serializable> {

    void save(T newEntity) throws DaoException;

    void update(T entity) throws DaoException;

    T findById(P id) throws DaoException;

    List<T> findByProperty(String propertyName, Object value) throws DaoException;

    List<T> findByPropertySort(String propertyName, Object value, String sortColumnName, boolean sortAscending) throws DaoException;

    List<T> findByProperty(String propertyName, Collection<?> values) throws DaoException;

    T findObjectByProperty(String propertyName, Object value) throws DaoException;

    List<T> findByNamedQuery(String queryName, Object value) throws DaoException;

    List<T> findByNamedQuery(String queryName, Object... values) throws DaoException;

    T findObjectByNamedQuery(String queryName, Object value) throws DaoException;

    T findObjectByNamedQuery(String queryName, Object... values) throws DaoException;

    List<T> findByNamedQueryWithoutAlias(String queryName, Object value) throws DaoException;

    List<T> findByNamedQueryWithoutAlias(String queryName, Object... values) throws DaoException;

    List<T> find(String queryString) throws DaoException;

    List<T> find(String queryString, Object value) throws DaoException;

    List<T> find(String queryString, Object... values) throws DaoException;

    Object maxByCriteria(String propertyName) throws DaoException;

    Object minByCriteria(String propertyName) throws DaoException;

    Object avgByCriteria(String propertyName) throws DaoException;

    List<T> findAll() throws DaoException;

    List<T> findPage(String sortColumnName, boolean sortAscending) throws DaoException;

    List<T> findByCriteria(String whereCondition) throws DaoException;

}
