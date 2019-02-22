package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cartas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idCarta;
	
	private Integer valor;
	
	private String naipe;
	
	public Cartas() {
		
	}

	

	public Cartas(Integer idCarta, Integer valor, String naipe) {
		super();
		this.idCarta = idCarta;
		this.valor = valor;
		this.naipe = naipe;
	}



	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Integer getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(Integer idCarta) {
		this.idCarta = idCarta;
	}


	public String getNaipe() {
		return naipe;
	}



	public void setNaipe(String naipe) {
		this.naipe = naipe;
	}





}
