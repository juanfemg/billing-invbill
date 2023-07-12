package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IUsuarioAppLogic;
import co.com.juan.invbill.dao.IUsuarioAppDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.RolApp;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Utilities;
import co.com.juan.invbill.util.security.IEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class UsuarioAppLogic implements IUsuarioAppLogic {

    private static final Logger log = LoggerFactory.getLogger(UsuarioAppLogic.class);
    private final IUsuarioAppDao usuarioAppDao;
    private final IEncryption encryption;

    @Inject
    public UsuarioAppLogic(IUsuarioAppDao usuarioAppDao, IEncryption encryption) {
        this.usuarioAppDao = usuarioAppDao;
        this.encryption = encryption;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUsuarioApp(UsuarioApp entity) throws EntityException {
        try {
            this.checkFields(entity);

            if (this.getUsuarioApp(entity.getIdUsuarioApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITH_SAME_KEY);
            }

            this.usuarioAppDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUsuarioApp(UsuarioApp entity) throws EntityException {
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
    public UsuarioApp getUsuarioApp(String id) throws EntityException {
        UsuarioApp entity;
        try {
            entity = this.usuarioAppDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private void checkFields(UsuarioApp entity) {
        if (entity.getIdUsuarioApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdUsuarioApp() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getIdUsuarioApp(), 20))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ID_ENTITY);
        }

        if (entity.getNombre() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_NOMBRE);
        }

        if ((entity.getNombre() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getNombre(), 50))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_NOMBRE);
        }

        if (entity.getPassword() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_PASSW);
        } else {
            entity.setPassword(this.encryption.encrypt(entity.getPassword()));
        }

        if ((entity.getPassword() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getPassword(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_PASSW);
        }

        if (entity.getEstado() == null) {
            entity.setEstado(StatusEnum.A);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ESTADO);
        }

        if (entity.getRolApp() == null) {
            RolApp rolApp = this.buildRolApp();
            entity.setRolApp(rolApp);
        }

        if ((entity.getRolApp() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getRolApp().getRol(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ROL);
        }
    }

    private RolApp buildRolApp() {
        return RolApp.builder()
                .idRolApp(1)
                .rol("ADMINISTRADOR")
                .estado(StatusEnum.A)
                .build();
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
