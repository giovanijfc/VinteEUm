package com.example.demo.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.ResponseResource;
import com.example.demo.domain.Usuarios;
import com.example.demo.dto.ChangedNewPassDTO;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.security.JWTUtil;
import com.example.demo.security.UserSS;
import com.example.demo.services.UserService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.exceptions.DataIntegrityException;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private UsuariosRepository repo;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new DataIntegrityException("Sem usuario logado");
		}
		String token = jwtUtil.generateToken(user.getUsername());

		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.PUT)
	public ResponseResource<Usuarios> forgot(@RequestBody ChangedNewPassDTO objDto) {
		Usuarios user = repo.findByEmail(objDto.getEmail());
		if (user == null) {
			throw new DataIntegrityException("Email incorreto!");
		}
		if ((pe.matches(objDto.getPalavraChave(), user.getPalavraChave()))
				&& objDto.getEmail().equals(user.getEmail())) {
			return usuarioService.changePass(objDto.getNewSenha(), user);
		}
		throw new DataIntegrityException("FALHA NA TENTATIVA DE AUTENTICAÇÃO");
	}
}
