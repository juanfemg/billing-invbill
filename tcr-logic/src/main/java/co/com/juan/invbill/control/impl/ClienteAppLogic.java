package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IClienteAppLogic;
import co.com.juan.invbill.dao.IClienteAppDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.ClienteApp;
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
public class ClienteAppLogic implements IClienteAppLogic {

    private static final Logger log = LoggerFactory.getLogger(ClienteAppLogic.class);
    private final IClienteAppDao clienteAppDao;

    @Inject
    public ClienteAppLogic(IClienteAppDao clienteAppDao) {
        this.clienteAppDao = clienteAppDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteApp> getClienteApp() throws EntityException {
        List<ClienteApp> list;
        try {
            list = this.clienteAppDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveClienteApp(ClienteApp entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.checkSecondaryFields(entity);

            if (this.getClienteApp(entity.getIdClienteApp()) != null) {
                throw new EntityException(EntityException.ENTITY_WITH_SAME_KEY);
            }

            this.clienteAppDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateClienteApp(ClienteApp entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.checkSecondaryFields(entity);
            this.clienteAppDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteApp getClienteApp(Integer id) throws EntityException {
        ClienteApp entity;
        try {
            entity = this.clienteAppDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private void checkFields(ClienteApp entity) {
        if (entity.getIdClienteApp() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_ID_ENTITY);
        }

        if ((entity.getIdClienteApp() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getIdClienteApp().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ID_ENTITY);
        }

        if (entity.getRazonSocial() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_RAZON_SOCIAL);
        }

        if ((entity.getRazonSocial() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getRazonSocial(), 200))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_RAZON_SOCIAL);
        }

        if (entity.getEstado() == null) {
            entity.setEstado(StatusEnum.A);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ESTADO);
        }
    }

    private void checkSecondaryFields(ClienteApp entity) {
        if ((entity.getCodVerificacion() != null) && !(Utilities
                .checkNumberAndCheckWithPrecisionAndScale(entity.getCodVerificacion().toString(), 1, 0))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_COD_VERIFICACION);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "ClienteApp";
        private static final String FIELD_ID_ENTITY = "idClienteApp";
        private static final String FIELD_COD_VERIFICACION = "codVerificacion";
        private static final String FIELD_RAZON_SOCIAL = "razonSocial";
        private static final String FIELD_ESTADO = "estado";
    }

}
