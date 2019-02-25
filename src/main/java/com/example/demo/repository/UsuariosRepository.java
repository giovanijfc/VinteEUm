package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository <Usuarios, Integer> {
		
	@Transactional(readOnly=true)
	Usuarios findByEmail(String email);
	
	@Transactional(readOnly=true)
	Usuarios findByPalavraChave(String palavraChave);
}
