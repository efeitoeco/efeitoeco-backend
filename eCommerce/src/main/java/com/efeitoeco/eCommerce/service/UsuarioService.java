package com.efeitoeco.eCommerce.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.efeitoeco.eCommerce.model.Usuario;
import com.efeitoeco.eCommerce.model.UsuarioLogin;
import com.efeitoeco.eCommerce.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		
		validarCadastroDuplicado(usuario);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return repository.save(usuario);
	}
	
	public void validarCadastroDuplicado(Usuario usuario) {
		
		Optional<Usuario> usuarioOptional = repository.findByEmail(usuario.getEmail());
		
		if(usuarioOptional.isPresent()) {
			throw new RuntimeException("E-mail " + usuario.getEmail() + " já cadastrado.");
		}
		
	}
	
	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByEmail(user.get().getEmail());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = user.get().getEmail() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				
				String authHeader = "Basic " + new String (encodedAuth);
				
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				user.get().setId(usuario.get().getId());
				user.get().setSobrenome(usuario.get().getSobrenome());
				user.get().setEmail(usuario.get().getEmail());
				user.get().setSenha(usuario.get().getSenha());
				user.get().setFoto(usuario.get().getFoto());
				
				
				return user;
			}
			
		}
		return null;
	}
}
