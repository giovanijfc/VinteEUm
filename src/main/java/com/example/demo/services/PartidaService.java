package com.example.demo.services;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CartaUsuarioPartida;
import com.example.demo.domain.Cartas;
import com.example.demo.domain.Partida;
import com.example.demo.domain.Usuarios;
import com.example.demo.repository.CartaUsuarioPartidaRepository;
import com.example.demo.repository.CartasRepository;
import com.example.demo.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	CartaUsuarioPartidaRepository cartaUsuPar;
	@Autowired
	CartasRepository cartaRepo;
	@Autowired
	PartidaRepository partidaRepo;

	
	public CartaUsuarioPartida puxarCarta(Usuarios usuario, Partida partida) {		
	List<Cartas> lista = cartaRepo.findAll().stream().sorted((a1,a2) -> (new Random()).nextInt(2)-1).collect(Collectors.toList());
	lista.removeAll(partida.getCarUsuPar().stream().map(CartaUsuarioPartida::getCartas).collect(Collectors.toList()));
	CartaUsuarioPartida carUsuPa = new CartaUsuarioPartida();
	carUsuPa.setUsuarios(usuario);
	carUsuPa.setPartida(partida);
	carUsuPa.setCartas(lista.stream().findFirst().orElse(null));
	return carUsuPa.getCartas() == null ? null: carUsuPa;
	

	}
	public String vitoria(Usuarios user, Integer valor, Integer idPart) {
		Partida p1 = partidaRepo.getOne(idPart);
		if(valor > 21) {
			p1.setPartidaFinalizada(true);
			partidaRepo.flush();
			return "Jogador " + user.getNome() + " estourou com  " +valor.intValue()+ " pontos.Partida finalizada.";
		}else if(valor == 21) {
			p1.setPartidaFinalizada(true);
			partidaRepo.flush();
			return "Jogador " + user.getNome() + " com " +valor.intValue()+ " pontos.";
		}
		return "Continuando....";
	}
}
