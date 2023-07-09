package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.TipoPeriodo;

/**
 * @author Juan Felipe
 */
public interface ITipoPeriodoLogic {

    List<TipoPeriodo> getTipoPeriodo() throws EntityException;

    void saveTipoPeriodo(TipoPeriodo entity) throws EntityException;

    void updateTipoPeriodo(TipoPeriodo entity) throws EntityException;

    TipoPeriodo getTipoPeriodo(Integer id) throws EntityException;

    List<TipoPeriodo> findByCriteria(Object[] variables, Object[] variablesBetween,
                                     Object[] variablesBetweenDates) throws EntityException;

    List<TipoPeriodo> findByProperty(String propertyName, Object value) throws EntityException;

}
