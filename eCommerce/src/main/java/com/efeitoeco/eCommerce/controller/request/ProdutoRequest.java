package com.efeitoeco.eCommerce.controller.request;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.efeitoeco.eCommerce.model.Categoria;
import com.efeitoeco.eCommerce.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ProdutoRequest {
	
	private String nome;
	
	private String descricao;
	
	private double preco;
	
	private String imagem1;
	
	private String imagem2;
	
	private Categoria categoria;
	
	private Long idDoUsuario;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getImagem1() {
		return imagem1;
	}

	public void setImagem1(String imagem1) {
		this.imagem1 = imagem1;
	}

	public String getImagem2() {
		return imagem2;
	}

	public void setImagem2(String imagem2) {
		this.imagem2 = imagem2;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getIdDoUsuario() {
		return idDoUsuario;
	}

	public void setIdDoUsuario(Long idDoUsuario) {
		this.idDoUsuario = idDoUsuario;
	}
	
	

}
