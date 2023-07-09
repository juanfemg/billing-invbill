package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;

/**
 * @author Juan Felipe
 */
public interface ICategoriaProductoLogic {

    void saveCategoriaProducto(CategoriaProducto entity) throws EntityException;

    void updateCategoriaProducto(CategoriaProducto entity) throws EntityException;

    CategoriaProducto getCategoriaProducto(Integer id) throws EntityException;

    List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending) throws EntityException;

}
