package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.diaz.springboot.backend.apirest.models.entity.Usuario;

public class CategoriaModelsServiceImpl implements IModelsService{

	@Autowired
	private IModelsService modelDao;
	
	@Override
	public List<Usuario> findAll() {
		return modelDao.findAll();
	}

	@Override
	public Usuario findById(Long id) {
		return modelDao.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return modelDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
}
