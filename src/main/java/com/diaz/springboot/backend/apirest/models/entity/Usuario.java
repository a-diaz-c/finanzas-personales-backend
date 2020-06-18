package com.diaz.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

	private String nombre;
	private String apellido;
	private String usuario;
	private String email;
	@JsonIgnore
	private String password;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Categoria> categoria;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Dia> dias;
	
	public Usuario() {
		this.categoria = new ArrayList<Categoria>();
		this.dias = new ArrayList<Dia>();
	}
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId_usuario() {
		return idUsuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.idUsuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Categoria> getCategorias() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	
	public void addCategoria(Categoria categoria) {
		this.categoria.add(categoria);
	}

	public List<Dia> getDias() {
		return dias;
	}

	public void setDias(List<Dia> dias) {
		this.dias = dias;
	}
	
	public void addDias(Dia dia) {
		this.dias.add(dia);
	}

}
