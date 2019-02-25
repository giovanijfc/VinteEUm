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
import com.example.demo.repository.UsuariosRepository;

@Service
public class PartidaService {

	@Autowired
	private CartasRepository cartaRepo;
	@Autowired
	private PartidaRepository partidaRepo;
	@Autowired
	private UsuariosRepository usuarioRepo;

	public CartaUsuarioPartida puxarCarta(Usuarios usuario, Partida partida) {

		List<Cartas> lista = cartaRepo.findAll().stream().sorted((a1, a2) -> (new Random()).nextInt(2) - 1)
				.collect(Collectors.toList());

		lista.removeAll(
				partida.getCarUsuPar().stream().map(CartaUsuarioPartida::getCartas).collect(Collectors.toList()));

		CartaUsuarioPartida carUsuPa = new CartaUsuarioPartida();

		carUsuPa.setUsuarios(usuario);

		carUsuPa.setPartida(partida);

		carUsuPa.setCartas(lista.stream().findFirst().orElse(null));

		return carUsuPa.getCartas() == null ? null : carUsuPa;
	}

	public ResponseResource<Partida> comparation(Usuarios user, Integer valorMaq, Integer valorUser, Integer idPart) {
		if (!partidaRepo.findById(idPart).get().getPartidaFinalizada()) {
			if (valorMaq > valorUser) {
				ResponseResource<Partida> response = comparing(user, valorMaq, valorUser, idPart);
				response.setMensagem(
						"Maquina venceu com " + valorMaq + " e você perdeu com " + valorUser);
				comparing(user, valorMaq, valorUser, idPart);
				winMaq(partidaRepo.findById(idPart).orElse(null));
				return response;

			} else if (valorUser > valorMaq) {
				ResponseResource<Partida> response = comparing(user, valorMaq, valorUser, idPart);
				response.setMensagem("Você venceu com " + valorUser + " e "
						+ partidaRepo.getOne(idPart).getMaquina().getNome() + " perdeu com " + valorMaq);
				comparing(user, valorMaq, valorUser, idPart);
				winUser(partidaRepo.findById(idPart).orElse(null));
				return response;

			} else if (valorMaq == valorUser) {
				ResponseResource<Partida> response = comparing(user, valorMaq, valorUser, idPart);
				response.setMensagem(
						"Empate!!! Você com " + valorUser + " e maquina com " + valorMaq + ".");
				draw(partidaRepo.findById(idPart).orElse(null));
				return response;
			}

			ResponseResource<Partida> response = new ResponseResource<Partida>();
			response.setDados(partidaRepo.findById(idPart).orElse(null));
			response.setMensagem("Partida finalizada");
			return response;
		}
		return pFinalize(idPart);
	}

	public ResponseResource<Partida> tierOrBurst(Usuarios user, Integer valor, Integer idPart) {

		if (!partidaRepo.findById(idPart).get().getPartidaFinalizada()) {
			if (valor > 21) {
				if (!user.getEmail().equals("maq@gmail.com")) {
					ResponseResource<Partida> response = drawOver(user, valor, idPart);
					response.setMensagem("Você estourou com " + valor + ", maquina ganhou!");
					winMaq(partidaRepo.getOne(idPart));
					drawOver(user, valor, idPart);
					return response;
				} else {
					ResponseResource<Partida> response = drawOver(user, valor, idPart);
					response.setMensagem("Maquina estourou com " + valor + ", jogador ganhou!");
					winUser(partidaRepo.findById(idPart).orElse(null));
					drawOver(user, valor, idPart);
					return response;
				}
			} else if (valor == 21) {
				if (!user.getEmail().equals("maq@gmail.com")) {
					ResponseResource<Partida> response = drawOver(user, valor, idPart);
					response.setMensagem("Você ganhou com " + valor);
					winUser(partidaRepo.findById(idPart).orElse(null));
					drawOver(user, valor, idPart);
					return response;
				} else {
					ResponseResource<Partida> response = drawOver(user, valor, idPart);
					response.setMensagem("Maquina ganhou com " + valor);
					winMaq(partidaRepo.findById(idPart).orElse(null));
					drawOver(user, valor, idPart);
					return response;
				}
			}
			return proceed(partidaRepo.findById(idPart).orElse(null));
		}
		return pFinalize(idPart);
	}

	public ResponseResource<Partida> drawOver(Usuarios user, Integer valor, Integer idPart) {
		Partida p1 = partidaRepo.findById(idPart).orElse(null);
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		p1.setPartidaFinalizada(true);
		partidaRepo.save(p1);
		response.setDados(p1);
		return response;
	}

	public ResponseResource<Partida> comparing(Usuarios user, Integer valorMaq, Integer valorUser, Integer idPart) {
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		Partida p1 = partidaRepo.getOne(idPart);
		p1.setPartidaFinalizada(true);
		partidaRepo.save(p1);
		response.setDados(p1);
		return response;
	}

	public void winMaq(Partida partida) {
		partida.getMaquina().setVitorias(partida.getMaquina().getVitorias() + 1);
		partida.getJogador().setDerrotas(partida.getJogador().getDerrotas() + 1);
		usuarioRepo.flush();
	}

	public void winUser(Partida partida) {
		partida.getJogador().setVitorias(partida.getJogador().getVitorias() + 1);
		partida.getMaquina().setDerrotas(partida.getMaquina().getDerrotas() + 1);
		usuarioRepo.flush();
	}

	public void draw(Partida partida) {
		partida.getJogador().setEmpates(partida.getJogador().getEmpates() + 1);
		partida.getMaquina().setEmpates(partida.getMaquina().getEmpates() + 1);
		usuarioRepo.flush();
	}

	public ResponseResource<Partida> proceed(Partida partida) {
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		response.setDados(partida);
		response.setMensagem("Continuando...");
		return response;
	}

	public ResponseResource<Partida> pFinalize(Integer id) {
		ResponseResource<Partida> response = new ResponseResource<Partida>();
		response.setDados(partidaRepo.findById(id).orElse(null));
		response.setMensagem("Partida finalizada");
		return response;
	}
}
