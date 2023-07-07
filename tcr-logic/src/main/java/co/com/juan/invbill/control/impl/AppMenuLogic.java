package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IAppMenuLogic;
import co.com.juan.invbill.dao.IAppMenuDao;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.util.SortMenu;
import co.com.juan.invbill.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Juan Felipe
 */

@Scope("singleton")
@Service("AppMenuLogic")
public class AppMenuLogic implements IAppMenuLogic {

    private static final Logger log = LoggerFactory.getLogger(AppMenuLogic.class);

    @Autowired
    private IAppMenuDao appMenuDao;

    @Override
    @Transactional(readOnly = true)
    public List<AppMenu> getDataAppMenu() {
        log.debug("getting {} instance", Constant.ENTITY_NAME);

        List<AppMenu> list;

        try {
            list = appMenuDao.findAll();
            list.sort(new SortMenu());
        } catch (Exception e) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppMenu> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates) {
        log.debug("getting {} instance by criteria", Constant.ENTITY_NAME);

        List<AppMenu> list;
        String where;

        try {
            where = Utilities.constructCriteria(variables, variablesBetween, variablesBetweenDates);
            list = appMenuDao.findByCriteria(where);
        } catch (Exception e) {
            log.error("get {} failed by criteria. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppMenu> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance", Constant.ENTITY_NAME);

        List<AppMenu> list;

        try {
            list = appMenuDao.findByProperty(propertyName, value);
        } catch (Exception e) {
            log.error("find {} failed. An error has occurred: {}", Constant.ENTITY_NAME, e.getMessage());
            throw new EntityException().new FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "AppMenu";
    }

}
