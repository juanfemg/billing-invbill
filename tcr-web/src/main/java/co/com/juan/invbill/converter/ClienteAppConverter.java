package co.com.juan.invbill.converter;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.ClienteApp;

/**
 * @author Juan Felipe
 */
@Named
@RequestScoped
public class ClienteAppConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(ClienteAppConverter.class);
    private final IClienteDelegate clienteDelegate;

    @Inject
    public ClienteAppConverter(IClienteDelegate clienteDelegate) {
        this.clienteDelegate = clienteDelegate;
    }

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        try {
            return this.clienteDelegate.findClienteByID(Integer.parseInt(value));
        } catch (Exception e) {
            log.error("An exception has occurred finding a cliente with id {}. Error: {}", value, e.getMessage());
        }

        return "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? String.valueOf(((ClienteApp) o).getIdClienteApp()) : null;
    }

}
