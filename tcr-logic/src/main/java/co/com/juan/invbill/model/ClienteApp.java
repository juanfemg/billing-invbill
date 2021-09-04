package co.com.juan.invbill.model;
// Generated 8/07/2017 10:57:33 AM by Hibernate Tools 4.0.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import co.com.juan.invbill.enums.EstadoEnum;

/**
 * ClienteApp generated by hbm2java
 */
public class ClienteApp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idClienteApp;
	private Integer codVerificacion;
	private String razonSocial;
	private EstadoEnum estado;
	private Date fechaCreacion;
	private transient Set<Object> facturaCabeceras = new HashSet<>(0);

	public ClienteApp() {
	}

	public ClienteApp(Integer idClienteApp, Integer codVerificacion, String razonSocial, EstadoEnum estado) {
		this.idClienteApp = idClienteApp;
		this.codVerificacion = codVerificacion;
		this.razonSocial = razonSocial;
		this.estado = estado;
	}

	public Integer getIdClienteApp() {
		return this.idClienteApp;
	}

	public void setIdClienteApp(Integer idClienteApp) {
		this.idClienteApp = idClienteApp;
	}

	public Integer getCodVerificacion() {
		return this.codVerificacion;
	}

	public void setCodVerificacion(Integer codVerificacion) {
		this.codVerificacion = codVerificacion;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public EstadoEnum getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Set<Object> getFacturaCabeceras() {
		return facturaCabeceras;
	}

	public void setFacturaCabeceras(Set<Object> facturaCabeceras) {
		this.facturaCabeceras = facturaCabeceras;
	}

}