package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IAppConfigLogic;
import co.com.juan.invbill.dao.IAppConfigDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;
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
@Service("AppConfigLogic")
public class AppConfigLogic implements IAppConfigLogic {

    private static final Logger log = LoggerFactory.getLogger(AppConfigLogic.class);

    @Autowired
    private IAppConfigDao appConfigDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAppConfig(AppConfig entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            if (getAppConfig(entity.getIdAppConfig()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            appConfigDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAppConfig(AppConfig entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            appConfigDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AppConfig getAppConfig(String id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        AppConfig entity;

        try {
            entity = appConfigDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppConfig> getDataAppConfig() {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        List<AppConfig> list;

        try {
            list = appConfigDao.findAll();
        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppConfig> findByCriteria(Object[] variables, Object[] variablesBetween,
                                          Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<AppConfig> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = appConfigDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppConfig> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<AppConfig> list;

        try {
            list = appConfigDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(AppConfig entity) {
        if (entity.getIdAppConfig() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdAppConfig() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getIdAppConfig(), 50))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if (entity.getDescripcion() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_DESCRIPCION);
        }

        if ((entity.getDescripcion() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getDescripcion(), 200))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_DESCRIPCION);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "AppConfig";
        private static final String FIELD_ID_ENTITY = "idAppConfig";
        private static final String FIELD_DESCRIPCION = "descripcion";
    }

}
