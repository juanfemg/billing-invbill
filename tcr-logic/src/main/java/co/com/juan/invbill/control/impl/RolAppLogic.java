package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IRolAppLogic;
import co.com.juan.invbill.dao.IRolAppDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.RolApp;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class RolAppLogic implements IRolAppLogic {

    private static final Logger log = LoggerFactory.getLogger(RolAppLogic.class);
    private final IRolAppDao rolAppDao;

    @Inject
    public RolAppLogic(IRolAppDao rolAppDao) {
        this.rolAppDao = rolAppDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolApp> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates) {
        List<RolApp> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.rolAppDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolApp> findByProperty(String propertyName, Object value) {
        List<RolApp> list;
        try {
            list = this.rolAppDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "RolApp";
    }

}
