package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IProveedorAppLogic;
import co.com.juan.invbill.dao.IProveedorAppDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ProveedorApp;
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
@Service("ProveedorAppLogic")
public class ProveedorAppLogic implements IProveedorAppLogic {

    private static final Logger log = LoggerFactory.getLogger(ProveedorAppLogic.class);

    @Autowired
    private IProveedorAppDao proveedorAppDao;

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorApp> getProveedorApp() {
        log.debug("finding all {} instances", Constant.ENTITY_NAME);

        List<ProveedorApp> list;

        try {
            list = proveedorAppDao.findAll();
        } catch (Exception e) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new GettingException(EntityException.ALL + Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProveedorApp(ProveedorApp entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);
            checkSecondaryFields(entity);

            if (getProveedorApp(entity.getIdProveedorApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            proveedorAppDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProveedorApp(ProveedorApp entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);
            checkSecondaryFields(entity);

            proveedorAppDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorApp getProveedorApp(Integer id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        ProveedorApp entity;

        try {
            entity = proveedorAppDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorApp> findByCriteria(Object[] variables, Object[] variablesBetween,
                                             Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<ProveedorApp> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = proveedorAppDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorApp> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<ProveedorApp> list;

        try {
            list = proveedorAppDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(ProveedorApp entity) {
        if (entity.getIdProveedorApp() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdProveedorApp() != null) && !(Utilities
                .checkNumberAndCheckWithPrecisionAndScale(entity.getIdProveedorApp().toString(), 11, 0))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_ID_ENTITY);
        }

        if (entity.getCodVerificacion() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_COD_VERIFICACION);
        }

        if ((entity.getCodVerificacion() != null) && !(Utilities
                .checkNumberAndCheckWithPrecisionAndScale(entity.getCodVerificacion().toString(), 1, 0))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_COD_VERIFICACION);
        }

        if (entity.getRazonSocial() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_RAZON_SOCIAL);
        }

        if ((entity.getRazonSocial() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getRazonSocial(), 200))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_RAZON_SOCIAL);
        }

        if (entity.getEstado() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_ESTADO);
        }
    }

    private void checkSecondaryFields(ProveedorApp entity) {
        if ((entity.getDireccion() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getDireccion(), 200))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_DIRECCION);
        }

        if ((entity.getTelefono() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getTelefono().toString(), 7, 0))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_TELEFONO);
        }

        if ((entity.getTelefonoExt() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getTelefonoExt().toString(), 6, 0))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_TELEFONO_EXT);
        }

        if ((entity.getCelular() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCelular().toString(), 11, 0))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_CELULAR);
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
