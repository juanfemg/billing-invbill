package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ILoginAppLogic;
import co.com.juan.invbill.dao.ILoginAppDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.LoginApp;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Juan Felipe
 */

@Scope("singleton")
@Service("LoginAppLogic")
public class LoginAppLogic implements ILoginAppLogic {

    private static final Logger log = LoggerFactory.getLogger(LoginAppLogic.class);

    @Autowired
    private ILoginAppDao loginAppDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLoginApp(LoginApp entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            if (getLoginApp(entity.getIdLoginApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            loginAppDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateLoginApp(LoginApp entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            loginAppDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoginApp getLoginApp(String id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        LoginApp entity;

        try {
            entity = loginAppDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoginApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                         Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<LoginApp> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = loginAppDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoginApp> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<LoginApp> list;

        try {
            list = loginAppDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(LoginApp entity) {
        if (entity.getIdLoginApp() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdLoginApp() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getIdLoginApp(), 20))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getUltimoLogin() != null) && !(Utilities
                .isDate(Utilities.formatDateWithoutTimeInAStringForBetweenWhere(entity.getUltimoLogin())))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_ULTIMO_LOGIN);
        }

        if ((entity.getIdSession() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getIdSession(), 100))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_ULTIMO_LOGIN);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "LoginApp";
        private static final String FIELD_ID_ENTITY = "idLoginApp";
        private static final String FIELD_ULTIMO_LOGIN = "ultimoLogin";
    }

}