package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.TipoPeriodo;

/**
 * @author Juan Felipe
 */
public interface ITipoPeriodoLogic {

    List<TipoPeriodo> getTipoPeriodo();

    void saveTipoPeriodo(TipoPeriodo entity);

    void updateTipoPeriodo(TipoPeriodo entity);

    TipoPeriodo getTipoPeriodo(Integer id);

    List<TipoPeriodo> findByCriteria(Object[] variables, Object[] variablesBetween,
                                     Object[] variablesBetweenDates);

    List<TipoPeriodo> findByProperty(String propertyName, Object value);

}
