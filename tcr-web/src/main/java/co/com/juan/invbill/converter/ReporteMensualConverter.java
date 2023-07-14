package co.com.juan.invbill.converter;

import co.com.juan.invbill.enums.MonthlyReportEnum;

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
public class ReporteMensualConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        Optional<MonthlyReportEnum> reporteMensual = Arrays.stream(MonthlyReportEnum.values())
                .filter(monthlyReportEnum -> monthlyReportEnum.getReport().equalsIgnoreCase(value))
                .findFirst();
        return reporteMensual.isPresent() ? reporteMensual.get() : "";
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? ((MonthlyReportEnum) o).getReport() : null;
    }

}
