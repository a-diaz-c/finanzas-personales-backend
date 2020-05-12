package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diaz.springboot.backend.apirest.models.dao.IModelsDao;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

@Service()
@Qualifier("usuarioModelsServiceImpl")
public class UsuarioModelsServiceImpl implements IModelsService{

	@Autowired
	private IModelsDao modelDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) modelDao.findAll();
		
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return modelDao.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return modelDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		modelDao.deleteById(id);
	}
}
