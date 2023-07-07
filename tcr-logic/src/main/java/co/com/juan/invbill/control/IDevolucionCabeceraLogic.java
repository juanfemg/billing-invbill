package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.DevolucionCabecera;

/**
 * @author Juan Felipe
 */
public interface IDevolucionCabeceraLogic {

    void saveDevolucionCabecera(DevolucionCabecera entity);

    void updateDevolucionCabecera(DevolucionCabecera entity);

    DevolucionCabecera getDevolucionCabecera(Integer id);

    List<DevolucionCabecera> getDataDevolucionCabecera();

    List<DevolucionCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
                                            Object[] variablesBetweenDates);

    List<DevolucionCabecera> findByProperty(String propertyName, Object value);

}
