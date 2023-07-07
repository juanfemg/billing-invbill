package co.com.juan.invbill.dataaccess.api;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author Juan Felipe
 * 
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HibernateDaoImpl<T, P extends Serializable> implements Dao<T, P> {

	private static final String DETACHED_CRITERIA_MUST_NOT_BE_NULL = "DetachedCriteria must not be null";
	private Class<T> entityClass;
	private Logger log = null;

	@Resource
	private SessionFactory sessionFactory;

	private boolean cacheQueries = false;
	private String queryCacheRegion = "cache";
	private int fetchSize = 0;
	private int maxResults = 0;

	public HibernateDaoImpl() {
		super();
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		log = LoggerFactory.getLogger(entityClass);
	}

	public Connection getConnection() {
		SessionImpl sessionImpl = (SessionImpl) getSession();
		return sessionImpl.connection();
	}

	@Override
	public void save(T newEntity) throws DaoException {
		getSession().save(newEntity);
	}

	@Override
	public T findById(P id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public T load(P id) {
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public void update(T entity) throws DaoException {
		getSession().update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) throws DaoException {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(T newEntity, boolean flush) throws DaoException {
		saveOrUpdate(newEntity);

		if (flush) {
			getSession().flush();
		}
	}

	@Override
	public T merge(T entity) throws DaoException {
		return (T) getSession().merge(entity);
	}

	@Override
	public void delete(T entity) throws DaoException {
		getSession().delete(entity);
	}

	@Override
	public void deleteById(P id) throws DaoException {
		T toRemove = findById(id);
		getSession().delete(toRemove);
	}

	@Override
	public void deleteByProperty(String tableName, String propertyName, Object value) throws DaoException {
		String queryString = String.format("delete from %s as model where model.%s= ?", tableName, propertyName);
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, value);
		queryObject.executeUpdate();
	}

	@Override
	public Long count() {
		Query query = createQuery("select count(*) from " + entityClass.getName());

		Long result = (Long) query.uniqueResult();

		return (result != null) ? result : 0;
	}

	@Override
	public Long countByExample(T example) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, null);

		criteria.setProjection(Projections.rowCount());

		if (example != null) {
			criteria.add(Example.create(example).ignoreCase());
		}

		Long count = (Long) criteria.uniqueResult();

		return (count != null) ? count : 0;
	}

	@Override
	public int countByCriteria(DetachedCriteria criteria) {
		if (criteria == null) {
			throw new IllegalArgumentException(DETACHED_CRITERIA_MUST_NOT_BE_NULL);
		}

		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		prepareCriteria(executableCriteria, null);

		executableCriteria.setProjection(Projections.rowCount());

		Object object = executableCriteria.uniqueResult();

		if (object instanceof Long) {
			Long count = (Long) object;

			return count.intValue();
		} else {
			Integer count = (Integer) object;

			return (count != null) ? count.intValue() : 0;
		}
	}

	@Override
	public Object maxByCriteria(String propertyName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, null);

		criteria.setProjection(Projections.max(propertyName));

		return criteria.uniqueResult();
	}

	@Override
	public Object minByCriteria(String propertyName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, null);

		criteria.setProjection(Projections.min(propertyName));

		return criteria.uniqueResult();
	}

	@Override
	public Object avgByCriteria(String propertyName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, null);

		criteria.setProjection(Projections.avg(propertyName));

		return criteria.uniqueResult();
	}

	@Override
	public List<T> findAllEntries(Paginator page) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, page);

		return criteria.list();
	}

	@Override
	public T findEntityByProperty(String property, String value) {
		if ((value == null) || (value.length() == 0)) {
			return null;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		criteria.add(Restrictions.eq(property, value));

		return (T) getByCriteria(criteria);
	}

	@Override
	public List<T> findAllByTextFilter(String propertyName, String text, Paginator page) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, page);

		if ((propertyName != null) && (text != null)) {
			criteria.add(Restrictions.ilike(propertyName, text, MatchMode.START));
		}

		return criteria.list();
	}

	@Override
	public List<T> findAllByLongFilter(String propertyName, Long id, Paginator page) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, page);

		if ((propertyName != null) && (id != null)) {
			criteria.add(Restrictions.eq(propertyName, id));
		}

		return criteria.list();
	}

	@Override
	public int countByTextFilter(String propertyName, String text) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, null);

		if ((propertyName != null) && (text != null)) {
			criteria.add(Restrictions.ilike(propertyName, text, MatchMode.START));
		}

		criteria.setProjection(Projections.rowCount());

		Long count = (Long) criteria.uniqueResult();

		return (count != null) ? count.intValue() : 0;
	}

	@Override
	public int countByLongFilter(String propertyName, Long id) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, null);

		if ((propertyName != null) && (id != null)) {
			criteria.add(Restrictions.eq(propertyName, id));
		}

		criteria.setProjection(Projections.rowCount());

		Long count = (Long) criteria.uniqueResult();

		return (count != null) ? count.intValue() : 0;
	}

	@Override
	public List<T> findAllByExample(T example, Paginator page) {
		Criteria criteria = getSession().createCriteria(entityClass);

		prepareCriteria(criteria, page);

		if (example != null) {
			criteria.add(Example.create(example).ignoreCase());
		}

		return criteria.list();
	}

	@Override
	public List<T> findByNamedQuery(String queryName) {
		return findByNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object value) {
		Object[] values = { value };
		return findByNamedQuery(queryName, values);
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object... values) {
		Query queryObject = createNamedQuery(queryName);

		queryObject.setResultTransformer(Transformers.aliasToBean(entityClass));

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}

		return queryObject.list();
	}

	@Override
	public T findObjectByNamedQuery(String queryName) {
		return findObjectByNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public T findObjectByNamedQuery(String queryName, Object value) {
		Object[] values = { value };
		return findObjectByNamedQuery(queryName, values);
	}

	@Override
	public T findObjectByNamedQuery(String queryName, Object... values) {
		Query queryObject = createNamedQuery(queryName);

		queryObject.setResultTransformer(Transformers.aliasToBean(entityClass));

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}

		return (T) queryObject.uniqueResult();
	}

	@Override
	public List<T> findByNamedQueryWithoutAlias(String queryName) {
		return findByNamedQueryWithoutAlias(queryName, (Object[]) null);
	}

	@Override
	public List<T> findByNamedQueryWithoutAlias(String queryName, Object value) {
		Object[] values = { value };
		return findByNamedQueryWithoutAlias(queryName, values);
	}

	@Override
	public List<T> findByNamedQueryWithoutAlias(String queryName, Object... values) {
		Query queryObject = createNamedQuery(queryName);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}

		return queryObject.list();
	}

	@Override
	public T findObjectByNamedQueryWithoutAlias(String queryName) {
		return findObjectByNamedQueryWithoutAlias(queryName, (Object[]) null);
	}

	@Override
	public T findObjectByNamedQueryWithoutAlias(String queryName, Object value) {
		Object[] values = { value };
		return findObjectByNamedQueryWithoutAlias(queryName, values);
	}

	@Override
	public T findObjectByNamedQueryWithoutAlias(String queryName, Object... values) {
		Query queryObject = createNamedQuery(queryName);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}

		return (T) queryObject.uniqueResult();
	}

	@Override
	public List<T> find(String queryString) {
		return find(queryString, (Object[]) null);
	}

	@Override
	public List<T> find(String queryString, Object value) {
		Object[] values = { value };
		return find(queryString, values);
	}

	@Override
	public List<T> find(String queryString, Object... values) {
		Query queryObject = createQuery(queryString);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}

		return queryObject.list();
	}

	@Override
	public List<T> findByNamedParam(String queryString, String paramName, Object value) {
		return findByNamedParam(queryString, new String[] { paramName }, new Object[] { value });
	}

	@Override
	public List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values) {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}

		Query queryObject = createQuery(queryString);

		for (int i = 0; i < values.length; i++) {
			applyNamedParameterToQuery(queryObject, paramNames[i], values[i]);
		}

		return queryObject.list();
	}

	protected List<T> findByCriteria(DetachedCriteria criteria) {
		return findByCriteria(criteria, null);
	}

	protected List<T> findByCriteria(DetachedCriteria criteria, Paginator page) {
		if (criteria == null) {
			throw new IllegalArgumentException(DETACHED_CRITERIA_MUST_NOT_BE_NULL);
		}

		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		prepareCriteria(executableCriteria, page);

		return executableCriteria.list();
	}

	protected Object getByCriteria(DetachedCriteria criteria) {
		return getByCriteria(criteria, null);
	}

	protected Object getByCriteria(DetachedCriteria criteria, Paginator page) {
		if (criteria == null) {
			throw new IllegalArgumentException(DETACHED_CRITERIA_MUST_NOT_BE_NULL);
		}

		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		prepareCriteria(executableCriteria, page);

		return executableCriteria.uniqueResult();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Query createQuery(String queryString) {
		Query query = getSession().createQuery(queryString);
		prepareQuery(query);

		return query;
	}

	protected Query createSQLQuery(String queryString) {
		Query query = getSession().createSQLQuery(queryString);
		prepareQuery(query);

		return query;
	}

	protected Query createNamedQuery(String queryString) {
		Query query = getSession().getNamedQuery(queryString);
		prepareQuery(query);

		return query;
	}

	protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value) {
		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, new Object[] { value });
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	protected void prepareQuery(Query queryObject) {
		if (isCacheQueries()) {
			queryObject.setCacheable(true);

			if (getQueryCacheRegion() != null) {
				queryObject.setCacheRegion(getQueryCacheRegion());
			}
		}

		if (getFetchSize() > 0) {
			queryObject.setFetchSize(getFetchSize());
		}

		if (getMaxResults() > 0) {
			queryObject.setMaxResults(getMaxResults());
		}
	}

	protected void prepareCriteria(Criteria criteria, Paginator page) {
		if (isCacheQueries()) {
			criteria.setCacheable(true);

			if (getQueryCacheRegion() != null) {
				criteria.setCacheRegion(getQueryCacheRegion());
			}
		}

		if (getFetchSize() > 0) {
			criteria.setFetchSize(getFetchSize());
		}

		if (getMaxResults() > 0) {
			criteria.setMaxResults(getMaxResults());
		}

		if (page != null) {
			preparePaginator(criteria, page);
		}
	}

	protected void preparePaginator(Criteria criteria, Paginator page) {
		if (page.getFirstResult() > 0) {
			criteria.setFirstResult(page.getFirstResult());
		}

		if (page.getMaxResults() > 0) {
			criteria.setMaxResults(page.getMaxResults());
			criteria.setFetchSize(page.getMaxResults());
		}

		if (page.getSort() != null) {
			StringTokenizer token = new StringTokenizer(page.getSort(), ",");

			while (token.hasMoreTokens()) {
				String column = token.nextToken();

				if ((column == null) || column.contains("null")) {
					continue;
				}

				int dot = column.indexOf('.');

				if (dot > 0) {
					Criteria subCriteria = criteria.createCriteria(column.substring(1, dot));
					addOrder(subCriteria, column.charAt(0) + column.substring(dot + 1));
				} else {
					addOrder(criteria, column);
				}
			}
		}
	}

	private void addOrder(Criteria criteria, String column) {
		criteria.addOrder(column.startsWith("+") ? Order.asc(column.substring(1)) : Order.desc(column.substring(1)));
	}

	public boolean isCacheQueries() {
		return cacheQueries;
	}

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	public String getQueryCacheRegion() {
		return queryCacheRegion;
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void deleteAll() throws DaoException {
		String queryString = "delete from " + entityClass.getName();
		Query queryObject = getSession().createQuery(queryString);
		queryObject.executeUpdate();
	}

	public Order prepareOrderForCriteria(String sort) {
		char symbol = sort.charAt(0);
		int dot = sort.indexOf('.');
		String originalSort = symbol + sort.substring(dot + 1, sort.length());
		sort = sort.substring(dot + 1, sort.length());

		return originalSort.startsWith("+") ? Order.asc(sort) : Order.desc(sort);
	}

	@Override
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(entityClass);

		return criteria.list();
	}

	@Override
	public List<T> findPage(String sortColumnName, boolean sortAscending, int startRow, int maxResults) {
		log.debug("findPage {}", entityClass.getName());

		String queryString = "";
		if ((sortColumnName != null) && (sortColumnName.length() > 0)) {
			try {
				if (sortAscending) {
					queryString = String.format("from %s model order by model.%s asc", entityClass.getName(),
							sortColumnName);
				} else {
					queryString = String.format("from %s model order by model.%s desc", entityClass.getName(),
							sortColumnName);
				}

				return sessionFactory.getCurrentSession().createQuery(queryString).setFirstResult(startRow)
						.setMaxResults(maxResults).list();
			} catch (RuntimeException re) {
				log.error(String.format("findPage %s failed", entityClass.getName()), re);
				throw re;
			}
		} else {
			try {
				queryString = String.format("from %s model", entityClass.getName());

				return sessionFactory.getCurrentSession().createQuery(queryString).setFirstResult(startRow)
						.setMaxResults(maxResults).list();
			} catch (RuntimeException re) {
				log.error(String.format("findPage %s failed", entityClass.getName()), re);
				throw re;
			}
		}
	}

	@Override
	public List<T> findPage(String sortColumnName, boolean sortAscending) {
		log.debug("findPage {}", entityClass.getName());

		String queryString = "";
		if ((sortColumnName != null) && (sortColumnName.length() > 0)) {
			try {
				if (sortAscending) {
					queryString = String.format("from %s model order by model.%s asc", entityClass.getName(),
							sortColumnName);
				} else {
					queryString = String.format("from %s model order by model.%s desc", entityClass.getName(),
							sortColumnName);
				}

				return sessionFactory.getCurrentSession().createQuery(queryString).list();
			} catch (RuntimeException re) {
				log.error(String.format("findPage %s failed", entityClass.getName()), re);
				throw re;
			}
		} else {
			try {
				queryString = String.format("from %s model", entityClass.getName());

				return sessionFactory.getCurrentSession().createQuery(queryString).list();
			} catch (RuntimeException re) {
				log.error(String.format("findPage %s failed", entityClass.getName()), re);
				throw re;
			}
		}
	}

	@Override
	public List<T> findByCriteria(String whereCondition) {
		log.debug("finding {} {}", entityClass.getName(), whereCondition);

		try {
			String where = ((whereCondition == null) || (whereCondition.length() == 0)) ? ""
					: ("where " + whereCondition);
			final String queryString = String.format("from %s model %s", entityClass.getName(), where);

			return sessionFactory.getCurrentSession().createQuery(queryString).list();
		} catch (RuntimeException re) {
			log.error("find By Criteria failed", re);
			throw re;
		}
	}

	@Override
	public T findObjectByCriteria(String whereCondition) {
		log.debug("finding {} {}", entityClass.getName(), whereCondition);

		try {
			String where = ((whereCondition == null) || (whereCondition.length() == 0)) ? ""
					: ("where " + whereCondition);
			final String queryString = String.format("from %s model %s", entityClass.getName(), where);

			return (T) sessionFactory.getCurrentSession().createQuery(queryString).uniqueResult();
		} catch (RuntimeException re) {
			log.error("find By Criteria failed", re);
			throw re;
		}
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		log.debug("finding {} instance with property: {}, value: {}", entityClass.getName(), propertyName, value);

		try {
			final String queryString = String.format("from %s model where model.%s = :%s", entityClass.getName(),
					propertyName, propertyName);

			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			applyNamedParameterToQuery(queryObject, propertyName, value);

			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<T> findByPropertySort(String propertyName, Object value, String sortColumnName, boolean sortAscending) {
		log.debug("finding {} instance with property: {}, value: {}, sortColumnName: {}, sortAscending: {}",
				entityClass.getName(), propertyName, value, sortColumnName, sortAscending);

		try {
			String queryString = "";
			if ((sortColumnName != null) && (sortColumnName.length() > 0)) {
				if (sortAscending) {
					queryString = String.format("from %s model where model.%s = :%s order by model.%s asc",
							entityClass.getName(), propertyName, propertyName, sortColumnName);
				} else {
					queryString = String.format("from %s model where model.%s = :%s order by model.%s desc",
							entityClass.getName(), propertyName, propertyName, sortColumnName);
				}
			} else {
				queryString = String.format("from %s model where model.%s = :%s", entityClass.getName(), propertyName,
						propertyName);
			}

			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			applyNamedParameterToQuery(queryObject, propertyName, value);

			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<T> findByProperty(String propertyName, Collection<?> values) {
		log.debug("finding {} instance with property: {}, value: {}", entityClass.getName(), propertyName, values);

		try {
			final String queryString = String.format("from %s model where model.%s in :%s", entityClass.getName(),
					propertyName, propertyName);

			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			applyNamedParameterToQuery(queryObject, propertyName, values);

			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public T findObjectByProperty(String propertyName, Object value) {
		log.debug("finding {} instance with property: {}, value: {}", entityClass.getName(), propertyName, value);

		try {
			final String queryString = String.format("from %s model where model.%s = :%s", entityClass.getName(),
					propertyName, propertyName);

			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			applyNamedParameterToQuery(queryObject, propertyName, value);

			return (T) queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

}
