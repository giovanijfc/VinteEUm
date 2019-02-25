package com.example.demo.dto;

public class ChangedNewPassDTO {

	private String palavraChave;
	private String email;
	private String newSenha;
		
	public ChangedNewPassDTO() {
		
	}
	
	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewSenha() {
		return newSenha;
	}

	public void setNewSenha(String newSenha) {
		this.newSenha = newSenha;
	}
}
