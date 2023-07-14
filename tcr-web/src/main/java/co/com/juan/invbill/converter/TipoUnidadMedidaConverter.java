package co.com.juan.invbill.converter;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.model.TipoUnidadMedida;
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
public class TipoUnidadMedidaConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(TipoUnidadMedidaConverter.class);
    private final IConfigDelegate configDelegate;

    @Inject
    public TipoUnidadMedidaConverter(IConfigDelegate configDelegate) {
        this.configDelegate = configDelegate;
    }

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        try {
            return this.configDelegate.findTipoUnidadMedidaByID(Integer.parseInt(value));
        } catch (Exception e) {
            log.error("An exception has occurred finding a tipoUnidadMedida with id {}. Error: {}", value, e.getMessage());
        }

        return "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? String.valueOf(((TipoUnidadMedida) o).getIdUnidadMedida()) : null;
    }

}
