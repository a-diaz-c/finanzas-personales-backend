package com.diaz.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.diaz.springboot.backend.apirest.models.entity.RelDiaGasto;

public interface ICantidad extends CrudRepository<RelDiaGasto, Long>{

}
