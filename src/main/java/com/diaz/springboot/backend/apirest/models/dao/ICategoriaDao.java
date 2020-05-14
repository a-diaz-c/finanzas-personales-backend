package com.diaz.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.diaz.springboot.backend.apirest.models.entity.Categoria;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;


public interface ICategoriaDao extends CrudRepository<Categoria, Long >{
	
	/*@Query("select u from Categoria u where u.usuario = :id_usuario")
	public List<Categoria> findByUsuario(@Param("id_usuario") Usuario usuario);*/

}
