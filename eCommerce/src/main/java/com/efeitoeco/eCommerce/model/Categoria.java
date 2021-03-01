package com.efeitoeco.eCommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(min = 3, max = 20, message = "Aviso: O valor mínimo de caracteres é de 3, e o valor máximo é de 20!")
	private String nome;
	
	@NotNull
	@Size(min = 3, max = 45, message = "Aviso: O valor mínimo de caracteres é de 3, e o valor máximo é de 45!")
	private String composicao;
	
	@NotNull
	@Size(min = 3, max = 155, message = "Aviso: O valor mínimo de caracteres é de 3, e o valor máximo é de 155!")
	private String descricao;
	
	@NotNull
	@Size(min = 3, max = 20, message = "Aviso: O valor mínimo de caracteres é de 3, e o valor máximo é de 20!")
	private String publico;

	@OneToMany (mappedBy = "categoria",cascade = CascadeType.ALL)
	@JsonIgnoreProperties ("categoria")
	private List<Produto> produto;
	
	
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

	public String getComposicao() {
		return composicao;
	}

	public void setComposicao(String composicao) {
		this.composicao = composicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	
}
