package com.example.demo.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuarios;
import com.example.demo.domain.enums.Perfil;
import com.example.demo.repository.UsuariosRepository;

@Service
public class DBService {

	@Autowired
	private UsuariosRepository usuarioRepo;
	@Autowired
	private CartasService cartaSer;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void instantiateTestDataBase() {
		Usuarios com = new Usuarios(1, "Maquina", "maq@gmail.com", bCryptPasswordEncoder.encode("123456"), 0, 0);
		Usuarios com1 = new Usuarios(null, "Giovani", "giovanijfc@gmail.com", bCryptPasswordEncoder.encode("123456"), 0, 0);
		com1.addPerfil(Perfil.ADMIN);
		usuarioRepo.saveAll(Arrays.asList(com,com1));
		cartaSer.newBaralho();
	}
}