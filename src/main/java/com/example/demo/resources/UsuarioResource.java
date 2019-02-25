package com.example.demo.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.domain.Usuarios;
import com.example.demo.domain.enums.Perfil;
import com.example.demo.dto.UsuariosDTO;
import com.example.demo.dto.UsuariosNewDTO;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.security.UserSS;
import com.example.demo.services.UserService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.exceptions.AuthorizationException;

@RestController
@RequestMapping(value = "21/user")
public class UsuarioResource {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private UsuariosRepository usuarioRepo;
	@Autowired
	private UsuarioService usuarioSer;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuarios> find(@PathVariable Integer id) {
		UserSS userss = UserService.authenticated();
		if (userss == null || !userss.hasRole(Perfil.ADMIN) && !(userss.getId() == id)) {
			throw new AuthorizationException("Sem autorização para fazer essa ação");
		}

		Usuarios user = usuarioSer.find(id);
		return ResponseEntity.ok().body(user);
	}

	@RequestMapping(value = "/registrando", method = RequestMethod.POST)
	public ResponseEntity<Void> registro(@Valid @RequestBody UsuariosNewDTO objDto) {
		Usuarios user = usuarioSer.fromDTO(objDto);
		usuarioSer.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getIdUsuario())
				.toUri();
		user.setSenha(pe.encode(user.getSenha()));
		user.setPalavraChave(pe.encode(user.getPalavraChave()));
		usuarioRepo.flush();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuariosDTO userDTO, @PathVariable Integer id) {
		UserSS userss = UserService.authenticated();

		if (userss == null || !userss.hasRole(Perfil.ADMIN) && !(userss.getId() == id)) {
			throw new AuthorizationException("Sem autorização para fazer essa ação");
		}
		Usuarios user = usuarioSer.fromDTO(userDTO, id);
		user.setIdUsuario(id);
		user = usuarioSer.update(user);
		user.setPalavraChave(pe.encode(user.getPalavraChave()));
		usuarioRepo.flush();
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('Administrador')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		usuarioRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
