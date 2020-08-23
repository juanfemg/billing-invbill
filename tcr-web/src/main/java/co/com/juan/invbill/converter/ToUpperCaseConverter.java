package co.com.juan.invbill.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Juan Felipe
 * 
 */
@FacesConverter("toUpperCaseConverter")
public class ToUpperCaseConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		return (value != null) ? value.toUpperCase() : null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		return (o != null) ? o.toString() : "";
	}

}
