package com.diaz.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dias_sub_categorias")
public class DiaSubCategoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "fk_id_sub_categoria", nullable = false)
	@ManyToOne
	private SubCategoria subCategoria;
	
	@JoinColumn(name = "fk_id_dia", nullable = false)
	@ManyToOne
	private Dia dia;
	
	private BigDecimal cantidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubCategoria getIdSubCategoria() {
		return subCategoria;
	}

	public void setIdSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Dia getIdDia() {
		return dia;
	}

	public void setIdDia(Dia dia) {
		this.dia = dia;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
