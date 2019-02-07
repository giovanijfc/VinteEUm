package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.CartasService;

@RestController
@RequestMapping(value="/partida")
public class PartidaResource {
	
	@Autowired
	CartasService cartaSer;
	
	@RequestMapping(value="/teste")
	public String teste() {
		cartaSer.embaralhando();
		return "                            Cartas inseridas com sucess!" ;
	}
	
}
