package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diaz.springboot.backend.apirest.models.dao.ISubCategoriaDao;
import com.diaz.springboot.backend.apirest.models.entity.SubCategoria;

@Service
public class SubCategoriaModelsServiceImpl {
	
	@Autowired
	private ISubCategoriaDao modelDao;
	
	public List<SubCategoria> finadAll(){
		return (List<SubCategoria>) modelDao.findAll();
	}
	
	public SubCategoria findById(Long id) {
		return modelDao.findById(id).orElse(null);
	}
	
	public SubCategoria save(SubCategoria sub) {
		return modelDao.save(sub);
	}
	
	public void delete(Long id) {
		modelDao.deleteById(id);
	}
	
	

}
