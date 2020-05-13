package com.diaz.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diaz.springboot.backend.apirest.models.entity.Categoria;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;
import com.diaz.springboot.backend.apirest.models.services.CategoriaModelsServiceImpl;
import com.diaz.springboot.backend.apirest.models.services.IModelsService;

@RestController
@RequestMapping("/api")
public class CategoriaRestController {
	
	@Autowired
	private CategoriaModelsServiceImpl categoriaService;
	
	@Autowired
	private IModelsService usuarioService;
	
	@GetMapping("/categorias")
	public List<Categoria> index(){
		return categoriaService.findAll();
	}
	
	@PostMapping("/categorias/{id}")
	public ResponseEntity<?> create(@PathVariable (value = "id") long idUsuario, @RequestBody Categoria categoria, BindingResult result){
		
		Categoria nuevaCategoria = null;
		Usuario user = usuarioService.findById(idUsuario);
		categoria.setId_usuario(user);
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			nuevaCategoria = categoriaService.save(categoria);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El Usuario se ha creado con éxito!");
		response.put("categoria", nuevaCategoria);	
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
	}
	
	@PutMapping("/categorias/{id}")
	public Categoria update(Categoria categoria, @PathVariable Long id) {
		
		Categoria categoriaActual = categoriaService.findById(id);
		
		categoriaActual.setNombre(categoria.getNombre());
		categoriaActual.setTipo(categoria.getTipo());

		return categoriaService.save(categoriaActual);
	}
	
	
	@DeleteMapping("/categorias/{id}")
	public void delete(@PathVariable Long id) {
		categoriaService.delete(id);
	}
	
	
	@GetMapping("categorias/{id}/usuario")
	public List<Categoria> findByUsuario(@PathVariable Long id) {
		Usuario usuario = usuarioService.findById(id);
		return categoriaService.findByUsuario(usuario);
	}

}
