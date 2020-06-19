package com.diaz.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.diaz.springboot.backend.apirest.models.services.UsuarioModelsServiceImpl;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class CategoriaRestController {
	
	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CategoriaModelsServiceImpl categoriaService;
	
	@Autowired
	private UsuarioModelsServiceImpl usuarioService;
	
	@GetMapping("/categorias/{idUsuario}")
	public ResponseEntity<?> index(@PathVariable (value = "idUsuario") Long idUsuario){
		
		Map<String, Object> response = new HashMap<>();		
		List<Categoria> categorias = null;
		Usuario usuario = null;
		log.info("Ruta Categorias del usuario");
		try {
			usuario = usuarioService.findById(idUsuario);
			
			categorias = categoriaService.findByUsuario(usuario);
		}catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(usuario == null) {
			response.put("respuesta", false);
			response.put("mensaje", "El usuario ID: ".concat(idUsuario.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		response.put("respuesta",true);
		response.put("mensaje", "Categorias consultadas");
		response.put("categorias", categorias);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/categorias/{idUsuario}")
	public ResponseEntity<?> create(@PathVariable (value = "idUsuario") long idUsuario, @RequestBody Categoria categoria, BindingResult result){
		
		Categoria nuevaCategoria = null;
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("respuesta",false);
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			usuario = usuarioService.findById(idUsuario);
			categoria.setUsuario(usuario);
			nuevaCategoria = categoriaService.save(categoria);
		}catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta",true);
		response.put("mensaje", "La categoría se ha creado con éxito!");
		response.put("categoria", nuevaCategoria);	
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
	}
	
	@PutMapping("/categorias/{idcategoria}")
	public ResponseEntity<?> update(@RequestBody Categoria categoria, @PathVariable Long idcategoria, BindingResult result) {
		
		Categoria categoriaActual = null;
		Categoria categoriaActulizada = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map( err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("respuesta", false);
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}		
		
		try {
			categoriaActual = categoriaService.findById(idcategoria);
			
			if(categoriaActual == null) {
				response.put("respuesta", false);
				response.put("mensaje", "Error: La categoría ID: ".concat(idcategoria.toString().concat(" no existe")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			categoriaActual.setNombre(categoria.getNombre());
			
			categoriaActulizada = categoriaService.save(categoriaActual);
		}catch(DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("respuesta",true);
		response.put("mensaje", "La categoría se ha actualizado con éxito!");
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
		
		response.put("respuesta",true);
		response.put("mensaje", "La categoría se ha eliminado");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}


}
