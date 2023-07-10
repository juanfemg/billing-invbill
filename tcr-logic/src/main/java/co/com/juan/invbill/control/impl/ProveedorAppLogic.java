package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IProveedorAppLogic;
import co.com.juan.invbill.dao.IProveedorAppDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ProveedorApp;
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
public class ProveedorAppLogic implements IProveedorAppLogic {

    private static final Logger log = LoggerFactory.getLogger(ProveedorAppLogic.class);
    private final IProveedorAppDao proveedorAppDao;

    @Inject
    public ProveedorAppLogic(IProveedorAppDao proveedorAppDao) {
        this.proveedorAppDao = proveedorAppDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorApp> getProveedorApp() throws EntityException {
        List<ProveedorApp> list;
        try {
            list = this.proveedorAppDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProveedorApp(ProveedorApp entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.checkSecondaryFields(entity);

            if (this.getProveedorApp(entity.getIdProveedorApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITH_SAME_KEY);
            }

            this.proveedorAppDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProveedorApp(ProveedorApp entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.checkSecondaryFields(entity);
            this.proveedorAppDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorApp getProveedorApp(Integer id) throws EntityException {
        ProveedorApp entity;
        try {
            entity = this.proveedorAppDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private void checkFields(ProveedorApp entity) {
        if (entity.getIdProveedorApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdProveedorApp() != null) && !(Utilities
                .checkNumberAndCheckWithPrecisionAndScale(entity.getIdProveedorApp().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if (entity.getCodVerificacion() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_COD_VERIFICACION);
        }

        if ((entity.getCodVerificacion() != null) && !(Utilities
                .checkNumberAndCheckWithPrecisionAndScale(entity.getCodVerificacion().toString(), 1, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_COD_VERIFICACION);
        }

        if (entity.getRazonSocial() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_RAZON_SOCIAL);
        }

        if ((entity.getRazonSocial() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getRazonSocial(), 200))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_RAZON_SOCIAL);
        }

        if (entity.getEstado() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ESTADO);
        }
    }

    private void checkSecondaryFields(ProveedorApp entity) {
        if ((entity.getDireccion() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getDireccion(), 200))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_DIRECCION);
        }

        if ((entity.getTelefono() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getTelefono().toString(), 7, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_TELEFONO);
        }

        if ((entity.getTelefonoExt() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getTelefonoExt().toString(), 6, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_TELEFONO_EXT);
        }

        if ((entity.getCelular() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCelular().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_CELULAR);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ProveedorApp";
        private static final String FIELD_ID_ENTITY = "idProveedorApp";
        private static final String FIELD_COD_VERIFICACION = "codVerificacion";
        private static final String FIELD_RAZON_SOCIAL = "razonSocial";
        private static final String FIELD_ESTADO = "estado";
        private static final String FIELD_DIRECCION = "direccion";
        private static final String FIELD_TELEFONO = "telefono";
        private static final String FIELD_TELEFONO_EXT = "telefonoExt";
        private static final String FIELD_CELULAR = "celular";
    }

}
