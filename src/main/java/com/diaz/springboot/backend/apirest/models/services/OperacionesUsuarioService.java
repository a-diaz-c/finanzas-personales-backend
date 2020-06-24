package com.diaz.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.diaz.springboot.backend.apirest.models.entity.Categoria;
import com.diaz.springboot.backend.apirest.models.entity.Dia;
import com.diaz.springboot.backend.apirest.models.entity.Gasto;
import com.diaz.springboot.backend.apirest.models.entity.RelDiaGasto;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

@Service
public class OperacionesUsuarioService {

	@Autowired
	private DiaModelsServiceImpl diaService;
	@Autowired
	private GastoModelsServiceImpl gastoService;
	@Autowired
	@Qualifier("usuarioModelsServiceImpl")
	private UsuarioModelsServiceImpl usuarioService;
	@Autowired
	private CategoriaModelsServiceImpl categoriaService;
	
	public void eliminarDatosUsuario(String email ) throws DataAccessException{
		
		Usuario usuario;
		List<Categoria> categorias;
		List<Dia> dias;
		
		usuario = usuarioService.buscasEmail(email);
		dias = diaService.findByUsuario(usuario);
		categorias = categoriaService.findByUsuario(usuario);		
		
		for (Dia dia : dias) {
			diaService.deleteCantidades(dia);
			diaService.delete(dia.getIdDia());
		}
		
		for (Categoria categoria : categorias) {
			gastoService.deleteByCategoria(categoria);
			categoriaService.delete(categoria.getIdCategoria());
		}
		
		usuarioService.delete(usuario.getId_usuario());
		
	}
}
