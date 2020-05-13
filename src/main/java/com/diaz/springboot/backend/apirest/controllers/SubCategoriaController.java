package com.diaz.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diaz.springboot.backend.apirest.models.entity.SubCategoria;
import com.diaz.springboot.backend.apirest.models.services.SubCategoriaModelsServiceImpl;

@RestController
@RequestMapping("/api")
public class SubCategoriaController {

	@Autowired
	private SubCategoriaModelsServiceImpl subCategoriaService;
	
	@GetMapping("/subcategorias")
	private List<SubCategoria> index(){
		return subCategoriaService.finadAll();
	}
}
