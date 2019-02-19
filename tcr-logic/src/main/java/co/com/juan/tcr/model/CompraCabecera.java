package co.com.juan.tcr.model;
// Generated 10/07/2017 10:38:42 PM by Hibernate Tools 4.0.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CompraCabecera generated by hbm2java
 */
public class CompraCabecera implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private CompraCabeceraId id;
	private ProveedorApp proveedorApp;
	private Double valorNeto;
	private Double valorIva;
	private Integer valorTotal;
	private String usuarioCreacion;
	private Date fechaCreacion;
	private transient Set<Object> compraDetalles = new HashSet<>(0);

	public CompraCabecera() {
	}

	public CompraCabecera(CompraCabeceraId id, ProveedorApp proveedorApp, Double valorNeto, Double valorIva,
			Integer valorTotal, String usuarioCreacion, Date fechaCreacion) {
		this.id = id;
		this.proveedorApp = proveedorApp;
		this.valorNeto = valorNeto;
		this.valorIva = valorIva;
		this.valorTotal = valorTotal;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaCreacion = fechaCreacion;
	}

	public CompraCabeceraId getId() {
		return this.id;
	}

	public void setId(CompraCabeceraId id) {
		this.id = id;
	}

	public Double getValorNeto() {
		return this.valorNeto;
	}

	public void setValorNeto(Double valorNeto) {
		this.valorNeto = valorNeto;
	}

	public Double getValorIva() {
		return this.valorIva;
	}

	public void setValorIva(Double valorIva) {
		this.valorIva = valorIva;
	}

	public Integer getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(Integer valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Set<Object> getCompraDetalles() {
		return this.compraDetalles;
	}

	public void setCompraDetalles(Set<Object> compraDetalles) {
		this.compraDetalles = compraDetalles;
	}

	public ProveedorApp getProveedorApp() {
		return proveedorApp;
	}

	public void setProveedorApp(ProveedorApp proveedorApp) {
		this.proveedorApp = proveedorApp;
	}

}