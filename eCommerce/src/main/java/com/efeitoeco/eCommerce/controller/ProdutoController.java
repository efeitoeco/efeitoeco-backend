package com.efeitoeco.eCommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efeitoeco.eCommerce.controller.request.ProdutoRequest;
import com.efeitoeco.eCommerce.controller.request.ProdutoUpdate;
import com.efeitoeco.eCommerce.model.Produto;
import com.efeitoeco.eCommerce.model.Usuario;
import com.efeitoeco.eCommerce.repository.ProdutoRepository;
import com.efeitoeco.eCommerce.repository.UsuarioRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Produto> post(@RequestBody ProdutoRequest produtoRequest) {
		
		Produto produto = new Produto();
		
		produto.setDescricao(produtoRequest.getDescricao());
		produto.setImagem1(produtoRequest.getImagem1());
		produto.setImagem2(produtoRequest.getImagem2());
		produto.setPreco(produtoRequest.getPreco());
		produto.setNome(produtoRequest.getNome());
		produto.setCategoria(produtoRequest.getCategoria());
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(produtoRequest.getIdDoUsuario());
		
		if (usuarioOptional.isEmpty())
			throw new RuntimeException("O usuário de id " + produtoRequest.getIdDoUsuario() + " não existe");
		
		produto.setCriadoPor(usuarioOptional.get());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> put(@RequestBody ProdutoUpdate produtoUpdate) {
		
		Produto produto = new Produto();
		
		produto.setId(produtoUpdate.getId());
		produto.setDescricao(produtoUpdate.getDescricao());
		produto.setImagem1(produtoUpdate.getImagem1());
		produto.setImagem2(produtoUpdate.getImagem2());
		produto.setPreco(produtoUpdate.getPreco());
		produto.setNome(produtoUpdate.getNome());
		produto.setCategoria(produtoUpdate.getCategoria());
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(produtoUpdate.getIdDoUsuario());
		
		if (usuarioOptional.isEmpty())
			throw new RuntimeException("O usuário de id " + produtoUpdate.getIdDoUsuario() + " não existe");
		
		produto.setCriadoPor(usuarioOptional.get());
		
		//try {
			return ResponseEntity.status(HttpStatus.OK).body(repository.save(produto));
		//} catch (Exception ex) {
			//throw new RuntimeException("Id do produto: " + produto.getId() + "\nId do usuário: " + produto.getCriadoPor().getId());
		//}
	}
	
	@DeleteMapping("/{id}")
	public void delete (@PathVariable long id) {
		repository.deleteById(id);
	}
}
