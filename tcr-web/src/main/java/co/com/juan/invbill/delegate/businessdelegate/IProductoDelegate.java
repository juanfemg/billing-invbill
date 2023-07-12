package co.com.juan.invbill.delegate.businessdelegate;

import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.StockProducto;

import java.util.List;

public interface IProductoDelegate {

    void save(Producto entity) throws EntityException;

    Producto findProductoByID(Integer id) throws EntityException;

    void update(Producto entity) throws EntityException;

    List<Producto> getProductos() throws EntityException;

    void save(CategoriaProducto entity) throws EntityException;

    CategoriaProducto findCategoriaByID(Integer id) throws EntityException;

    void update(CategoriaProducto entity) throws EntityException;

    List<CategoriaProducto> getCategoriasProductoSortByCategoria() throws EntityException;

    void save(StockProducto entity) throws EntityException;

    void update(StockProducto entity) throws EntityException;

    List<StockProducto> getStockProductos() throws EntityException;

    StockProducto getStockProductoByProducto(Producto producto) throws EntityException;

    Object getMaximoStockProductoByPropertyName(String propertyName) throws EntityException;

    List<Producto> getProductosByCategoriaProductoSortByProducto(CategoriaProducto categoriaProducto) throws EntityException;

    List<Producto> getProductosByCategoriasProducto(List<CategoriaProducto> categoriasProducto) throws EntityException;

}
