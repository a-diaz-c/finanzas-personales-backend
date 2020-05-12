package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import com.diaz.springboot.backend.apirest.models.entity.Usuario;

public interface IModelsService {
	
	public List<Usuario> findAll();
	
	public Usuario findById(Long id);
	
	public Usuario save(Usuario usuario);
	
	public void delete(Long id);
}
