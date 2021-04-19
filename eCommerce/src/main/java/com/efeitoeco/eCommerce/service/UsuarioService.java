package com.efeitoeco.eCommerce.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.efeitoeco.eCommerce.controller.request.UsuarioUpdate;
import com.efeitoeco.eCommerce.model.Produto;
import com.efeitoeco.eCommerce.model.Usuario;
import com.efeitoeco.eCommerce.model.UsuarioLogin;
import com.efeitoeco.eCommerce.repository.ProdutoRepository;
import com.efeitoeco.eCommerce.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		
		validarCadastroDuplicado(usuario);
		
		validarEmailInvalido(usuario.getEmail());
		
		String senhaCodificada = codificarSenha(usuario.getSenha());
		
		usuario.setSenha(senhaCodificada);
		
		return repository.save(usuario);
	}
	
	public void validarCadastroDuplicado(Usuario usuario) {
		
		Optional<Usuario> usuarioOptional = repository.findByEmail(usuario.getEmail());
		
		if(usuarioOptional.isPresent()) {
			throw new RuntimeException("E-mail " + usuario.getEmail() + " já cadastrado.");
		}
		
	}
	
	public void validarEmailInvalido(String email) {
		String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPat.matcher(email);
		if(matcher.find() == false) {
			throw new RuntimeException("E-mail inválido.");
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
	
	public Usuario alterarUsuario(UsuarioUpdate usuarioUpdate, Long usuarioId) {
		Optional<Usuario> usuarioOptional = repository.findById(usuarioId);
		
		if(usuarioOptional.isEmpty())
			throw new RuntimeException("O usuario com o id: " + usuarioId + " não foi encontrado.");
		
		Usuario usuario = usuarioOptional.get();
		
		usuario.setFoto(usuarioUpdate.getFoto());
		usuario.setNome(usuarioUpdate.getNome());
		usuario.setSobrenome(usuarioUpdate.getSobrenome());
		
		//String senhaCodificada = codificarSenha(usuarioUpdate.getSenha());
		
		//usuario.setSenha(senhaCodificada);
		
		return usuario;		
	}
	
	public Usuario adicionarProdutosComprados(Long usuarioId, List<Long> idsDosProdutosComprados) {
		
		Optional<Usuario> usuarioOptional = repository.findById(usuarioId);
		
		Usuario usuario = usuarioOptional.get();
		
		List<Produto> produtosComprados = produtoRepository.findAllById(idsDosProdutosComprados);
		
		usuario.getMinhasCompras().addAll(produtosComprados);
		
		repository.save(usuario);
		
		return usuario;
	}
	
	private String codificarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaCodificada = encoder.encode(senha);
		
		return senhaCodificada;
	}
}
