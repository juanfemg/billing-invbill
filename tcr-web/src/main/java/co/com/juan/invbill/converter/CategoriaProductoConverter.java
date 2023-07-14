package co.com.juan.invbill.converter;

import co.com.juan.invbill.delegate.businessdelegate.IProductoDelegate;
import co.com.juan.invbill.model.CategoriaProducto;
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
public class CategoriaProductoConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(CategoriaProductoConverter.class);
    private final IProductoDelegate productoDelegate;

    @Inject
    public CategoriaProductoConverter(IProductoDelegate productoDelegate) {
        this.productoDelegate = productoDelegate;
    }

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        try {
            return this.productoDelegate.findCategoriaByID(Integer.parseInt(value));
        } catch (Exception e) {
            log.error("An exception has occurred finding a categoria with id {}. Error: {}", value, e.getMessage());
        }

        return "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? String.valueOf(((CategoriaProducto) o).getIdCategoria()) : null;
    }

}
