package co.com.juan.invbill.dataaccess.api;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Juan Felipe
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class HibernateDaoImpl<T, P extends Serializable> implements Dao<T, P> {

    private static final String QUERY_CACHE_REGION = "cache";
    private static final int MAX_RESULTS = Integer.MAX_VALUE;
    private final Class<T> entityClass;
    private final Logger log;
    private final SessionFactory sessionFactory;

    @Inject
    public HibernateDaoImpl(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.log = LoggerFactory.getLogger(this.entityClass);
    }

    @Override
    public void save(T newEntity) throws DaoException {
        try {
            this.sessionFactory.getCurrentSession().save(newEntity);
        } catch (HibernateException he) {
            this.log.error(String.format("failed creating %s instance", this.entityClass.getSimpleName()), he);
            throw new DaoException(he);
        }
    }

    @Override
    public void update(T entity) throws DaoException {
        try {
            this.sessionFactory.getCurrentSession().update(entity);
        } catch (HibernateException he) {
            this.log.error(String.format("failed updating %s instance", this.entityClass.getSimpleName()), he);
            throw new DaoException(he);
        }
    }

    @Override
    public T findById(P id) throws DaoException {
        try {
            return (T) this.sessionFactory.getCurrentSession().get(this.entityClass, id);
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instance with id: %s", this.entityClass.getSimpleName(), id), he);
            throw new DaoException(he);
        }
    }

    @Override
    public Object maxByCriteria(String propertyName) throws DaoException {
        try {
            Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(this.entityClass);
            criteria.setProjection(Projections.max(propertyName));
            prepareCriteria(criteria);
            return criteria.uniqueResult();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding result for %s instance with property: %s", this.entityClass.getSimpleName(), propertyName), he);
            throw new DaoException(he);
        }
    }

    @Override
    public Object minByCriteria(String propertyName) throws DaoException {
        try {
            Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(this.entityClass);
            criteria.setProjection(Projections.min(propertyName));
            prepareCriteria(criteria);
            return criteria.uniqueResult();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding result for %s instance with property: %s", this.entityClass.getSimpleName(), propertyName), he);
            throw new DaoException(he);
        }
    }

    @Override
    public Object avgByCriteria(String propertyName) throws DaoException {
        try {
            Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(this.entityClass);
            criteria.setProjection(Projections.avg(propertyName));
            prepareCriteria(criteria);
            return criteria.uniqueResult();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding result for %s instance with property: %s", this.entityClass.getSimpleName(), propertyName), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findByNamedQuery(String queryName, Object value) throws DaoException {
        Object[] values = {value};
        return findByNamedQuery(queryName, values);
    }

    @Override
    public List<T> findByNamedQuery(String queryName, Object... values) throws DaoException {
        try {
            Query queryObject = createNamedQuery(queryName);
            queryObject.setResultTransformer(Transformers.aliasToBean(this.entityClass));

            if (values != null) {
                IntStream.range(0, values.length)
                        .forEach(index -> queryObject.setParameter(index, values[index]));
            }

            return queryObject.list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with query name: %s, values: %s", this.entityClass.getSimpleName(), queryName, Arrays.toString(values)), he);
            throw new DaoException(he);
        }
    }

    @Override
    public T findObjectByNamedQuery(String queryName, Object value) throws DaoException {
        Object[] values = {value};
        return findObjectByNamedQuery(queryName, values);
    }

    @Override
    public T findObjectByNamedQuery(String queryName, Object... values) throws DaoException {
        try {
            Query queryObject = createNamedQuery(queryName);
            queryObject.setResultTransformer(Transformers.aliasToBean(this.entityClass));

            if (values != null) {
                IntStream.range(0, values.length)
                        .forEach(index -> queryObject.setParameter(index, values[index]));
            }

            return (T) queryObject.uniqueResult();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instance with query name: %s, values: %s", this.entityClass.getSimpleName(), queryName, Arrays.toString(values)), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findByNamedQueryWithoutAlias(String queryName, Object value) throws DaoException {
        Object[] values = {value};
        return findByNamedQueryWithoutAlias(queryName, values);
    }

    @Override
    public List<T> findByNamedQueryWithoutAlias(String queryName, Object... values) throws DaoException {
        try {
            Query queryObject = createNamedQuery(queryName);

            if (values != null) {
                IntStream.range(0, values.length)
                        .forEach(index -> queryObject.setParameter(index, values[index]));
            }

            return queryObject.list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with query name: %s, values: %s", this.entityClass.getSimpleName(), queryName, Arrays.toString(values)), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> find(String queryString) throws DaoException {
        return find(queryString, (Object[]) null);
    }

    @Override
    public List<T> find(String queryString, Object value) throws DaoException {
        Object[] values = {value};
        return find(queryString, values);
    }

    @Override
    public List<T> find(String queryString, Object... values) throws DaoException {
        try {
            Query queryObject = createQuery(queryString);

            if (values != null) {
                IntStream.range(0, values.length)
                        .forEach(index -> queryObject.setParameter(index, values[index]));
            }

            return queryObject.list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with query string: %s, values: %s", this.entityClass.getSimpleName(), queryString, Arrays.toString(values)), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findAll() throws DaoException {
        try {
            return this.sessionFactory.getCurrentSession().createCriteria(this.entityClass).list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances", this.entityClass.getSimpleName()), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findPage(String sortColumnName, boolean sortAscending) throws DaoException {
        String queryString;
        try {
            if ((sortColumnName != null) && (sortColumnName.length() > 0)) {
                if (sortAscending) {
                    queryString = String.format("from %s model order by model.%s asc", this.entityClass.getSimpleName(),
                            sortColumnName);
                } else {
                    queryString = String.format("from %s model order by model.%s desc", this.entityClass.getSimpleName(),
                            sortColumnName);
                }
            } else {
                queryString = String.format("from %s model", this.entityClass.getSimpleName());
            }

            return this.sessionFactory.getCurrentSession().createQuery(queryString).list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with sort column name: %s, sort ascending: %s", this.entityClass.getSimpleName(), sortColumnName, sortAscending), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findByCriteria(String whereCondition) throws DaoException {
        try {
            String where = ((whereCondition == null) || (whereCondition.length() == 0)) ? ""
                    : ("where " + whereCondition);
            final String queryString = String.format("from %s model %s", this.entityClass.getSimpleName(), where);

            return this.sessionFactory.getCurrentSession().createQuery(queryString).list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with condition: %s", this.entityClass.getSimpleName(), whereCondition), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) throws DaoException {
        try {
            final String queryString = String.format("from %s model where model.%s = :%s", this.entityClass.getSimpleName(),
                    propertyName, propertyName);

            Query queryObject = this.sessionFactory.getCurrentSession().createQuery(queryString);
            applyNamedParameterToQuery(queryObject, propertyName, value);

            return queryObject.list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with property: %s, value: %s", this.entityClass.getSimpleName(), propertyName, value), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findByPropertySort(String propertyName, Object value, String sortColumnName, boolean sortAscending) throws DaoException {
        try {
            String queryString;
            if ((sortColumnName != null) && (sortColumnName.length() > 0)) {
                if (sortAscending) {
                    queryString = String.format("from %s model where model.%s = :%s order by model.%s asc",
                            this.entityClass.getSimpleName(), propertyName, propertyName, sortColumnName);
                } else {
                    queryString = String.format("from %s model where model.%s = :%s order by model.%s desc",
                            this.entityClass.getSimpleName(), propertyName, propertyName, sortColumnName);
                }
            } else {
                queryString = String.format("from %s model where model.%s = :%s", this.entityClass.getSimpleName(), propertyName,
                        propertyName);
            }

            Query queryObject = this.sessionFactory.getCurrentSession().createQuery(queryString);
            applyNamedParameterToQuery(queryObject, propertyName, value);

            return queryObject.list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with property: %s, value: %s, sortColumnName: %s, sortAscending: %s",
                    this.entityClass.getSimpleName(), propertyName, value, sortColumnName, sortAscending), he);
            throw new DaoException(he);
        }
    }

    @Override
    public List<T> findByProperty(String propertyName, Collection<?> values) throws DaoException {
        try {
            final String queryString = String.format("from %s model where model.%s in :%s", this.entityClass.getSimpleName(),
                    propertyName, propertyName);

            Query queryObject = this.sessionFactory.getCurrentSession().createQuery(queryString);
            applyNamedParameterToQuery(queryObject, propertyName, values);

            return queryObject.list();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instances with property: %s, value: %s", this.entityClass.getSimpleName(), propertyName, values), he);
            throw new DaoException(he);
        }
    }

    @Override
    public T findObjectByProperty(String propertyName, Object value) throws DaoException {
        try {
            final String queryString = String.format("from %s model where model.%s = :%s", this.entityClass.getSimpleName(),
                    propertyName, propertyName);

            Query queryObject = this.sessionFactory.getCurrentSession().createQuery(queryString);
            applyNamedParameterToQuery(queryObject, propertyName, value);

            return (T) queryObject.uniqueResult();
        } catch (HibernateException he) {
            this.log.error(String.format("failed finding %s instance with property: %s, value: %s failed", this.entityClass.getSimpleName(), propertyName, value), he);
            throw new DaoException(he);
        }
    }

    private void applyNamedParameterToQuery(Query queryObject, String paramName, Object value) {
        if (value instanceof Collection) {
            queryObject.setParameterList(paramName, (Collection) value);
        } else if (value instanceof Object[]) {
            queryObject.setParameterList(paramName, new Object[]{value});
        } else {
            queryObject.setParameter(paramName, value);
        }
    }

    private void prepareQuery(Query queryObject) {
        queryObject.setCacheable(true);
        queryObject.setCacheRegion(QUERY_CACHE_REGION);
        queryObject.setMaxResults(MAX_RESULTS);
    }

    private void prepareCriteria(Criteria criteria) {
        criteria.setCacheable(true);
        criteria.setCacheRegion(QUERY_CACHE_REGION);
        criteria.setMaxResults(MAX_RESULTS);
    }

    private Query createQuery(String queryString) {
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        prepareQuery(query);
        return query;
    }

    private Query createNamedQuery(String queryString) {
        Query query = this.sessionFactory.getCurrentSession().getNamedQuery(queryString);
        prepareQuery(query);
        return query;
    }

}
