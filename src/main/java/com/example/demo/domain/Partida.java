package com.example.demo.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Partida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="partida", fetch= FetchType.EAGER)
	List <CartaUsuarioPartida> carUsuPar;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPartida;
	
	
	@ManyToOne
	@JoinColumn(name="Maquina")
	private Usuarios maquina;
	
	
	@ManyToOne
	@JoinColumn(name="Jogador")
	private Usuarios jogador;
	
	
	private Boolean partidaFinalizada;


	public Partida(Integer idPartida, Usuarios maquina, Usuarios jogador) {
		super();
		this.idPartida = idPartida;
		this.maquina = maquina;
		this.jogador = jogador;
	}


	public Partida(Integer idPartida) {
		super();
		this.idPartida = idPartida;
	}


	public  Partida() {
		
	}

	
	
	public Integer getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}


	public Usuarios getMaquina() {
		return maquina;
	}


	public void setMaquina(Usuarios maquina) {
		this.maquina = maquina;
	}


	public Usuarios getJogador() {
		return jogador;
	}


	public void setJogador(Usuarios jogador) {
		this.jogador = jogador;
	}


	public List<CartaUsuarioPartida> getCarUsuPar() {
		return carUsuPar;
	}


	public void setCarUsuPar(List<CartaUsuarioPartida> carUsuPar) {
		this.carUsuPar = carUsuPar;
	}
	
	public Boolean getPartidaFinalizada() {
		return partidaFinalizada;
	}


	public void setPartidaFinalizada(Boolean partidaFinalizada) {
		this.partidaFinalizada = partidaFinalizada;
	}



	
	
	
}
