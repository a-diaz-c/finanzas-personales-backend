package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diaz.springboot.backend.apirest.models.dao.ICategoriaDao;
import com.diaz.springboot.backend.apirest.models.entity.Categoria;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

@Service
public class CategoriaModelsServiceImpl{

	@Autowired
	private ICategoriaDao modelDao;
	
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		return (List<Categoria>) modelDao.findAll();
	}

	@Transactional(readOnly = true)
	public Categoria findById(Long id) {
		return modelDao.findById(id).orElse(null);
	}

	public Categoria save(Categoria categoria) {
		return modelDao.save(categoria);
	}

	public void delete(Long id) {
		modelDao.deleteById(id);
	}
	
	public List<Categoria> findByUsuario(Usuario usuario){
		return modelDao.findByUsuario(usuario);
	}

	
	
}
