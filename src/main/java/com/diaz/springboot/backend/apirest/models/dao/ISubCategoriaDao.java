package com.diaz.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.diaz.springboot.backend.apirest.models.entity.SubCategoria;

public interface ISubCategoriaDao extends CrudRepository<SubCategoria, Long> {

}
