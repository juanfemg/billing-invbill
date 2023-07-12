package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ITipoUnidadMedidaLogic;
import co.com.juan.invbill.dao.ITipoUnidadMedidaDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.TipoUnidadMedida;
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
public class TipoUnidadMedidaLogic implements ITipoUnidadMedidaLogic {

    private static final Logger log = LoggerFactory.getLogger(TipoUnidadMedidaLogic.class);
    private final ITipoUnidadMedidaDao tipoUnidadMedidaDao;

    @Inject
    public TipoUnidadMedidaLogic(ITipoUnidadMedidaDao tipoUnidadMedidaDao) {
        this.tipoUnidadMedidaDao = tipoUnidadMedidaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoUnidadMedida> getTipoUnidadMedida() throws EntityException {
        List<TipoUnidadMedida> list;
        try {
            list = this.tipoUnidadMedidaDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTipoUnidadMedida(TipoUnidadMedida entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.tipoUnidadMedidaDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTipoUnidadMedida(TipoUnidadMedida entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.tipoUnidadMedidaDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TipoUnidadMedida getTipoUnidadMedida(Integer id) throws EntityException {
        TipoUnidadMedida entity;
        try {
            entity = this.tipoUnidadMedidaDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    private void checkFields(TipoUnidadMedida entity) {
        if (entity.getTipoUnidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_TIPO_UNIDAD);
        }

        if ((entity.getTipoUnidad() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getTipoUnidad(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_TIPO_UNIDAD);
        }

        if (entity.getUnidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_UNIDAD);
        }

        if ((entity.getUnidad() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getUnidad(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_UNIDAD);
        }

        if (entity.getEstado() == null) {
            entity.setEstado(StatusEnum.A);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithLength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_ESTADO);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "TipoUnidadMedida";
        private static final String FIELD_TIPO_UNIDAD = "tipoUnidad";
        private static final String FIELD_UNIDAD = "unidad";
        private static final String FIELD_ESTADO = "estado";
    }

}
