package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ITipoPeriodoLogic;
import co.com.juan.invbill.dao.ITipoPeriodoDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.TipoPeriodo;
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
public class TipoPeriodoLogic implements ITipoPeriodoLogic {

    private static final Logger log = LoggerFactory.getLogger(TipoPeriodoLogic.class);
    private final ITipoPeriodoDao tipoPeriodoDao;

    @Inject
    public TipoPeriodoLogic(ITipoPeriodoDao tipoPeriodoDao) {
        this.tipoPeriodoDao = tipoPeriodoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPeriodo> getTipoPeriodo() throws EntityException {
        List<TipoPeriodo> list;
        try {
            list = this.tipoPeriodoDao.findAll();
        } catch (DaoException de) {
            log.error("finding all {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.GettingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTipoPeriodo(TipoPeriodo entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.tipoPeriodoDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTipoPeriodo(TipoPeriodo entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.tipoPeriodoDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TipoPeriodo getTipoPeriodo(Integer id) throws EntityException {
        TipoPeriodo entity;
        try {
            entity = this.tipoPeriodoDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPeriodo> findByCriteria(Object[] variables, Object[] variablesBetween,
                                            Object[] variablesBetweenDates) throws EntityException {
        List<TipoPeriodo> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.tipoPeriodoDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPeriodo> findByProperty(String propertyName, Object value) throws EntityException {
        List<TipoPeriodo> list;
        try {
            list = this.tipoPeriodoDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(TipoPeriodo entity) {
        if (entity.getPeriodo() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_PERIODO);
        }

        if ((entity.getPeriodo() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getPeriodo(), 45))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_PERIODO);
        }

        if (entity.getNumeroMeses() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_NUMERO_MESES);
        }

        if ((entity.getNumeroMeses() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getNumeroMeses().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_NUMERO_MESES);
        }

        if (entity.getEstado() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_ESTADO);
        }

        if ((entity.getEstado() != null) && !(Utilities.checkWordAndCheckWithlength(entity.getEstado().name(), 1))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_ESTADO);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "TipoPeriodo";
        private static final String FIELD_PERIODO = "periodo";
        private static final String FIELD_NUMERO_MESES = "numeroMeses";
        private static final String FIELD_ESTADO = "estado";
    }

}
