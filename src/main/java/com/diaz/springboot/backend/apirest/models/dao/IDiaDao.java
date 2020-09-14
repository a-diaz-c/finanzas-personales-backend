package com.diaz.springboot.backend.apirest.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.diaz.springboot.backend.apirest.models.entity.Dia;
import com.diaz.springboot.backend.apirest.models.entity.RelDiaGasto;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

public interface IDiaDao extends CrudRepository<Dia, Long>{
	
	@Query("SELECT d, r FROM Dia d INNER JOIN RelDiaGasto r ON d.idDia = r.id_dia")
	List<Dia> findDiaCantidad(Long saldo_inicial);
	
	@Query("SELECT d FROM RelDiaGasto d WHERE d.id_dia = ?1")
	List<RelDiaGasto> findCantidades(Dia dia);
	
	@Query("select u from Dia u where u.usuario = :id_usuario")
	List<Dia> findByUsuario(@Param("id_usuario") Usuario usuario);
	
	@Query("select u from Dia u where u.fecha = :fecha and u.usuario = :usuario")
	Dia findByFecha(@Param("fecha") Date date, @Param("usuario") Usuario usuario);
	
	@Transactional
	@Modifying
	@Query("delete from RelDiaGasto u where u.id_dia = :dia")
	void deleteCantidades(@Param("dia") Dia dia);
	
	
	
	
}
