package co.com.juan.tcr.model;
// Generated 20/10/2017 10:41:54 AM by Hibernate Tools 4.0.1.Final

import java.util.Date;

/**
 * AppConfig generated by hbm2java
 */
public class AppConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String idAppConfig;
	private String valor;
	private String descripcion;
	private Date fechaCreacion;

	public AppConfig() {
	}

	public AppConfig(String idAppConfig) {
		this.idAppConfig = idAppConfig;
	}

	public AppConfig(String idAppConfig, String valor, String descripcion, Date fechaCreacion) {
		this.idAppConfig = idAppConfig;
		this.valor = valor;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
	}

	public String getIdAppConfig() {
		return this.idAppConfig;
	}

	public void setIdAppConfig(String idAppConfig) {
		this.idAppConfig = idAppConfig;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
