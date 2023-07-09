package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IAppConfigLogic;
import co.com.juan.invbill.dao.IAppConfigDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppConfig;
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
public class AppConfigLogic implements IAppConfigLogic {

    private static final Logger log = LoggerFactory.getLogger(AppConfigLogic.class);
    private final IAppConfigDao appConfigDao;

    @Inject
    public AppConfigLogic(IAppConfigDao appConfigDao) {
        this.appConfigDao = appConfigDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAppConfig(AppConfig entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.appConfigDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppConfig> getDataAppConfig() throws EntityException {
        List<AppConfig> list;
        try {
            list = this.appConfigDao.findAll();
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(AppConfig entity) {
        if (entity.getIdAppConfig() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdAppConfig() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getIdAppConfig(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if (entity.getDescripcion() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_DESCRIPCION);
        }

        if ((entity.getDescripcion() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getDescripcion(), 200))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_DESCRIPCION);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "AppConfig";
        private static final String FIELD_ID_ENTITY = "idAppConfig";
        private static final String FIELD_DESCRIPCION = "descripcion";
    }

}
