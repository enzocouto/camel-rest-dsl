package com.ecouto.spring.camel.api.resource;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.ecouto.spring.camel.api.processor.CardapioProcessor;
import com.ecouto.spring.camel.api.service.CardapioRestService;

@Component
public class CardapioResource extends RouteBuilder {

	@Autowired
	private CardapioRestService service;

	@BeanInject
	private CardapioProcessor processor;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").port(9090).host("localhost").bindingMode(RestBindingMode.json);

		rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE).route()
				.setBody(constant("Hello Word!")).endRest();

		rest().get("/getCardapio").produces(MediaType.APPLICATION_JSON_VALUE).route()
		.setBody(()->"select * from burguer")
	     .to("jdbc:mysql?useHeadersAsParameters=true")
				.endRest();

		//rest().post("/addCardapio").consumes(MediaType.APPLICATION_JSON_VALUE).type(Burguer.class).outType(Burguer.class)
		//		.route().process(processor).endRest();
	}

}
