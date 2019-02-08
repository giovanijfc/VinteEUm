package com.example.demo.domain.enums;

public enum Naipes {
	
	COPAS("COPAS"),
	ESPADAS("ESPADAS"),
	PAUS("PAUS"),
	OUROS("OUROS"),
	ÁS_OURO("ÁS_OUROS"),
	ÁS_ESPADA("ÁS_ESPADAS"),
	ÁS_PAUS("ÁS_PAUS"),
	ÁS_COPAS("ÁS_COPAS"),
	K_COPAS("K_COPAS"),
	K_ESPADAS("K_ESPADAS"),
	K_PAUS("K_PAUS"),
	K_OUROS("K_OUROS"),
	Q_ESPADAS("Q_ESPADAS"),
	Q_PAUS("Q_PAUS"),
	Q_OUROS("Q_OUROS"),
	Q_COPAS("Q_COPAS"),
	J_COPAS("J_COPAS"),
	J_PAUS("J_PAUS"),
	J_OUROS("J_OUROS"),
	J_ESPADAS("J_ESPADAS");
	
	private String descricao;
	
	
	private Naipes( String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	
}
