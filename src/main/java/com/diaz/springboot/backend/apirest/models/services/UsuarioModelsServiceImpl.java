package com.diaz.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diaz.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.diaz.springboot.backend.apirest.models.entity.Usuario;

@Service
@Qualifier("usuarioModelsServiceImpl")
public class UsuarioModelsServiceImpl implements UserDetailsService{
	
	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	@Autowired
	private IUsuarioDao modelDao;

	
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) modelDao.findAll();
		
	}

	
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return modelDao.findById(id).orElse(null);
	}

	
	public Usuario save(Usuario usuario) {
		return modelDao.save(usuario);
	}

	
	public void delete(Long id) {
		modelDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = modelDao.findByEmail(email);
		
		if(usuario == null) {
			log.error("Error en el login: no existe el usuario");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("user"));
		
		return new User(usuario.getEmail(), usuario.getPassword(), true, true, true, true, authorities);
	}
	
	public Usuario buscasEmail(String email) {
		return modelDao.findByEmail(email);
	}
}
