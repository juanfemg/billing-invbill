package co.com.juan.invbill.model;
// Generated 11-feb-2017 9:50:57 by Hibernate Tools 4.0.0

import java.util.Date;

import co.com.juan.invbill.enums.EstadoEnum;

/**
 * UsuarioApp generated by hbm2java
 */
public class UsuarioApp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String idUsuarioApp;
	private RolApp rolApp;
	private String nombre;
	private String password;
	private EstadoEnum estado;
	private Date fechaCreacion;

	public UsuarioApp() {
	}

	public UsuarioApp(String idUsuarioApp, RolApp rolApp, String nombre,
			String password, EstadoEnum estado, Date fechaCreacion) {
		this.idUsuarioApp = idUsuarioApp;
		this.rolApp = rolApp;
		this.nombre = nombre;
		this.password = password;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
	}

	public String getIdUsuarioApp() {
		return this.idUsuarioApp;
	}

	public void setIdUsuarioApp(String idUsuarioApp) {
		this.idUsuarioApp = idUsuarioApp;
	}

	public RolApp getRolApp() {
		return this.rolApp;
	}

	public void setRolApp(RolApp rolApp) {
		this.rolApp = rolApp;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
