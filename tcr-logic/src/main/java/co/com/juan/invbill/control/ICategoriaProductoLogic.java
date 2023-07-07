package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.CategoriaProducto;

/**
 * @author Juan Felipe
 */
public interface ICategoriaProductoLogic {

    List<CategoriaProducto> getCategoriaProducto();

    void saveCategoriaProducto(CategoriaProducto entity);

    void updateCategoriaProducto(CategoriaProducto entity);

    CategoriaProducto getCategoriaProducto(Integer id);

    List<CategoriaProducto> findPageCategoriaProducto(String sortColumnName, boolean sortAscending);

    List<CategoriaProducto> findByCriteria(Object[] variables, Object[] variablesBetween,
                                           Object[] variablesBetweenDates);

    List<CategoriaProducto> findByProperty(String propertyName, Object value);

}
