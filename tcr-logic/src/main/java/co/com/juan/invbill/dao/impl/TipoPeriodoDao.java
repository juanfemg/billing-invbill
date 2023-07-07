package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ITipoPeriodoDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.TipoPeriodo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("TipoPeriodoDao")
public class TipoPeriodoDao extends HibernateDaoImpl<TipoPeriodo, Integer> implements ITipoPeriodoDao {

}
