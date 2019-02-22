package com.example.demo.services;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CartaUsuarioPartida;
import com.example.demo.domain.Cartas;
import com.example.demo.domain.Partida;
import com.example.demo.domain.ResponseResource;
import com.example.demo.domain.Usuarios;
import com.example.demo.repository.CartasRepository;
import com.example.demo.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private CartasRepository cartaRepo;
	@Autowired
	private PartidaRepository partidaRepo;

	
	public CartaUsuarioPartida puxarCarta(Usuarios usuario, Partida partida) {		
	List<Cartas> lista = cartaRepo.findAll().stream().sorted((a1,a2) -> (new Random()).nextInt(2)-1).collect(Collectors.toList());
	lista.removeAll(partida.getCarUsuPar().stream().map(CartaUsuarioPartida::getCartas).collect(Collectors.toList()));
	CartaUsuarioPartida carUsuPa = new CartaUsuarioPartida();
	carUsuPa.setUsuarios(usuario);
	carUsuPa.setPartida(partida);
	carUsuPa.setCartas(lista.stream().findFirst().orElse(null));
	return carUsuPa.getCartas() == null ? null: carUsuPa;
	}
	
	public ResponseResource<Partida>comparacao(Usuarios user, Integer valorMaq, Integer valorUser, Integer idPart){
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		Partida p1 = partidaRepo.getOne(idPart);					
		if(valorMaq > valorUser) {
			p1.setPartidaFinalizada(true);
			partidaRepo.save(p1);
			partidaRepo.flush();
			response.setDados(p1);
			response.setMensagem("Maquina venceu com "+valorMaq+" pontos e usuario(a) " +user.getNome()+ " perdeu com "+valorUser+" pontos.");
			return response;
		}else if(valorUser > valorMaq) {
			p1.setPartidaFinalizada(true);
			partidaRepo.save(p1);
			partidaRepo.flush();
			response.setDados(p1);
			response.setMensagem(user.getNome()+" venceu com "+valorUser+" pontos e maquina perdeu com "+valorMaq+" pontos.");
			return response;
		}else if(valorMaq == valorUser) {
		p1.setPartidaFinalizada(true);
		partidaRepo.save(p1);
		partidaRepo.flush();
		response.setDados(p1);
		response.setMensagem("Empate!!! "+user.getNome()+" com "+valorUser+" e maquina com "+valorMaq+".");
		return response;
		}		
		return response;
	}
	
	public ResponseResource<Partida> vitoria(Usuarios user, Integer valor, Integer idPart) {
		Partida p1 = partidaRepo.getOne(idPart);
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		if(valor > 21) {
			p1.setPartidaFinalizada(true);
			partidaRepo.save(p1);
			partidaRepo.flush();
			response.setDados(p1);
			response.setMensagem("Jogador(a) " + user.getNome() + " estourou com  " +valor.intValue()+ " pontos.Partida finalizada.");
			return response;
		}else if(valor == 21) {
			p1.setPartidaFinalizada(true);
			partidaRepo.save(p1);
			partidaRepo.flush();
			response.setDados(p1);
			response.setMensagem("Jogador(a) " + user.getNome() + " ganhou com " +valor.intValue()+ " pontos.");
			return response;
		}
		response.setDados(p1);
		response.setMensagem("Continuando....");
		return response;
	}
}
