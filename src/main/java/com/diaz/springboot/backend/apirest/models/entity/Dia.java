package com.diaz.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dias")
public class Dia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dia")
	private Long idDia;
	
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name = "saldo_inicial")
	private BigDecimal saldoInicial;
	@Column(name = "saldo_final")
	private BigDecimal saldoFinal;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_usuario")
	private Usuario usuario;
	
	/*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "rel_dia_gasto",
			   joinColumns = @JoinColumn(name = "FK_dia", nullable = false),
			   inverseJoinColumns = @JoinColumn(name="FK_gasto", nullable = false))*/
	@OneToMany(fetch = FetchType.LAZY)
	private List<RelDiaGasto> movimientos;
	
	public Dia() {
		this.movimientos = new ArrayList<>();
	}
	
	public Long getIdDia() {
		return idDia;
	}

	public void setIdDia(Long idDia) {
		this.idDia = idDia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public List<RelDiaGasto> getCantidades() {
		return movimientos;
	}

	public void setCantidades(List<RelDiaGasto> relDiaGasto) {
		this.movimientos = relDiaGasto;
	}
	
	public void addCantidades(RelDiaGasto relDiaGasto) {
		this.movimientos.add(relDiaGasto);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
