package com.example.demo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ResponseResource;
import com.example.demo.domain.Usuarios;
import com.example.demo.domain.enums.Perfil;
import com.example.demo.dto.UsuariosDTO;
import com.example.demo.dto.UsuariosNewDTO;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.security.UserSS;
import com.example.demo.services.exceptions.AuthorizationException;
import com.example.demo.services.exceptions.DataIntegrityException;
import com.example.demo.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuariosRepository usuarioRepo;
	@Autowired
	private BCryptPasswordEncoder pe;

	public Usuarios find(Integer id) {
		UserSS userss = UserService.authenticated();

		if (userss == null || !id.equals(userss.getId()) && !userss.hasRole(Perfil.ADMIN)) {
			throw new AuthorizationException("Sem autorização para completar esta ação");
		}

		Optional<Usuarios> user = usuarioRepo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
	}

	public Usuarios findByEmail(String email) {
		Usuarios user = usuarioRepo.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("Usuario não existe");
		}
		return user;
	}

	public Usuarios fromDTO(UsuariosNewDTO objDto) {
		return new Usuarios(null, objDto.getNome(), objDto.getEmail(), pe.encode(objDto.getSenha()), 0, 0, 0,
				pe.encode(objDto.getPalavraChave()));
	}

	public Usuarios fromDTO(UsuariosDTO objDto, Integer id) {
		Usuarios user = find(id);
		try {
			return new Usuarios(null, objDto.getNome(), objDto.getEmail(), null, user.getVitorias(), user.getDerrotas(),
					user.getEmpates(), objDto.getPalavraChave());
		} catch (NullPointerException e) {
			throw new DataIntegrityException("Não deixe campos nulos");
		}
	}
	
	@Transactional
	public ResponseEntity<Usuarios> findEmailConfirmation(String email){
		Usuarios user = usuarioRepo.findByEmail(email);
		if (user != null) {
			throw new DataIntegrityException("E-mail already registered");		
		}
		return null;
	}

	public Usuarios update(Usuarios obj) {
		Usuarios newObj = find(obj.getIdUsuario());
		updateData(newObj, obj);
		return usuarioRepo.save(newObj);
	}

	public Usuarios insert(Usuarios obj) {
		obj.setIdUsuario(null);
		usuarioRepo.save(obj);
		return obj;
	}

	public void delete(Integer id) {
		try {
			usuarioRepo.deleteById(id);
			usuarioRepo.flush();
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
	}

	public void updateData(Usuarios newObj, Usuarios obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setPalavraChave(obj.getPalavraChave());
	}

	public ResponseResource<Usuarios> changePass(String senha, Usuarios user) {
		ResponseResource<Usuarios> response = new ResponseResource<Usuarios>();
		user.setSenha(pe.encode(senha));
		usuarioRepo.flush();
		response.setDados(user);
		response.setMensagem("Senha alterada com sucesso");
		return response;
	}

}
