package com.example.demo.dto;

import java.io.Serializable;

public class UsuariosNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idUsuario;	

	private String nome;	
	
	private String email;	
	
	private String senha;
	
	private Integer vitorias;
	private Integer derrotas;
	
	private String palavraChave;
	
	public UsuariosNewDTO() {
		
	}
	
	public UsuariosNewDTO(Integer idUsuario, String nome, String email, String senha, Integer vitorias,
			Integer derrotas, String palavraChave) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
		this.palavraChave = palavraChave;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
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
