package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IDevolucionCabeceraLogic;
import co.com.juan.invbill.dao.IDevolucionCabeceraDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.DevolucionCabecera;
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
@Service("DevolucionCabeceraLogic")
public class DevolucionCabeceraLogic implements IDevolucionCabeceraLogic {

    private static final Logger log = LoggerFactory.getLogger(DevolucionCabeceraLogic.class);

    @Autowired
    private IDevolucionCabeceraDao devolucionCabeceraDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDevolucionCabecera(DevolucionCabecera entity) {
        log.debug("saving {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            if (getDevolucionCabecera(entity.getIdFactura()) != null) {
                throw new EntityException(EntityException.ENTITY_WITHSAMEKEY);
            }

            devolucionCabeceraDao.save(entity);

            log.debug("save {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDevolucionCabecera(DevolucionCabecera entity) {
        log.debug("updating {} instance", Constant.ENTITY_NAME);

        try {
            checkFields(entity);

            devolucionCabeceraDao.update(entity);

            log.debug("update {} successful", Constant.ENTITY_NAME);
        } catch (Exception e) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DevolucionCabecera getDevolucionCabecera(Integer id) {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        DevolucionCabecera entity;

        try {
            entity = devolucionCabeceraDao.findById(id);

        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevolucionCabecera> getDataDevolucionCabecera() {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        List<DevolucionCabecera> list;

        try {
            list = devolucionCabeceraDao.findAll();
        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevolucionCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
                                                   Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<DevolucionCabecera> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = devolucionCabeceraDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevolucionCabecera> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<DevolucionCabecera> list;

        try {
            list = devolucionCabeceraDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(DevolucionCabecera entity) {
        if (entity.getValorNeto() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_VALOR_NETO);
        }

        if ((entity.getValorNeto() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorNeto().toString(), 22, 2))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_VALOR_NETO);
        }

        if (entity.getValorIva() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_VALOR_IVA);
        }

        if ((entity.getValorIva() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorIva().toString(), 22, 2))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_VALOR_IVA);
        }

        if (entity.getValorTotal() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_VALOR_TOTAL);
        }

        if ((entity.getValorTotal() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorTotal().toString(), 11, 0))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_VALOR_TOTAL);
        }

        if (entity.getUsuarioCreacion() == null) {
            throw new EntityException().new EmptyFieldException(Constant.FIELD_USUARIO_CREACION);
        }

        if ((entity.getUsuarioCreacion() != null)
                && !(Utilities.checkWordAndCheckWithlength(entity.getUsuarioCreacion(), 20))) {
            throw new EntityException().new NotValidFormatException(Constant.FIELD_USUARIO_CREACION);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "DevolucionCabecera";
        private static final String FIELD_VALOR_NETO = "valorNeto";
        private static final String FIELD_VALOR_IVA = "valorIva";
        private static final String FIELD_VALOR_TOTAL = "valorTotal";
        private static final String FIELD_USUARIO_CREACION = "usuarioCreacion";
    }

}