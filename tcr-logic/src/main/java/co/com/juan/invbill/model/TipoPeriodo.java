package co.com.juan.invbill.model;

import co.com.juan.invbill.enums.EstadoEnum;

// Generated 16/06/2019 09:33:47 PM by Hibernate Tools 4.0.1.Final

/**
 * TipoPeriodo generated by hbm2java
 */
public class TipoPeriodo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idPeriodo;
	private String periodo;
	private Integer numeroMeses;
	private EstadoEnum estado;

	public TipoPeriodo() {
	}

	public TipoPeriodo(Integer idPeriodo, String periodo, Integer numeroMeses, EstadoEnum estado) {
		this.idPeriodo = idPeriodo;
		this.periodo = periodo;
		this.numeroMeses = numeroMeses;
		this.estado = estado;
	}

	public Integer getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Integer getNumeroMeses() {
		return this.numeroMeses;
	}

	public void setNumeroMeses(Integer numeroMeses) {
		this.numeroMeses = numeroMeses;
	}

	public EstadoEnum getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPeriodo == null) ? 0 : idPeriodo.hashCode());
		result = prime * result + ((numeroMeses == null) ? 0 : numeroMeses.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoPeriodo other = (TipoPeriodo) obj;
		if (idPeriodo == null) {
			if (other.idPeriodo != null)
				return false;
		} else if (!idPeriodo.equals(other.idPeriodo))
			return false;
		if (numeroMeses == null) {
			if (other.numeroMeses != null)
				return false;
		} else if (!numeroMeses.equals(other.numeroMeses))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}

}
