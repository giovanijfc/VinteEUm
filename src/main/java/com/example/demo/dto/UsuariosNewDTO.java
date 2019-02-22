package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuariosNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idUsuario;	

	private String nome;	
	
	private String email;	
	
	private String senha;
	
	private Integer vitorias;
	private Integer derrotas;
	
	public UsuariosNewDTO() {
		
	}
	
	
	
	public UsuariosNewDTO(Integer idUsuario, @NotEmpty(message = "Preenchimento obrigatório") String nome,
			@NotEmpty(message = "Preenchimento obrigatório") @Email(message = "Email inválido") String email,
			@NotEmpty(message = "Preenchimento obrigatório") String senha, Integer vitorias, Integer derrotas) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
	}



	public Integer getVitorias() {
		return vitorias;
	}

	public void setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
	}

	public Integer getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
}
