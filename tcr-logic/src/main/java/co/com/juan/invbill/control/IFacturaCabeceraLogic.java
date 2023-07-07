package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.FacturaCabecera;

/**
 * @author Juan Felipe
 */
public interface IFacturaCabeceraLogic {

    List<FacturaCabecera> getFacturaCabecera();

    void saveFacturaCabecera(FacturaCabecera entity);

    void updateFacturaCabecera(FacturaCabecera entity);

    FacturaCabecera getFacturaCabecera(Integer id);

    List<FacturaCabecera> getDataFacturaCabecera();

    List<FacturaCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
                                         Object[] variablesBetweenDates);

    List<FacturaCabecera> findByProperty(String propertyName, Object value);

    Object findMaxObjectByCriteria(String propertyName);

    Object findMinObjectByCriteria(String propertyName);

    Object findAvgObjectByCriteria(String propertyName);

}
