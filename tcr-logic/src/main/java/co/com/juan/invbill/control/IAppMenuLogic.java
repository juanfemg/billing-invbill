package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.AppMenu;

/**
 * @author Juan Felipe
 */
public interface IAppMenuLogic {

    List<AppMenu> getDataAppMenu();

    List<AppMenu> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates);

    List<AppMenu> findByProperty(String propertyName, Object value);

}
