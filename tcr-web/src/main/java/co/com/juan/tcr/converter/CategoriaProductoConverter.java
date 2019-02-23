package co.com.juan.tcr.converter;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.tcr.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.tcr.model.CategoriaProducto;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "categoriaProductoConverter")
@RequestScoped
public class CategoriaProductoConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(CategoriaProductoConverter.class);

	@ManagedProperty(value = "#{BusinessDelegate}")
	private IBusinessDelegate businessDelegate;

	private CategoriaProducto categoriaProducto;

	/**
	 * Gets the as object.
	 * 
	 * @param ctx       the ctx
	 * @param component the component
	 * @param value     the value
	 * @return the as object
	 */
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value == null)
			return "";

		try {
			List<CategoriaProducto> categoriasProducto = businessDelegate.getCategoriasProducto();

			for (CategoriaProducto categoriaProductoTemp : categoriasProducto) {
				if (value.equalsIgnoreCase(String.valueOf(categoriaProductoTemp.getCategoria()))) {
					categoriaProducto = categoriaProductoTemp;
					break;
				}
			}
		} catch (Exception e) {
			log.error(
					"== Categoria Producto Converter: Fallo al retornar el objeto del valor {}. Se ha producido un error: {}",
					value, e.getMessage());
		}

		return categoriaProducto;
	}

	/**
	 * Gets the as string.
	 * 
	 * @param fc  the fc
	 * @param uic the uic
	 * @param o   the o
	 * @return the as string
	 */
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null)
			return null;

		categoriaProducto = (CategoriaProducto) o;
		return categoriaProducto.getCategoria();
	}

	/**
	 * @return the categoriaProducto
	 */
	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	/**
	 * @param categoriaProducto the categoriaProducto to set
	 */
	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	/**
	 * @return the businessDelegate
	 */
	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	/**
	 * @param businessDelegate the businessDelegate to set
	 */
	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

}
