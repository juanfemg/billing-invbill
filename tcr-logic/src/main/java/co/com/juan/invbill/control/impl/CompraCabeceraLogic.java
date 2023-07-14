package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.ICompraCabeceraLogic;
import co.com.juan.invbill.dao.ICompraCabeceraDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class CompraCabeceraLogic implements ICompraCabeceraLogic {

    private static final Logger log = LoggerFactory.getLogger(CompraCabeceraLogic.class);
    private final ICompraCabeceraDao compraCabeceraDao;

    @Inject
    public CompraCabeceraLogic(ICompraCabeceraDao compraCabeceraDao) {
        this.compraCabeceraDao = compraCabeceraDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCompraCabecera(CompraCabecera entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.compraCabeceraDao.save(entity);
        } catch (DaoException de) {
            log.error("save {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.SavingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCompraCabecera(CompraCabecera entity) throws EntityException {
        try {
            this.checkFields(entity);
            this.compraCabeceraDao.update(entity);
        } catch (DaoException de) {
            log.error("update {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.UpdatingException(Constant.ENTITY_NAME);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CompraCabecera getCompraCabecera(CompraCabeceraId id) throws EntityException {
        CompraCabecera entity;
        try {
            entity = this.compraCabeceraDao.findById(id);
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraCabecera> getDataCompraCabecera() throws EntityException {
        List<CompraCabecera> list;
        try {
            list = this.compraCabeceraDao.findAll();
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraCabecera> findByIdFacturaAndOrIdProveedor(String idFacturaCompra, Integer idProveedorApp) throws EntityException {
        List<CompraCabecera> list;
        try {
            List<Object> firstVariables = Utilities.constructVariablesCriteriaCondition("id.idFacturaCompra", idFacturaCompra);
            List<Object> secondVariables = Utilities.constructVariablesCriteriaCondition("id.idProveedorApp", idProveedorApp);
            String where = Utilities.constructCriteria(Stream.concat(firstVariables.stream(), secondVariables.stream()).toArray(), null, null);
            list = this.compraCabeceraDao.findByCriteria(where);
        } catch (DaoException de) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private void checkFields(CompraCabecera entity) {
        if (entity.getValorNeto() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_NETO);
        }

        if ((entity.getValorNeto() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorNeto().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_NETO);
        }

        if (entity.getValorIva() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_IVA);
        }

        if ((entity.getValorIva() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorIva().toString(), 22, 2))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_IVA);
        }

        if (entity.getValorTotal() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_TOTAL);
        }

        if ((entity.getValorTotal() != null)
                && !(Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getValorTotal().toString(), 11, 0))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_VALOR_TOTAL);
        }

        if (entity.getUsuarioCreacion() == null) {
            throw new EntityException.EmptyFieldException(Constant.ENTITY_NAME, Constant.FIELD_USUARIO_CREACION);
        }

        if ((entity.getUsuarioCreacion() != null)
                && !(Utilities.checkWordAndCheckWithLength(entity.getUsuarioCreacion(), 20))) {
            throw new EntityException.NotValidFormatException(Constant.ENTITY_NAME, Constant.FIELD_USUARIO_CREACION);
        }
    }

    private static class Constant {

        private static final String ENTITY_NAME = "CompraCabecera";
        private static final String FIELD_VALOR_NETO = "valorNeto";
        private static final String FIELD_VALOR_IVA = "valorIva";
        private static final String FIELD_VALOR_TOTAL = "valorTotal";
        private static final String FIELD_USUARIO_CREACION = "usuarioCreacion";
    }

}
