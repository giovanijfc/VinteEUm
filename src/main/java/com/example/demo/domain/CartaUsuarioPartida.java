package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartaUsuarioPartida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idCarParUsu;
	
	@ManyToOne
	@JsonIgnore 
	private Partida partida;
	
	@Autowired
	@OneToOne
	@JoinColumn(name="cartas_id")
	private Cartas cartas;
	
	
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuarios usuarios;
	
	
	
	public CartaUsuarioPartida() {
		
	}
	public CartaUsuarioPartida(Integer idCarParUsu, Boolean foi_Utilizada, Cartas cartas, Usuarios usuarios) {
		super();
		this.idCarParUsu = idCarParUsu;
		this.cartas = cartas;
		this.usuarios = usuarios;
	}


	public Integer getIdCarParUsu() {
		return idCarParUsu;
	}	
	public void setIdCarParUsu(Integer idCarParUsu) {
		this.idCarParUsu = idCarParUsu;
	}
	
	public Cartas getCartas() {
		return cartas;
	}

	public void setCartas(Cartas cartas) {
		this.cartas = cartas;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}
	
	
	public Partida getPartida() {
		return partida;
	}





	public void setPartida(Partida partida) {
		this.partida = partida;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCarParUsu == null) ? 0 : idCarParUsu.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaUsuarioPartida other = (CartaUsuarioPartida) obj;
		if (idCarParUsu == null) {
			if (other.idCarParUsu != null)
				return false;
		} else if (!idCarParUsu.equals(other.idCarParUsu))
			return false;
		return true;
	}
		
	
}
