package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;

/**
 * @author Juan Felipe
 */
public interface ICategoriaProductoLogic {

    List<CategoriaProducto> getCategoriaProducto() throws EntityException;

    void saveCategoriaProducto(CategoriaProducto entity) throws EntityException;

    void updateCategoriaProducto(CategoriaProducto entity) throws EntityException;

    CategoriaProducto getCategoriaProducto(Integer id) throws EntityException;

    List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending) throws EntityException;

    List<CategoriaProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                           Object[] variablesBetweenDates) throws EntityException;

    List<CategoriaProducto> findByProperty(String propertyName, Object value) throws EntityException;

}
