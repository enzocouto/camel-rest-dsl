package com.ecouto.spring.camel.api.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecouto.spring.camel.api.entity.Burguer;
import com.ecouto.spring.camel.api.service.CardapioRestService;

@Component
public class CardapioProcessor implements Processor{
	
	@Autowired
	private CardapioRestService service;

	@Override
	public void process(Exchange exchange) throws Exception {
		service.addBurguer(exchange.getIn().getBody(Burguer.class));
	}

}
