package com.example.demo.resources;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CartaUsuarioPartida;
import com.example.demo.domain.Cartas;
import com.example.demo.domain.Partida;
import com.example.demo.domain.ResponseResource;
import com.example.demo.repository.CartaUsuarioPartidaRepository;
import com.example.demo.repository.PartidaRepository;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.services.PartidaService;

@RestController
@RequestMapping(value="/21/partida")
public class PartidaResource {
	
	
	@Autowired
	private CartaUsuarioPartidaRepository cartaUsuPar;
	@Autowired
	private PartidaService partidaSer;
	@Autowired
	private PartidaRepository partidaRepo;
	@Autowired
	private UsuariosRepository usuarioRepo;

	
	@RequestMapping(value="/iniciar/{id}", method=RequestMethod.GET)
	public ResponseResource<Partida> inicio(@PathVariable Integer id) {
		ResponseResource<Partida> response = new ResponseResource<>();
		Partida p1 = new Partida(null);
		p1.setPartidaFinalizada(false);
		p1.setCarUsuPar(new ArrayList<CartaUsuarioPartida>());
		p1.setMaquina(usuarioRepo.findById(1).orElse(null));
		p1.setJogador(usuarioRepo.findById(id).orElse(null));
		if(p1.getJogador() == null) {
			response.setMensagem("Sem jogador(a) na partida!");
			response.setDados(p1);
			return response;
		}	
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		partidaRepo.save(p1);partidaRepo.flush();	
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		partidaRepo.save(p1);	
		partidaRepo.flush();
		response.setMensagem("Cartas puxadas para jogador(a) "+p1.getJogador().getNome());
		response.setDados(p1);
		return response;
			
	}
	
	@RequestMapping(value="/continuacao/partida{idPart}", method=RequestMethod.GET)
	public ResponseResource<Partida> continuar(@PathVariable Integer idPart) {
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		Partida p1 = partidaRepo.getOne(idPart);
		
		if(p1.getPartidaFinalizada()) {
			response.setMensagem("Esta Partida já foi finalizada!");
			response.setDados(p1);
			return response;
		}
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		cartaUsuPar.flush();
		partidaRepo.flush();	
			
		return partidaSer.vitoria(p1.getJogador(), p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(p1.getJogador().getIdUsuario()))
				.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor)
				.sum(), idPart);		
	}		
	
	@RequestMapping(value="/naoContinuar/partida{idPart}", method=RequestMethod.GET)
  	public ResponseResource<Partida> naoContinuar(@PathVariable Integer idPart) {
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		Partida p1 = partidaRepo.getOne(idPart);
		
		if(p1.getPartidaFinalizada()) {
			response.setMensagem("Esta Partida já foi finalizada!");
			response.setDados(p1);
			return response;
		}
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getMaquina(), p1));
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getMaquina(), p1));		
		cartaUsuPar.flush();
		partidaRepo.flush();
		while(p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
				.map(CartaUsuarioPartida::getCartas)
				.mapToInt(Cartas::getValor)
				.sum() < 17) {
			p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getMaquina(), p1));
			cartaUsuPar.flush();				
		}		
		partidaSer.vitoria(p1.getMaquina(),
		p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
		.map(CartaUsuarioPartida::getCartas)
		.mapToInt(Cartas::getValor)
		.sum(),
		idPart);
		
		if( ! p1.getPartidaFinalizada()) {		
		return partidaSer.comparacao(p1.getJogador(),
		p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
		.map(CartaUsuarioPartida::getCartas)
		.mapToInt(Cartas::getValor)
		.sum(),
		p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(p1.getJogador().getIdUsuario()))
		.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor)
		.sum(),
		idPart);	
		}
		
		return partidaSer.vitoria(p1.getMaquina(),
				p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
				.map(CartaUsuarioPartida::getCartas)
				.mapToInt(Cartas::getValor)
				.sum(),
				idPart);
	}

}
