package com.diaz.springboot.backend.apirest.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.diaz.springboot.backend.apirest.models.entity.Dia;
import com.diaz.springboot.backend.apirest.models.entity.Gasto;
import com.diaz.springboot.backend.apirest.models.entity.RelDiaGasto;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;
import com.diaz.springboot.backend.apirest.models.services.DiaModelsServiceImpl;
import com.diaz.springboot.backend.apirest.models.services.GastoModelsServiceImpl;
import com.diaz.springboot.backend.apirest.models.services.UsuarioModelsServiceImpl;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class DiaRestController {
	
	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	@Autowired
	DiaModelsServiceImpl diaService;
	@Autowired
	GastoModelsServiceImpl gastoService;
	@Autowired
	private UsuarioModelsServiceImpl usuarioService;
	
	@GetMapping("/dias/{idUsuario}")
	public ResponseEntity<?> index(@PathVariable (value = "idUsuario") Long idUsuario){
		
		Map<String, Object> response = new HashMap<>();	
		Usuario usuario = null;
		List<Dia> dias = null;
		
		try {
			usuario = usuarioService.findById(idUsuario);
			
			if(usuario == null) {
				response.put("respuesta", false);
				response.put("mensaje", "El usuario ID: ".concat(idUsuario.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			dias = diaService.findByUsuario(usuario);
			
		} catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta",true);
		response.put("mensaje", "Dias consultados");
		response.put("dias", dias);
				
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("dias/cantidad/{idDia}")
	public ResponseEntity<?> cantidad(@PathVariable (value = "idDia") Long idDia){
		
		Map<String, Object> response = new HashMap<>();
		Dia dia = null;
		List<RelDiaGasto> cantidades = null;
		
		try {
			dia = diaService.findById(idDia);
			if(dia == null) {
				response.put("respuesta", false);
				response.put("mensaje", "El dia ID: ".concat(idDia.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			cantidades = diaService.findCantidades(dia);
		} catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta",true);
		response.put("mensaje", "Dias consultados");
		response.put("cantidades", cantidades);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	
	@PostMapping("/dias/{idUsuario}")
	public ResponseEntity<?> create(
			@PathVariable (value = "idUsuario") long idUsuario, @RequestBody Dia dia, BindingResult result){
		
		Dia nuevoDia = null;
		Usuario usuario = null;
		List<RelDiaGasto> cantidades = dia.getCantidades();
		dia.setCantidades(new ArrayList<RelDiaGasto>());
		
		Map<String, Object> response = new HashMap<>();
		
		log.info("La fecha recibida: " + dia.getFecha());
		
		//dia.setFecha(modifiarFecha(dia.getFecha(), 1));
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			usuario = usuarioService.findById(idUsuario);
			dia.setUsuario(usuario);
			nuevoDia = diaService.save(dia);
			
			BigDecimal gastoDia = new BigDecimal("0.0");
			BigDecimal saldoFinal;
			
			for(RelDiaGasto cantidad: cantidades) {
				cantidad.setDia(nuevoDia);
				gastoDia = gastoDia.add(cantidad.getCantidad());
				diaService.saveCantidad(cantidad);
			}
			
			saldoFinal = dia.getSaldoInicial().subtract(gastoDia);
	
			dia.setSaldoFinal(saldoFinal);
			nuevoDia = diaService.save(dia);			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		nuevoDia.setFecha(modifiarFecha(nuevoDia.getFecha(), -1));
		response.put("mensaje", "El dia se ha agregado con éxito!");
		response.put("Día", nuevoDia);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
		
	}
	
	@PutMapping("/dias/{idUsuario}/{idDia}")
	public ResponseEntity<?> update(@RequestBody Dia dia, @PathVariable (value = "idUsuario") Long idUsuario, 
			 						@PathVariable (value = "idDia") Long idDia, BindingResult result){
		
		Dia diaActual = null;
		Dia diaNuevo = null;
		List<RelDiaGasto> cantidades = dia.getCantidades();
		dia.setCantidades(new ArrayList<RelDiaGasto>());
		
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
			
			diaActual = diaService.findById(idDia);
			
			if(diaActual == null) {
				response.put("respuesta", false);
				response.put("mensaje", "Error: El dia ID: ".concat(idDia.toString().concat(" no existe")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			
			diaActual.setFecha(dia.getFecha());
			diaActual.setSaldoInicial(dia.getSaldoInicial());
			diaService.deleteCantidades(diaActual);
			
			BigDecimal gastoDia = new BigDecimal("0.0");
			BigDecimal saldoFinal;
			
			for(RelDiaGasto cantidad: cantidades) {
				cantidad.setDia(diaActual);
				gastoDia = gastoDia.add(cantidad.getCantidad());
				diaService.saveCantidad(cantidad);
			}			
			
			saldoFinal = dia.getSaldoInicial().subtract(gastoDia);
			log.info("el total del dia: " + saldoFinal);
			diaActual.setSaldoFinal(saldoFinal);
			diaNuevo = diaService.save(diaActual);
			
		} catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al actulizar en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta", true);
		response.put("mensaje", "El dia se ha modificado con éxito!");
		response.put("Día", diaNuevo);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/dias/{idDia}")
	public ResponseEntity<?> delete(@PathVariable (value = "idDia") Long idDia){
		
		Dia dia = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			dia = diaService.findById(idDia);
			
			if(dia == null) {
				response.put("respuesta", false);
				response.put("mensaje", "Error: El dia ID: ".concat(idDia.toString().concat(" no existe")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			
			diaService.deleteCantidades(dia);
			diaService.delete(idDia);
		} catch (DataAccessException e) {
			response.put("estado", false);
			response.put("mensaje", "Error al eliminar la categoría de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("estado", true);
		response.put("mensaje", "El dia se ha eliminado");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/cantidad")
	public ResponseEntity<?> createCantidad(@RequestBody RelDiaGasto cantidad, BindingResult result){
		
		RelDiaGasto nuevaCantidad = null;
		Dia dia = diaService.findById(cantidad.getDia().getIdDia());
		Gasto gasto = gastoService.findById(cantidad.getGasto().getIdGasto());
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
			cantidad.setGasto(gasto);
			cantidad.setDia(dia);
			nuevaCantidad = diaService.saveCantidad(cantidad);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El dia se ha agregado con éxito!");
		response.put("cantidad", nuevaCantidad);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
		
	}
	
	private Date modifiarFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		
		return calendar.getTime();
	}
}
