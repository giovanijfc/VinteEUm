package com.example.demo.resources;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CartaUsuarioPartida;
import com.example.demo.domain.Partida;
import com.example.demo.domain.Usuarios;
import com.example.demo.repository.CartaUsuarioPartidaRepository;
import com.example.demo.repository.PartidaRepository;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.services.CartasService;
import com.example.demo.services.PartidaService;

@RestController
@RequestMapping(value="/21")
public class PartidaResource {
	
	private Integer valorUser;
	private Integer valorMaquina = 0;
	
	
	@Autowired
	CartaUsuarioPartidaRepository cartaUsuPar;
	@Autowired
	PartidaService partidaSer;
	@Autowired
	PartidaRepository partidaRepo;
	@Autowired
	UsuariosRepository usuarioRepo;
	@Autowired
	CartasService cartaSer;
	


	
	@RequestMapping(value="/registrando/{nome}", method=RequestMethod.GET)
	public Usuarios registro(@PathVariable String nome) {
		Usuarios user = new Usuarios(null, nome);	
		usuarioRepo.save(user);
		return user;
	}
	
	@RequestMapping(value="/iniciar/{id}", method=RequestMethod.GET)
	public Partida inicio(@PathVariable Integer id) {
		Partida p1 = new Partida(null);
		p1.setPartidaFinalizada(false);
		p1.setCarUsuPar(new ArrayList<CartaUsuarioPartida>());
		p1.setMaquina(usuarioRepo.findById(1).orElse(null));
		p1.setJogador(usuarioRepo.findById(id).orElse(null));
		if(p1.getJogador() == null) {
			return null;
		}	
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		partidaRepo.save(p1);partidaRepo.flush();	
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		partidaRepo.save(p1);	
		partidaRepo.flush();
		return p1;
			
	}
	
	@RequestMapping(value="/continuacao/partida{idPart}/user{idUser}", method=RequestMethod.GET)
	public String continuar(@PathVariable Integer idUser, @PathVariable Integer idPart) {	
		int i =1;
		Partida p1 = partidaRepo.getOne(idPart);
		if(p1.getPartidaFinalizada()) {
			return null;
		}
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		cartaUsuPar.flush();
		partidaRepo.flush();	
		Integer valorUser =+ cartaUsuPar.getOne(i).getCartas().getValor();	++i;		
		valorUser = valorUser + cartaUsuPar.getOne(i).getCartas().getValor();  ++i;		
		valorUser = valorUser + cartaUsuPar.getOne(i).getCartas().getValor();
				
		return partidaSer.vitoria(p1.getJogador(), valorUser, idPart);		
	}
	
	@RequestMapping
	public void continuar2() {
		
	}
	
	@RequestMapping
	public void naoContinuar() {
		
	}

	public Integer getValorUser() {
		return valorUser;
	}

	public void setValorUser(Integer valorUser) {
		this.valorUser = valorUser;
	}

	public Integer getValorMaquina() {
		return valorMaquina;
	}

	public void setValorMaquina(Integer valorMaquina) {
		this.valorMaquina = valorMaquina;
	}
}
