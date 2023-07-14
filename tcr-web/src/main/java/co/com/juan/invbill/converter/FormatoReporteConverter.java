package co.com.juan.invbill.converter;

import co.com.juan.invbill.enums.ReportFormatEnum;

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
public class FormatoReporteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        Optional<ReportFormatEnum> formatoReporte = Arrays.stream(ReportFormatEnum.values())
                .filter(reportFormatEnum -> reportFormatEnum.getFormat().equalsIgnoreCase(value))
                .findFirst();
        return formatoReporte.isPresent() ? formatoReporte.get() : "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? ((ReportFormatEnum) o).getFormat() : null;
    }

}
