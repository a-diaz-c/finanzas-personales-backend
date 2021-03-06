package com.diaz.springboot.backend.apirest.models.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.diaz.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	public Usuario findByEmail(String email);
	
	@Query("DELETE FROM Usuario u WHERE u.email = ?1")
	public void delteByEmail(String email);
	
}
