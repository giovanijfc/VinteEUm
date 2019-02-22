package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repository.CartasRepository;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.services.CartasService;

@SpringBootApplication
public class VinteEUmApplication implements CommandLineRunner {

	@Autowired
	CartasRepository cartaRepo;
	@Autowired
	UsuariosRepository usuarioRepo;
	@Autowired
	CartasService cartaSer;
	
	public static void main(String[] args) {
		SpringApplication.run(VinteEUmApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	}	
}


