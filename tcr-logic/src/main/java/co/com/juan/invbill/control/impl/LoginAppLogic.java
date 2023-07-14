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
import java.time.LocalDateTime;

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
    public void saveLoginApp(LoginApp entity) throws EntityException {
        try {
            this.checkFields(entity);

            if (this.getLoginApp(entity.getIdLoginApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITH_SAME_KEY);
            }

            this.loginAppDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateLoginApp(LoginApp entity) throws EntityException {
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
    public LoginApp getLoginApp(String id) throws EntityException {
        LoginApp entity;
        try {
            entity = this.loginAppDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private void checkFields(LoginApp entity) {
        if (entity.getIdLoginApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdLoginApp() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getIdLoginApp(), 20))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ID_ENTITY);
        }

        if (entity.getUltimoLogin() != null) {
            LocalDateTime date = Utilities.convertToLocalDateTime(entity.getUltimoLogin());
            if (!(Utilities
                    .isDate(Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date)))) {
                throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ULTIMO_LOGIN);
            }
        }

        if ((entity.getIdSession() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getIdSession(), 100))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ULTIMO_LOGIN);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "LoginApp";
        private static final String FIELD_ID_ENTITY = "idLoginApp";
        private static final String FIELD_ULTIMO_LOGIN = "ultimoLogin";
    }

}
