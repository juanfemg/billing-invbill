package co.com.juan.invbill.dao.impl;

import co.com.juan.invbill.dao.ICategoriaProductoDao;
import co.com.juan.invbill.dataaccess.api.HibernateDaoImpl;
import co.com.juan.invbill.model.CategoriaProducto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Felipe
 */
@Scope("singleton")
@Repository("CategoriaProductoDao")
public class CategoriaProductoDao extends
        HibernateDaoImpl<CategoriaProducto, Integer> implements
        ICategoriaProductoDao {

}
