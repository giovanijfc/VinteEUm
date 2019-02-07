package com.example.demo.domain.enums;

public enum Naipes {
	
	COPAS("COPA"),
	ESPADAS("ESPADA"),
	PAUS("PAU"),
	OUROS("OURO"),
	ÁS_OURO("ÁS_OURO"),
	ÁS_ESPADA("ÁS_ESPADA"),
	ÁS_PAUS("ÁS_PAU"),
	ÁS_COPAS("ÁS_COPA"),
	K_COPAS("K_COPA"),
	K_ESPADAS("K_ESPADA"),
	K_PAUS("K_PAU"),
	K_OUROS("K_OURO"),
	Q_ESPADAS("Q_ESPADA"),
	Q_PAUS("Q_PAU"),
	Q_OUROS("Q_OURO"),
	Q_COPAS("Q_COPA"),
	J_COPAS("J_COPA"),
	J_PAUS("J_PAU"),
	J_OUROS("J_OURO"),
	J_ESPADAS("J_ESPADA");
	
	private int cod;
	private String descricao;
	
	
	private Naipes( String descricao) {
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	
}
