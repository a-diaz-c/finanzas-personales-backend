package com.diaz.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.diaz.springboot.backend.apirest.models.entity.Categoria;
import com.diaz.springboot.backend.apirest.models.entity.Gasto;

public interface IGastoDao extends CrudRepository<Gasto, Long> {

	@Query("select u from Gasto u where u.categoria = :id_categoria")
	public List<Gasto> findByCategoria(@Param("id_categoria") Categoria categoria);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Gasto u WHERE u.categoria = ?1")
	public void deleteByCategoria(Categoria categoria);
	
	
}
