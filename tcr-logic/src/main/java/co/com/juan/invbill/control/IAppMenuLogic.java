package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppMenu;

/**
 * @author Juan Felipe
 */
public interface IAppMenuLogic {

    List<AppMenu> getDataAppMenu() throws EntityException;

}
