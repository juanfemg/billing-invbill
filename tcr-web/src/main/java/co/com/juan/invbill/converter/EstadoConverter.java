package co.com.juan.invbill.converter;

import co.com.juan.invbill.enums.StatusEnum;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Juan Felipe
 */
@Named
@RequestScoped
public class EstadoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        Optional<StatusEnum> estado = Arrays.stream(StatusEnum.values())
                .filter(statusEnum -> statusEnum.getStatus().equalsIgnoreCase(value))
                .findFirst();
        return estado.isPresent() ? estado.get() : "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? ((StatusEnum) o).getStatus() : null;
    }

}
