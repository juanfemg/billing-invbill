package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ITipoUnidadMedidaLogic;
import co.com.juan.invbill.dao.ITipoUnidadMedidaDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.TipoUnidadMedida;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Service("TipoUnidadMedidaLogic")
public class TipoUnidadMedidaLogic implements ITipoUnidadMedidaLogic {

    private static final Logger log = LoggerFactory.getLogger(TipoUnidadMedidaLogic.class);
    private final ITipoUnidadMedidaDao tipoUnidadMedidaDao;

    @Inject
    public TipoUnidadMedidaLogic(ITipoUnidadMedidaDao tipoUnidadMedidaDao) {
        this.tipoUnidadMedidaDao = tipoUnidadMedidaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoUnidadMedida> getTipoUnidadMedida() {
        log.debug("finding all {} instances", Constant.ENTITY_NAME);

        List<TipoUnidadMedida> list;

        try {
            list = tipoUnidadMedidaDao.findAll();
        } catch (Exception e) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.GettingException(EntityException.ALL + Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTipoUnidadMedida(TipoUnidadMedida entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            if (getTipoUnidadMedida(entity.getIdUnidadMedida()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            tipoUnidadMedidaDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTipoUnidadMedida(TipoUnidadMedida entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            tipoUnidadMedidaDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TipoUnidadMedida getTipoUnidadMedida(Integer id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        TipoUnidadMedida entity;

        try {
            entity = tipoUnidadMedidaDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoUnidadMedida> findByCriteria(Object[] variables, Object[] variablesBetween,
                                                 Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<TipoUnidadMedida> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = tipoUnidadMedidaDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoUnidadMedida> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<TipoUnidadMedida> list;

        try {
            list = tipoUnidadMedidaDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(TipoUnidadMedida entity) {
        if (entity.getTipoUnidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_TIPO_UNIDAD);
        }

        if ((entity.getTipoUnidad() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getTipoUnidad(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_TIPO_UNIDAD);
        }

        if (entity.getUnidad() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_UNIDAD);
        }

        if ((entity.getUnidad() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getUnidad(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_UNIDAD);
        }

        if (entity.getEstado() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ESTADO);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "TipoUnidadMedida";
        private static final String FIELD_TIPO_UNIDAD = "tipoUnidad";
        private static final String FIELD_UNIDAD = "unidad";
        private static final String FIELD_ESTADO = "estado";
    }

}
