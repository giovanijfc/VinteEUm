package com.example.demo.services;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

	public Usuarios fromDTO(UsuariosNewDTO objDto) {
		return new Usuarios(null, objDto.getNome(), objDto.getEmail(), objDto.getSenha(), 0, 0, 0,
				objDto.getPalavraChave());
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

	public Usuarios update(Usuarios obj) {
		Usuarios newObj = find(obj.getIdUsuario());
		updateData(newObj, obj);
		return usuarioRepo.save(newObj);
	}

	public void insert(Usuarios obj) {
		obj.setIdUsuario(null);
		try {
			usuarioRepo.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Email já cadastrado");
		} catch (ConstraintViolationException e) {
			throw new DataIntegrityException("Nome maior de 6 caracteres(Sem caracteres especiais), palavraChave maior que 3 caracteres"
					+ " e senha maior que 6 caracteres... ",
					e.getCause());
		}
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
