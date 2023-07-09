package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IUsuarioAppLogic;
import co.com.juan.invbill.dao.IUsuarioAppDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.UsuarioApp;
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
public class UsuarioAppLogic implements IUsuarioAppLogic {

    private static final Logger log = LoggerFactory.getLogger(UsuarioAppLogic.class);
    private final IUsuarioAppDao usuarioAppDao;

    @Inject
    public UsuarioAppLogic(IUsuarioAppDao usuarioAppDao) {
        this.usuarioAppDao = usuarioAppDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUsuarioApp(UsuarioApp entity) {
        try {
            this.checkFields(entity);

            if (this.getUsuarioApp(entity.getIdUsuarioApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            this.usuarioAppDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUsuarioApp(UsuarioApp entity) {
        try {
            this.checkFields(entity);
            this.usuarioAppDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioApp getUsuarioApp(String id) {
        UsuarioApp entity;
        try {
            entity = this.usuarioAppDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                           Object[] variablesBetweenDates) {
        List<UsuarioApp> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.usuarioAppDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioApp> findByProperty(String propertyName, Object value) {
        List<UsuarioApp> list;
        try {
            list = this.usuarioAppDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(UsuarioApp entity) {
        if (entity.getIdUsuarioApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdUsuarioApp() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getIdUsuarioApp(), 20))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if (entity.getNombre() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_NOMBRE);
        }

        if ((entity.getNombre() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getNombre(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_NOMBRE);
        }

        if (entity.getPassword() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_PASSW);
        }

        if ((entity.getPassword() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getPassword(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_PASSW);
        }

        if (entity.getEstado() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ESTADO);
        }

        if (entity.getRolApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ROL);
        }

        if ((entity.getRolApp() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getRolApp().getRol(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ROL);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "UsuarioApp";
        private static final String FIELD_ID_ENTITY = "idUsuarioApp";
        private static final String FIELD_NOMBRE = "nombre";
        private static final String FIELD_PASSW = "password";
        private static final String FIELD_ESTADO = "estado";
        private static final String FIELD_ROL = "rol";
    }

}
