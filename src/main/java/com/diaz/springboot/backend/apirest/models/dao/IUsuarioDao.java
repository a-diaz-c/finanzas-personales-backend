package com.diaz.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.diaz.springboot.backend.apirest.models.entity.Dia;
import com.diaz.springboot.backend.apirest.models.entity.RelDiaGasto;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	public Usuario findByEmail(String email);
	
}
