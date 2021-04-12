package com.efeitoeco.eCommerce.controller.request;

import javax.validation.constraints.NotNull;

public class UsuarioUpdate {

	private String nome;
	
	private String sobrenome;
		
	private String foto;

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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
