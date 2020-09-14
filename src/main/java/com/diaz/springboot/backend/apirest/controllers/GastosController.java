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
import com.diaz.springboot.backend.apirest.models.entity.Gasto;
import com.diaz.springboot.backend.apirest.models.services.CategoriaModelsServiceImpl;
import com.diaz.springboot.backend.apirest.models.services.GastoModelsServiceImpl;

@RestController
@RequestMapping("/api")
public class GastosController {

	@Autowired
	private GastoModelsServiceImpl gastoService;
	
	@Autowired
	private CategoriaModelsServiceImpl categoriaService;
	
	@GetMapping("/gastos/{idCategoria}")
	private ResponseEntity<?> index(@PathVariable(value = "idCategoria") Long idCategoria){
		
		Categoria categoria = null;
		List<Gasto> gastos = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			categoria = categoriaService.findById(idCategoria);
			
			if(categoria == null) {
				response.put("respuesta", false);
				response.put("mensaje", "La categoría ID: ".concat(idCategoria.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			gastos = gastoService.findByCategoria(categoria);
			
			
		} catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta",true);
		response.put("mensaje", "Categorias consultadas");
		response.put("gastos", gastos);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/gastos/{idCategoria}")
	private ResponseEntity<?> create(@PathVariable(value = "idCategoria") Long idCategoria, 
									 @RequestBody Gasto gasto, BindingResult result){
		
		Gasto nuevoGasto = null;
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
			Categoria categoria = categoriaService.findById(idCategoria);
			
			if(categoria == null) {
				response.put("respuesta",false);
				response.put("respuesta", false);
				response.put("mensaje", "La categoría ID: ".concat(idCategoria.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			gasto.setCategoria(categoria);
			nuevoGasto = gastoService.save(gasto);
		} catch (DataAccessException e) {
			response.put("respuesta",false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta",true);
		response.put("mensaje", "El gasto se ha creado con éxito!");
		response.put("categoria", nuevoGasto);	
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/gastos/{id}")
	public ResponseEntity<?> update(@RequestBody Gasto subcategoria, @PathVariable Long id, BindingResult result){
		
		Gasto gastoActual = null;
		Gasto gastoActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map( err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		

		
		try {
			gastoActual = gastoService.findById(id);
			
			if(gastoActual == null) {
				response.put("respuesta",false);
				response.put("mensaje", "Error: La categoría ID: ".concat(id.toString().concat(" no existe")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			gastoActual.setNombre(subcategoria.getNombre());
			
			gastoActualizado = gastoService.save(gastoActual);
		} catch (DataAccessException e) {
			response.put("respuesta",false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("respuesta",true);
		response.put("mensaje", "El gasto se ha actulizado con éxito!");
		response.put("subcategoria", gastoActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/gastos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			gastoService.delete(id);
		} catch (DataAccessException e) {
			response.put("respuesta",false);
			response.put("mensaje", "Error al eliminar la categoría de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta",true);
		response.put("mensaje", "El gasto se ha eliminado");
		
		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
