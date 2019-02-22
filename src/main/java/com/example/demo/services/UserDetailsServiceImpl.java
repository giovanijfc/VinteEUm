package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuarios;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuariosRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuarios user = repo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Email [" + email+ "] não cadastrado" );
		}
		return new UserSS(user.getIdUsuario(), user.getEmail(), user.getSenha(), user.getPerfis());
	}

}
