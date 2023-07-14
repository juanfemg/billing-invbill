package co.com.juan.invbill.delegate.businessdelegate.impl;

import co.com.juan.invbill.control.ICategoriaProductoLogic;
import co.com.juan.invbill.control.IProductoLogic;
import co.com.juan.invbill.control.IStockProductoLogic;
import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.CategoriaProducto;
import co.com.juan.invbill.model.Producto;
import co.com.juan.invbill.model.StockProducto;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Component
public class ProductoDelegate implements IProductoDelegate {

    private final ICategoriaProductoLogic categoriaProductoLogic;
    private final IProductoLogic productoLogic;
    private final IStockProductoLogic stockProductoLogic;

    @Inject
    public ProductoDelegate(ICategoriaProductoLogic categoriaProductoLogic, IProductoLogic productoLogic, IStockProductoLogic stockProductoLogic) {
        this.categoriaProductoLogic = categoriaProductoLogic;
        this.productoLogic = productoLogic;
        this.stockProductoLogic = stockProductoLogic;
    }

    @Override
    public void save(CategoriaProducto entity) throws EntityException {
        this.categoriaProductoLogic.saveCategoriaProducto(entity);
    }

    @Override
    public CategoriaProducto findCategoriaByID(Integer id) throws EntityException {
        return this.categoriaProductoLogic.getCategoriaProducto(id);
    }

    @Override
    public void update(CategoriaProducto entity) throws EntityException {
        this.categoriaProductoLogic.updateCategoriaProducto(entity);
    }

    @Override
    public List<CategoriaProducto> getCategoriasProductoSortByCategoria() throws EntityException {
        return this.categoriaProductoLogic.findPageCategoriaProducto("categoria", true);
    }

    @Override
    public void save(Producto entity) throws EntityException {
        this.productoLogic.saveProducto(entity);
    }

    @Override
    public Producto findProductoByID(Integer id) throws EntityException {
        return this.productoLogic.getProducto(id);
    }

    @Override
    public void update(Producto entity) throws EntityException {
        this.productoLogic.updateProducto(entity);
    }

    @Override
    public List<Producto> getProductos() throws EntityException {
        return this.productoLogic.getProducto();
    }

    @Override
    public void save(StockProducto entity) throws EntityException {
        this.stockProductoLogic.saveStockProducto(entity);
    }

    @Override
    public void update(StockProducto entity) throws EntityException {
        this.stockProductoLogic.updateStockProducto(entity);
    }

    @Override
    public List<StockProducto> getStockProductos() throws EntityException {
        return this.stockProductoLogic.getStockProducto();
    }

    @Override
    public StockProducto getStockProductoByProducto(Producto producto) throws EntityException {
        return this.stockProductoLogic.findObjectByProperty("producto", producto);
    }

    @Override
    public Object getMaximoStockProductoByPropertyName(String propertyName) throws EntityException {
        return this.stockProductoLogic.findMaxObjectByCriteria(propertyName);
    }

    @Override
    public List<Producto> getProductosByCategoriaProductoSortByProducto(CategoriaProducto categoriaProducto) throws EntityException {
        return this.productoLogic.findByPropertySort("categoriaProducto", categoriaProducto, "producto", true);
    }

    @Override
    public List<Producto> getProductosByCategoriasProducto(List<CategoriaProducto> categoriasProducto) throws EntityException {
        return this.productoLogic.findByProperty("categoriaProducto", categoriasProducto);
    }

}
