package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IFacturaCabeceraLogic;
import co.com.juan.invbill.dao.IFacturaCabeceraDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.FacturaCabecera;
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
public class FacturaCabeceraLogic implements IFacturaCabeceraLogic {

    private static final Logger log = LoggerFactory.getLogger(FacturaCabeceraLogic.class);
    private final IFacturaCabeceraDao facturaCabeceraDao;

    @Inject
    public FacturaCabeceraLogic(IFacturaCabeceraDao facturaCabeceraDao) {
        this.facturaCabeceraDao = facturaCabeceraDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFacturaCabecera(FacturaCabecera entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.facturaCabeceraDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateFacturaCabecera(FacturaCabecera entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.facturaCabeceraDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FacturaCabecera getFacturaCabecera(Integer id) throws EntityException {
        FacturaCabecera entity;
        try {
            entity = this.facturaCabeceraDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaCabecera> getDataFacturaCabecera() throws EntityException {
        List<FacturaCabecera> list;
        try {
            list = this.facturaCabeceraDao.findAll();
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
                                                Object[] variablesBetweenDates) throws EntityException {
        List<FacturaCabecera> list;
        String where;
        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = this.facturaCabeceraDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaCabecera> findByProperty(String propertyName, Object value) throws EntityException {
        List<FacturaCabecera> list;
        try {
            list = this.facturaCabeceraDao.findByProperty(propertyName, value);
        } catch (DaoException de) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findMaxObjectByCriteria(String propertyName) throws EntityException {
        Object object;
        try {
            object = this.facturaCabeceraDao.maxByCriteria(propertyName);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findMinObjectByCriteria(String propertyName) throws EntityException {
        Object object;
        try {
            object = this.facturaCabeceraDao.minByCriteria(propertyName);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return object;
    }

    private void checkFields(FacturaCabecera entity) {
        if (entity.getValorNeto() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_VALOR_NETO);
        }

        if ((entity.getValorNeto() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorNeto().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_VALOR_NETO);
        }

        if (entity.getValorIva() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_VALOR_IVA);
        }

        if ((entity.getValorIva() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorIva().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_VALOR_IVA);
        }

        if (entity.getValorTotal() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_VALOR_TOTAL);
        }

        if ((entity.getValorTotal() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorTotal().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_VALOR_TOTAL);
        }

        if (entity.getUsuarioCreacion() == null) {
            throw new EntityException.EmptyFieldException(Constant.FIELD_USUARIO_CREACION);
        }

        if ((entity.getUsuarioCreacion() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getUsuarioCreacion(), 20))) {
            throw new EntityException.NotValidFormatException(Constant.FIELD_USUARIO_CREACION);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "FacturaCabecera";
        private static final String FIELD_VALOR_NETO = "valorNeto";
        private static final String FIELD_VALOR_IVA = "valorIva";
        private static final String FIELD_VALOR_TOTAL = "valorTotal";
        private static final String FIELD_USUARIO_CREACION = "usuarioCreacion";
    }
}
