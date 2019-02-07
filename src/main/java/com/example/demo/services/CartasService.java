package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cartas;
import com.example.demo.domain.enums.Naipes;
import com.example.demo.repository.CartasRepository;

@Service
public class CartasService {
	Naipes naipes;
	@Autowired
	CartasRepository cartaRepo;
	String ouros = "" + naipes.OUROS;
	
	
	public void embaralhando() {
		List<Cartas> cartas = new ArrayList<Cartas>();
		for(int i = 1; i < 10; ++i) {
			Naipes ouro = Enum.valueOf(Naipes.class, "COPAS");
			Cartas c = new Cartas(null,i,ouros);			
			cartas.add(c);				
		}
		
/*		for(int i = 1; i < 10; ++i) {		
			Cartas c = new Cartas(null,i,Naipes.ESPADAS);			
			cartas.add(c);	
		}
		
		for(int i = 1; i < 10; ++i) {
			Cartas c = new Cartas(null,i,Naipes.OUROS);			
			cartas.add(c);		
		}
		
		for(int i = 1; i < 10; ++i) {
			Cartas c = new Cartas(null,i,Naipes.PAUS);			
			cartas.add(c);	
		}
		
		for(int i = 0; i < 5; ++i) {
			if(i == 1) {
			int con = 11;
			Cartas c = new Cartas(null,con,Naipes.ÁS_COPAS);			
			cartas.add(c);	
			}else if(i == 2) {
				int con = 11;
				Cartas c = new Cartas(null,con,Naipes.ÁS_ESPADA);			
				cartas.add(c);		
			}else if(i == 3) {
				int con = 11;
				Cartas c = new Cartas(null,con,Naipes.ÁS_OURO);			
				cartas.add(c);	
			}else if (i ==4) {
				int con = 11;
				Cartas c = new Cartas(null,con,Naipes.ÁS_PAUS);			
				cartas.add(c);	
			}
		}
		
			int con = 10;
			Cartas c12 = new Cartas(null,con,Naipes.J_COPAS);			
			cartas.add(c12);
			Cartas c1 = new Cartas(null,con,Naipes.J_ESPADAS);			
			cartas.add(c1);
			Cartas c2 = new Cartas(null,con,Naipes.J_OUROS);			
			cartas.add(c2);
			Cartas c3 = new Cartas(null,con,Naipes.J_PAUS);			
			cartas.add(c3);
			Cartas c4 = new Cartas(null,con,Naipes.K_COPAS);			
			cartas.add(c4);
			Cartas c5 = new Cartas(null,con,Naipes.K_ESPADAS);			
			cartas.add(c5);
			Cartas c6 = new Cartas(null,con,Naipes.K_OUROS);			
			cartas.add(c6);
			Cartas c7 = new Cartas(null,con,Naipes.K_PAUS);			
			cartas.add(c7);
			Cartas c8 = new Cartas(null,con,Naipes.Q_COPAS);			
			cartas.add(c8);
			Cartas c9 = new Cartas(null,con,Naipes.Q_ESPADAS);			
			cartas.add(c9);
			Cartas c11 = new Cartas(null,con,Naipes.Q_OUROS);			
			cartas.add(c11);
			Cartas c10 = new Cartas(null,con,Naipes.Q_PAUS);			
			cartas.add(c10); */
			
			 cartaRepo.saveAll(cartas);
	}
		
}
	

