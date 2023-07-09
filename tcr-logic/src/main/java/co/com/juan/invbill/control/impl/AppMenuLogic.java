package co.com.juan.invbill.control.impl;

import co.com.juan.invbill.control.IAppMenuLogic;
import co.com.juan.invbill.dao.IAppMenuDao;
import co.com.juan.invbill.dataaccess.api.DaoException;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Service
public class AppMenuLogic implements IAppMenuLogic {

    private static final Logger log = LoggerFactory.getLogger(AppMenuLogic.class);
    private final IAppMenuDao appMenuDao;

    @Inject
    public AppMenuLogic(IAppMenuDao appMenuDao) {
        this.appMenuDao = appMenuDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppMenu> getDataAppMenu() throws EntityException {
        List<AppMenu> list;
        try {
            list = this.appMenuDao.findAll();
        } catch (DaoException de) {
            log.error("get {} failed. An error has occurred: {}", Constant.ENTITY_NAME, de.getMessage());
            throw new EntityException.FindingException(Constant.ENTITY_NAME);
        }

        return list;
    }

    private static class Constant {

        private static final String ENTITY_NAME = "AppMenu";
    }

}
