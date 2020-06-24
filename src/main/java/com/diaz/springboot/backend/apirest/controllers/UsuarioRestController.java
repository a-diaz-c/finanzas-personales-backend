package com.diaz.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
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
import com.diaz.springboot.backend.apirest.models.services.IModelsService;
import com.diaz.springboot.backend.apirest.models.services.OperacionesUsuarioService;
import com.diaz.springboot.backend.apirest.models.services.UsuarioModelsServiceImpl;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	
	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("usuarioModelsServiceImpl")
	private UsuarioModelsServiceImpl usuarioService;
	
	@Autowired
	private CategoriaModelsServiceImpl categoriaService;
	
	@Autowired
	private OperacionesUsuarioService operacionesService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*@GetMapping("/usuarios")
	public ResponseEntity<?> index(){
		Map<String, Object> response = new HashMap<>();
		List<Usuario> usuarios = null;
		
		try {
			usuarios = usuarioService.findAll();
		} catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(" :").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("respuesta", true);
		response.put("usuarios", usuarios);
		response.put("mensaje", "Usuarios consultadas");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}*/
	
	@GetMapping("/usuarios")
	public ResponseEntity<?> show(Authentication authentication) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			usuario = usuarioService.buscasEmail(authentication.getName());			
		}catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(usuario == null) {
			response.put("respuesta", false);
			response.put("mensaje", "El usuario ID: ".concat(authentication.getName().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("respuesta", true);
		response.put("usuario", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
		
	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@RequestBody Usuario usuario, BindingResult result) {
		
		Usuario usuarioNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("respuesta", false);
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			/*String passwordBcryp =  passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(passwordBcryp);*/
			usuarioNuevo = usuarioService.save(usuario);
		}catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("respuesta", true);
		response.put("mensaje", "El usuario se ha creado con éxito!");
		response.put("cliente", usuarioNuevo);	
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/usuarios")
	public ResponseEntity<?> update(@RequestBody Usuario usuario,Authentication authentication, BindingResult result) {
		
		Usuario usuarioActual = usuarioService.buscasEmail(authentication.getName());
		Usuario usuarioActualizado = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			response.put("respuesta", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioActual == null) {
			response.put("respuesta", false);
			response.put("mensaje", "Error: El Usuario ID: ".concat(authentication.getName().concat(" no existe")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setUsuario(usuario.getUsuario());
			
			usuarioActualizado = usuarioService.save(usuarioActual);
		}catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		
		response.put("respuesta", true);
		response.put("mensaje", "El usuario se ha actualizado con éxito!");
		response.put("cliente", usuarioActualizado);	
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/usuarios")
	public ResponseEntity<?> delete(Authentication authentication) {		
		Map<String, Object> response = new HashMap<>();
		
		try {
			operacionesService.eliminarDatosUsuario(authentication.getName());
		}catch (DataAccessException e) {
			response.put("respuesta", false);
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("respuesta", true);
		response.put("mensaje","El usuario se ha eliminada");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

}
