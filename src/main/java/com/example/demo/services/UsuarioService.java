package com.example.demo.services;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	private BCryptPasswordEncoder pe;
	@Autowired
	private UsuariosRepository usuarioRepo;
	
	public Usuarios find(Integer id) {
		UserSS userss = UserService.authenticated();
		
		if(userss == null || !userss.hasRole(Perfil.ADMIN) && !id.equals(userss.getId())) {
			throw new AuthorizationException("Sem autorização para completar essa ação");
		}
		
		Optional<Usuarios> user = usuarioRepo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
	}
	
	public Usuarios fromDTO(UsuariosNewDTO objDto) {
		return new Usuarios(null, objDto.getNome(), objDto.getEmail(), 
							pe.encode(objDto.getSenha()), null, null);
	}
	public Usuarios fromDTO(UsuariosDTO objDto) {
		return new Usuarios(null, objDto.getNome(), objDto.getEmail(), 
							null, null, null);
	}
	
	public Usuarios update(Usuarios obj) {
		Usuarios newObj = find(obj.getIdUsuario());
		updateData(newObj, obj);
		return usuarioRepo.save(newObj);
	}
	
	public void insert(Usuarios obj) {
		obj.setIdUsuario(null);		
		obj.setVitorias(0);
		obj.setDerrotas(0);
		try {
			usuarioRepo.save(obj);
		}catch(DataIntegrityViolationException e) {
			 throw new DataIntegrityException("Email ou nome já cadastrado");
		}catch(ConstraintViolationException e) {
			throw new DataIntegrityException("Não introduza caracteres especiais e tenha o nome maior que 6 caracteres", e.getCause());
		}
    } 
	
	public void delete(Integer id) {
		try {
		usuarioRepo.deleteById(id);
		usuarioRepo.flush();
		}catch(ObjectNotFoundException e){
			throw new ObjectNotFoundException("Objeto não encontrado");
		}	
	}
	
	public void updateData(Usuarios newObj, Usuarios obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}



