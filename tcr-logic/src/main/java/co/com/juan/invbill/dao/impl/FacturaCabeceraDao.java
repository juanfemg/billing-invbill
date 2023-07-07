package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.IFacturaCabeceraDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.FacturaCabecera;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("FacturaCabeceraDao")
public class FacturaCabeceraDao extends HibernateDaoImpl<FacturaCabecera, Integer> implements IFacturaCabeceraDao {

}
