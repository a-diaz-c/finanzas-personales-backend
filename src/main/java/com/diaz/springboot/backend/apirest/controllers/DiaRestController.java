package com.diaz.springboot.backend.apirest.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.springframework.security.core.Authentication;
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
	
	@GetMapping("/dias")
	public ResponseEntity<?> index(Authentication authentication){
		
		Map<String, Object> response = new HashMap<>();	
		Usuario usuario = null;
		List<Dia> dias = null;
		
		try {
			usuario = usuarioService.buscasEmail(authentication.getName());
			
			if(usuario == null) {
				response.put("respuesta", false);
				response.put("mensaje", "El usuario ID: ".concat(authentication.getName().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			dias = diaService.findByUsuario(usuario);
			Collections.sort(dias, (x,y)-> y.getFecha().compareTo(x.getFecha()));
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
		response.put("dia", dia);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/dias/{idDia}/movimiento")
	public ResponseEntity<?> addDia(Authentication authentication, @RequestBody RelDiaGasto moviento, 
									BindingResult result, @PathVariable (value = "idDia") Long idDia){
		
		Map<String, Object> response = new HashMap<>();
		Dia dia = null;
		
		try {
			dia = diaService.findById(idDia);
			if(dia == null) {
				response.put("respuesta", false);
				response.put("mensaje", "El dia ID: ".concat(idDia.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			moviento.setDia(dia);
			RelDiaGasto movientoNuevo = diaService.saveCantidad(moviento);
			response.put("mensaje", "El movimiento se ha agregado al dia con éxito!");
			response.put("Día", movientoNuevo);
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@PostMapping("/dias")
	public ResponseEntity<?> create(Authentication authentication, @RequestBody Dia dia, BindingResult result){
		
		Dia nuevoDia = null;
		Usuario usuario = null;
		List<RelDiaGasto> cantidades = dia.getCantidades();
		dia.setCantidades(new ArrayList<RelDiaGasto>());
		
		Map<String, Object> response = new HashMap<>();
		
		log.info("La fecha recibida: " + dia.getFecha());
				
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {			
			usuario = usuarioService.buscasEmail(authentication.getName());
			//dia.setFecha(modifiarFecha(dia.getFecha(), 1));
			
			if(diaService.findByFecha(dia.getFecha(), usuario) != null) {
				response.put("respuesta", false);
				response.put("mensaje", "El la fecha ya esta guardada");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			
			dia.setUsuario(usuario);
			nuevoDia = diaService.save(dia);
			
			BigDecimal gastoDelDia = new BigDecimal("0.0");
			BigDecimal saldoFinal;
			
			for(RelDiaGasto cantidad: cantidades) {
				cantidad.setDia(nuevoDia);
				if(cantidad.isIngreso())
					gastoDelDia = gastoDelDia.add(cantidad.getCantidad());
				else
					gastoDelDia = gastoDelDia.subtract(cantidad.getCantidad());
				
				diaService.saveCantidad(cantidad);
			}
			log.info("total gasto: " + gastoDelDia);
			saldoFinal = dia.getSaldoInicial().add(gastoDelDia);
			
	
			dia.setSaldoFinal(saldoFinal);
			usuario.setSaldo(usuario.getSaldo().add(saldoFinal));
			nuevoDia = diaService.save(dia);			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//nuevoDia.setFecha(modifiarFecha(nuevoDia.getFecha(), -1));
		response.put("mensaje", "El dia se ha agregado con éxito!");
		response.put("Día", nuevoDia);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
		
	}

	
	@PutMapping("/dias/{idDia}")
	public ResponseEntity<?> update(@RequestBody Dia dia, Authentication authentication,
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
	
	@GetMapping("dias/saldoInicial/{fecha}")
	private ResponseEntity<?> diaAnterrior(@PathVariable (value = "fecha") String fecha, Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		Date date = null;
		try {
			Usuario usuario = usuarioService.buscasEmail(authentication.getName());
			date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
		
			
			date = modifiarFecha(date, -1);
			Dia diaAnterio = diaService.findByFecha(date, usuario);
			if(diaAnterio != null) {
				response.put("respuesta", true);
				response.put("mensaje", "Dia anterior encontrado");
				response.put("usuario", diaAnterio);
			}else {
				response.put("repuesta", false);
				response.put("mensaje", "No hay dia anterior");
			}
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (ParseException e) {
			response.put("error", e.getMessage().concat(" ").concat(e.getCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
