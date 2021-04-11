package com.efeitoeco.eCommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(
		name = "tb_usuario",
		uniqueConstraints = {
	          @UniqueConstraint(columnNames = "email")
	        }
)
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String sobrenome;
	
	@NotNull
	private String email;
	
	@NotNull
	private String senha;
	
	private String foto;
	
	@OneToMany(mappedBy = "criadoPor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("criadoPor")
	private List<Produto> produtosVenda = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "compras",
			joinColumns = @JoinColumn(name = "comprador_id"),
			inverseJoinColumns = @JoinColumn(name = "produto_id")
			)
	@JsonIgnoreProperties("cliente")
	private List<Produto> minhasCompras = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Produto> getProdutosVenda() {
		return produtosVenda;
	}

	public void setProdutosVenda(List<Produto> produtosVenda) {
		this.produtosVenda = produtosVenda;
	}

	public List<Produto> getMinhasCompras() {
		return minhasCompras;
	}

	public void setMinhasCompras(List<Produto> minhasCompras) {
		this.minhasCompras = minhasCompras;
	}
	
	
	
}
