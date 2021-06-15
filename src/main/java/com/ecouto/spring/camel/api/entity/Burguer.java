package com.ecouto.spring.camel.api.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Burguer {

	private String id;
	private String nome;
	private String frase;
	private String ingredientes;
	private BigDecimal preco;
	private String img;
	

}