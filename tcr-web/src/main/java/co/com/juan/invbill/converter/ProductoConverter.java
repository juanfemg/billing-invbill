package co.com.juan.invbill.converter;

import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.model.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Juan Felipe
 */
@Named
@RequestScoped
public class ProductoConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(ProductoConverter.class);
    private final IProductoDelegate productoDelegate;

    @Inject
    public ProductoConverter(IProductoDelegate productoDelegate) {
        this.productoDelegate = productoDelegate;
    }

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        try {
            return this.productoDelegate.findProductoByID(Integer.parseInt(value));
        } catch (Exception e) {
            log.error("An exception has occurred finding a producto with id {}. Error: {}", value, e.getMessage());
        }

        return "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? String.valueOf(((Producto) o).getIdProducto()) : null;
    }

}
