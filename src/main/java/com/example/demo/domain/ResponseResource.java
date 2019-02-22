/**
 * 
 */
package com.example.demo.domain;

import java.io.Serializable;

 public class ResponseResource <T> implements Serializable {

	private static final long serialVersionUID = -5333631717996304317L;
	
	private T dados;
	private String mensagem;
	
	public T getDados() {
		return dados;
	}
	public void setDados(T dados) {
		this.dados = dados;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	

}
