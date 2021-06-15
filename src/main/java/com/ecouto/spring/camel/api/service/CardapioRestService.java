package com.ecouto.spring.camel.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecouto.spring.camel.api.entity.Burguer;

@Service
public class CardapioRestService  { 

	private List<Burguer> list = new ArrayList<>();

	

	public Burguer addBurguer(Burguer burguer) {
		list.add(burguer);
		return burguer;
	}

	public List<Burguer> getCardapio() {
		return list;
	}
	

}
