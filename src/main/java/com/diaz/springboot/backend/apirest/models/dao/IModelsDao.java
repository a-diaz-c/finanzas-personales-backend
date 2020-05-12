package com.diaz.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.diaz.springboot.backend.apirest.models.entity.Usuario;

public interface IModelsDao extends CrudRepository<Usuario, Long>{

}
