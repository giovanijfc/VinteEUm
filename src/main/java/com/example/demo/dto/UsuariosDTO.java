package com.example.demo.dto;

import java.io.Serializable;

public class UsuariosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nome;
	private String email;
	
	private String palavraChave;
	
	public UsuariosDTO() {
		
	}
	
	public UsuariosDTO(String id, String nome, String email, String palavraChave) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.palavraChave = palavraChave;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavaChave) {
		this.palavraChave = palavaChave;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
