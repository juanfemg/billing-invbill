package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ICompraCabeceraDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("CompraCabeceraDao")
public class CompraCabeceraDao extends HibernateDaoImpl<CompraCabecera, CompraCabeceraId>
        implements ICompraCabeceraDao {

}
