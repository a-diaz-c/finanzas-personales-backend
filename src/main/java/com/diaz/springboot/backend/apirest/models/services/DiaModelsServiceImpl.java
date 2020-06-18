package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diaz.springboot.backend.apirest.models.dao.ICantidad;
import com.diaz.springboot.backend.apirest.models.dao.IDiaDao;
import com.diaz.springboot.backend.apirest.models.entity.Dia;
import com.diaz.springboot.backend.apirest.models.entity.RelDiaGasto;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

@Service
public class DiaModelsServiceImpl {
	
	@Autowired
	private IDiaDao modelDao;
	
	@Autowired
	private ICantidad candidadDao;
	
	
	@Transactional(readOnly = true)
	public List<Dia> findAll() {
		return (List<Dia>) modelDao.findAll();
	}
	
	@Transactional(readOnly = true)
	public Dia findById(Long id) {
		return modelDao.findById(id).orElse(null);
	}	
	
	public Dia save(Dia dia) {
		return modelDao.save(dia);
	}

	public void delete(Long id) {
		modelDao.deleteById(id);
	}
	
	public List<Dia> findDiaCantidad(Long dia){
		return modelDao.findDiaCantidad(dia);
	}	

	public List<Dia> findByUsuario(Usuario usuario) {
		return modelDao.findByUsuario(usuario);
	}
	
	@Transactional(readOnly = true)
	public List<RelDiaGasto> findCantidades(Dia dia){
		return modelDao.findCantidades(dia);
	}
	
	public RelDiaGasto saveCantidad(RelDiaGasto cantidad) {
		return candidadDao.save(cantidad);
	}
	
	public void deleteCantidades(Dia dia) {
		modelDao.deleteCantidades(dia);
	}
	
}
