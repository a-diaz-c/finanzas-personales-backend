package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diaz.springboot.backend.apirest.models.dao.IGastoDao;
import com.diaz.springboot.backend.apirest.models.entity.Categoria;
import com.diaz.springboot.backend.apirest.models.entity.Gasto;

@Service
public class GastoModelsServiceImpl {
	
	@Autowired
	private IGastoDao modelDao;
	
	public List<Gasto> finadAll(){
		return (List<Gasto>) modelDao.findAll();
	}
	
	public Gasto findById(Long id) {
		return modelDao.findById(id).orElse(null);
	}
	
	public Gasto save(Gasto sub) {
		return modelDao.save(sub);
	}
	
	public void delete(Long id) {
		modelDao.deleteById(id);
	}

	public List<Gasto> findByCategoria(Categoria categoria) {
		return modelDao.findByCategoria(categoria);
	}
	
	public void deleteByCategoria(Categoria categoria) {
		modelDao.deleteByCategoria(categoria);
	}
	
	

}
