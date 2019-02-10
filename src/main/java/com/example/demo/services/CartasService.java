package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CartaUsuarioPartida;
import com.example.demo.domain.Cartas;
import com.example.demo.domain.enums.Naipes;
import com.example.demo.repository.CartaUsuarioPartidaRepository;
import com.example.demo.repository.CartasRepository;

@Service
public class CartasService {
	
	
	List<Cartas> cartas = new ArrayList<Cartas>();
	
	Random rand = new Random();
	
	Naipes naipes;
	
	@Autowired
	CartasRepository cartaRepo;
	@Autowired
	CartaUsuarioPartidaRepository cartaUsuPar;
	
	List<CartaUsuarioPartida> carUP = new ArrayList<>();
	
	public void embaralhando() {
		
		
	}
	
	public void newBaralho() {
		for(int i = 2; i < 11; ++i) {			
			Cartas c = new Cartas(null,i,Naipes.COPAS.getDescricao());	
			cartas.add(c);	
			
		}
		
		for(int i = 2; i < 11; ++i) {		
			Cartas c = new Cartas(null,i,Naipes.ESPADAS.getDescricao());	
			cartas.add(c);	
		}
		
		for(int i = 2; i < 11; ++i) {
			Cartas c = new Cartas(null,i,Naipes.OUROS.getDescricao());	
			cartas.add(c);		
		}
	
		for(int i = 2; i < 11; ++i) {
			Cartas c = new Cartas(null,i,Naipes.PAUS.getDescricao());	
			cartas.add(c);	
		}
		
		for(int i = 0; i < 5; ++i) {
			if(i == 1) {
			int con = 11;
			Cartas c = new Cartas(null,con,Naipes.ÁS_COPAS.getDescricao());
			cartas.add(c);	
			}else if(i == 2) {
				int con = 11;
				Cartas c = new Cartas(null,con,Naipes.ÁS_ESPADA.getDescricao());
				cartas.add(c);		
			}else if(i == 3) {
				int con = 11;
				Cartas c = new Cartas(null,con,Naipes.ÁS_OURO.getDescricao());	
				cartas.add(c);	
			}else if (i == 4) {
				int con = 11;
				Cartas c = new Cartas(null,con,Naipes.ÁS_PAUS.getDescricao());	
				cartas.add(c);	
			}
		}
		
			int con = 10;
			Cartas c12 = new Cartas(null,con,Naipes.J_COPAS.getDescricao());
			cartas.add(c12);
			Cartas c1 = new Cartas(null,con,Naipes.J_ESPADAS.getDescricao());
			cartas.add(c1);
			Cartas c2 = new Cartas(null,con,Naipes.J_OUROS.getDescricao());		
			cartas.add(c2);
			Cartas c3 = new Cartas(null,con,Naipes.J_PAUS.getDescricao());
			cartas.add(c3);
			Cartas c4 = new Cartas(null,con,Naipes.K_COPAS.getDescricao());	
			cartas.add(c4);
			Cartas c5 = new Cartas(null,con,Naipes.K_ESPADAS.getDescricao());
			cartas.add(c5);
			Cartas c6 = new Cartas(null,con,Naipes.K_OUROS.getDescricao());	
			cartas.add(c6);
			Cartas c7 = new Cartas(null,con,Naipes.K_PAUS.getDescricao());
			cartas.add(c7);
			Cartas c8 = new Cartas(null,con,Naipes.Q_COPAS.getDescricao());
			cartas.add(c8);
			Cartas c9 = new Cartas(null,con,Naipes.Q_ESPADAS.getDescricao());
			cartas.add(c9);
			Cartas c11 = new Cartas(null,con,Naipes.Q_OUROS.getDescricao());
			cartas.add(c11);
			Cartas c10 = new Cartas(null,con,Naipes.Q_PAUS.getDescricao());	
			cartas.add(c10); 
			
			cartaRepo.saveAll(cartas);
			 
			 
			 
			 
	}
		
}
	

