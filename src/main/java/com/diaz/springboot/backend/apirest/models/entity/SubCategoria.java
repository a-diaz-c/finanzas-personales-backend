package com.diaz.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sub_categorias")
public class SubCategoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sub_categoria")
	private Long idSubCategoria;
	private String nombre;
	
	@JoinColumn(name = "fk_id_categoria", nullable = false)
	@ManyToOne
	private Categoria categoria;

	public Long getIdSubCategori() {
		return idSubCategoria;
	}

	public void setIdSubCategori(Long idSubCategori) {
		this.idSubCategoria = idSubCategori;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getIdCategoria() {
		return categoria;
	}

	public void setIdCategoria(Categoria idCategoria) {
		this.categoria = idCategoria;
	}

}
