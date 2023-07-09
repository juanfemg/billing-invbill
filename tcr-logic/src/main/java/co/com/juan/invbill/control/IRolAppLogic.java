package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.RolApp;

/**
 * @author Juan Felipe
 */
public interface IRolAppLogic {

    List<RolApp> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates) throws EntityException;

    List<RolApp> findByProperty(String propertyName, Object value) throws EntityException;

}
