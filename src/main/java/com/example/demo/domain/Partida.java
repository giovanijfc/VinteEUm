package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partida implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPartida;
	

	private Usuarios perdedor_id;
	

	private Usuarios vencedor_id;
	

	public Partida(Integer idPartida, Usuarios perdedor, Usuarios vencedor) {
		super();
		this.idPartida = idPartida;
		this.perdedor_id = perdedor;
		this.vencedor_id = vencedor;
	}

	public  Partida() {
		
	}

	
	
	public Integer getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}

	public Usuarios getPerdedor() {
		return perdedor_id;
	}

	public void setPerdedor(Usuarios perdedor) {
		this.perdedor_id = perdedor;
	}

	public Usuarios getVencedor() {
		return vencedor_id;
	}

	public void setVencedor(Usuarios vencedor) {
		this.vencedor_id = vencedor;
	}

	
	
	
}
