package com.example.demo.resources;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CartaUsuarioPartida;
import com.example.demo.domain.Cartas;
import com.example.demo.domain.Partida;
import com.example.demo.domain.ResponseResource;
import com.example.demo.domain.Usuarios;
import com.example.demo.domain.enums.Perfil;
import com.example.demo.repository.CartaUsuarioPartidaRepository;
import com.example.demo.repository.PartidaRepository;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.security.UserSS;
import com.example.demo.services.PartidaService;
import com.example.demo.services.UserService;
import com.example.demo.services.exceptions.AuthorizationException;
import com.example.demo.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/21/partida")
public class PartidaResource {

	@Autowired
	private CartaUsuarioPartidaRepository cartaUsuPar;
	@Autowired
	private PartidaService partidaSer;
	@Autowired
	private PartidaRepository partidaRepo;
	@Autowired
	private UsuariosRepository usuarioRepo;

	@RequestMapping(value = "/iniciar", method = RequestMethod.GET)
	public ResponseResource<Partida> inicio(@RequestParam(name = "usuario") Integer id) {
		@SuppressWarnings("unused")
		Usuarios u1 = usuarioRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuario não existe"));

		UserSS user = UserService.authenticated();
		ResponseResource<Partida> response = new ResponseResource<>();
		Partida p1 = new Partida(null);
		partidaRepo.save(p1);

		if (user == null || !user.hasRole(Perfil.ADMIN) || !id.equals(user.getId()) || user.getId() == 1) {
			throw new AuthorizationException("Sem autorização ou requisitos para completar esta ação");
		}

		p1.setPartidaFinalizada(false);
		p1.setCarUsuPar(new ArrayList<CartaUsuarioPartida>());

		p1.setMaquina(usuarioRepo.findById(1).orElse(null));
		p1.setJogador(
				usuarioRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado")));

		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));

		partidaRepo.flush();
		cartaUsuPar.flush();
		response.setMensagem("Cartas puxadas para jogador(a) " + p1.getJogador().getNome());
		response.setDados(p1);

		return response;

	}

	@RequestMapping(value = "/continuacao", method = RequestMethod.GET)
	public ResponseResource<Partida> continuar(@RequestParam(name = "partida") Integer idPart) {

		UserSS user = UserService.authenticated();

		ResponseResource<Partida> response = new ResponseResource<Partida>();
		Partida p1 = partidaRepo.findById(idPart).orElseThrow(() -> new ObjectNotFoundException("Partida não existe"));

		p1.setCarUsuPar(p1.getCarUsuPar().stream().distinct().collect(Collectors.toList()));

		if (user == null || !user.hasRole(Perfil.ADMIN) && !p1.getJogador().getIdUsuario().equals(user.getId())
				|| p1.getJogador().getIdUsuario() == 1) {
			throw new AuthorizationException("Sem autorização ou requisitos para completar esta ação");
		}

		if (p1.getPartidaFinalizada()) {
			response.setMensagem("Esta Partida já foi finalizada!");
			response.setDados(p1);
			return response;
		}
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getJogador(), p1));

		partidaRepo.flush();
		cartaUsuPar.flush();

		return partidaSer.tierOrBurst(p1.getJogador(),
				p1.getCarUsuPar().stream()
						.filter(a -> a.getUsuarios().getIdUsuario().equals(p1.getJogador().getIdUsuario()))
						.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum(),
				idPart);

	}

	@RequestMapping(value = "/naoContinuar", method = RequestMethod.GET)
	public ResponseResource<Partida> naoContinuar(@RequestParam(name = "partida") Integer idPart) {

		UserSS user = UserService.authenticated();

		ResponseResource<Partida> response = new ResponseResource<Partida>();
		Partida p1 = partidaRepo.findById(idPart).orElseThrow(() -> new ObjectNotFoundException("Partida não existe"));

		if (user == null || !user.hasRole(Perfil.ADMIN) && !p1.getJogador().getIdUsuario().equals(user.getId())
				|| p1.getJogador().getIdUsuario() == 1) {
			throw new AuthorizationException("Sem autorização ou requisitos para completar esta ação");
		}

		p1.setCarUsuPar(p1.getCarUsuPar().stream().distinct().collect(Collectors.toList()));

		if (p1.getPartidaFinalizada()) {
			response.setMensagem("Esta Partida já foi finalizada!");
			response.setDados(p1);
			return response;
		}

		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getMaquina(), p1));
		p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getMaquina(), p1));

		while (p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
				.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum() < 17) {
			p1.getCarUsuPar().add(partidaSer.puxarCarta(p1.getMaquina(), p1));
			cartaUsuPar.flush();
		}

		partidaSer.tierOrBurst(p1.getMaquina(),
				p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
						.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum(),
				idPart);

		if (p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
				.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum() > 21) {
			response.setDados(p1);
			response.setMensagem("Maquina estourou com "
					+ p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
							.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum());
			return response;
		} else if (!p1.getPartidaFinalizada()) {
			return partidaSer.comparation(p1.getJogador(),
					p1.getCarUsuPar().stream().filter(a -> a.getUsuarios().getIdUsuario().equals(1))
							.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum(),
					p1.getCarUsuPar().stream()
							.filter(a -> a.getUsuarios().getIdUsuario().equals(p1.getJogador().getIdUsuario()))
							.map(CartaUsuarioPartida::getCartas).mapToInt(Cartas::getValor).sum(),
					idPart);
		}
		response.setDados(p1);
		response.setMensagem("!!!!!!!!!!!!!ERROR!!!!!!!!!!!!!!");
		return response;
	}

}
