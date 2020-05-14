package com.diaz.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
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
import com.diaz.springboot.backend.apirest.models.services.UsuarioModelsServiceImpl;

@RestController
@RequestMapping("/api")
public class CategoriaRestController {
	
	@Autowired
	private CategoriaModelsServiceImpl categoriaService;
	
	@Autowired
	private UsuarioModelsServiceImpl usuarioService;
	
	@GetMapping("/categorias")
	public ResponseEntity<?> index(){
		Map<String, Object> response = new HashMap<>();
		List<Categoria> categorias = null;
		try {
			categorias = categoriaService.findAll();
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Categorias consultadas");
		response.put("categorias", categorias);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/categorias/{id}")
	public ResponseEntity<?> create(@PathVariable (value = "id") long idUsuario, @RequestBody Categoria categoria, BindingResult result){
		
		Categoria nuevaCategoria = null;
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = usuarioService.findById(idUsuario);
		
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
			System.out.println("SE CREO LA CATEGORIA");
			usuario.addCategoria(nuevaCategoria);
			usuarioService.save(usuario);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La categoría se ha creado con éxito!");
		response.put("categoria", nuevaCategoria);	
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<?> update(@RequestBody Categoria categoria, @PathVariable Long id, BindingResult result) {
		
		Categoria categoriaActual = categoriaService.findById(id);
		Categoria categoriaActulizada = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map( err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(categoriaActual == null) {
			response.put("mensaje", "Error: La categoría ID: ".concat(id.toString().concat(" no existe")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			categoriaActual.setNombre(categoria.getNombre());
			categoriaActual.setNombre(categoria.getTipo());
			
			categoriaActulizada = categoriaService.save(categoriaActulizada);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario se ha creado con éxito!");
		response.put("cliente", categoriaActulizada);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			categoriaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la categoría de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La categoría se ha eliminado");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}


}
