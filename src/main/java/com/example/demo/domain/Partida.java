package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partida {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPartida;
	
	private Integer perdedor;
	private Integer vencedor;
	
	private CartaUsuarioPartida cartasUsuPar;
	
	private Usuarios usuario1;
	
	private Usuarios usuario2;
	
	
	public Partida(Integer idPartida, Integer perdedor, Integer vencedor) {
		super();
		this.idPartida = idPartida;
		this.perdedor = perdedor;
		this.vencedor = vencedor;
	}

	public  Partida() {
		
	}

	public Integer getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}

	public Integer getPerdedor() {
		return perdedor;
	}

	public void setPerdedor(Integer perdedor) {
		this.perdedor = perdedor;
	}

	public Integer getVencedor() {
		return vencedor;
	}

	public void setVencedor(Integer vencedor) {
		this.vencedor = vencedor;
	}

	public CartaUsuarioPartida getCartasUsuPar() {
		return cartasUsuPar;
	}

	public void setCartasUsuPar(CartaUsuarioPartida cartasUsuPar) {
		this.cartasUsuPar = cartasUsuPar;
	}

	public Usuarios getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(Usuarios usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuarios getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(Usuarios usuario2) {
		this.usuario2 = usuario2;
	}
	
	
	
	
}
