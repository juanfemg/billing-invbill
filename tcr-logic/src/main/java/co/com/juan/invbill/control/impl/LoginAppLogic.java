package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ILoginAppLogic;
import co.com.juan.invbill.dao.ILoginAppDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class LoginAppLogic implements ILoginAppLogic {

    private static final Logger log = LoggerFactory.getLogger(LoginAppLogic.class);
    private final ILoginAppDao loginAppDao;

    @Inject
    public LoginAppLogic(ILoginAppDao loginAppDao) {
        this.loginAppDao = loginAppDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLoginApp(LoginApp entity) {
        try {
            this.checkFields(entity);

            if (this.getLoginApp(entity.getIdLoginApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            this.loginAppDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateLoginApp(LoginApp entity) {
        try {
            this.checkFields(entity);
            this.loginAppDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoginApp getLoginApp(String id) {
        LoginApp entity;
        try {
            entity = this.loginAppDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoginApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                         Object[] variablesBetweenDates) {
        List<LoginApp> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.loginAppDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoginApp> findByProperty(String propertyName, Object value) {
        List<LoginApp> list;
        try {
            list = this.loginAppDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(LoginApp entity) {
        if (entity.getIdLoginApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdLoginApp() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getIdLoginApp(), 20))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getUltimoLogin() != null) && !(Utilities
                .isDate(Utilities.formatDateWithoutTimeInAStringForBetweenWhere(entity.getUltimoLogin())))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ULTIMO_LOGIN);
        }

        if ((entity.getIdSession() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getIdSession(), 100))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ULTIMO_LOGIN);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "LoginApp";
        private static final String FIELD_ID_ENTITY = "idLoginApp";
        private static final String FIELD_ULTIMO_LOGIN = "ultimoLogin";
    }

}
