package com.diaz.springboot.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.diaz.springboot.backend.apirest.models.entity.Usuario;
import com.diaz.springboot.backend.apirest.models.services.UsuarioModelsServiceImpl;

@Component
public class InfoAdicionalToken implements TokenEnhancer{
	
	@Autowired
	@Qualifier("usuarioModelsServiceImpl")
	private UsuarioModelsServiceImpl usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = usuarioService.buscasEmail(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("email", authentication.getName());
		info.put("user ID", usuario.getId_usuario());
		info.put("nombre", usuario.getNombre().concat(" ").concat(usuario.getApellido()));
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
