package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repository.CartasRepository;

@SpringBootApplication
public class VinteEUmApplication {

	@Autowired
	CartasRepository cartaRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(VinteEUmApplication.class, args);
	}
	

	
}


