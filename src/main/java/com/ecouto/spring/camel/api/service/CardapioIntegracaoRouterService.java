package com.ecouto.spring.camel.api.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.ecouto.spring.camel.api.entity.Burguer;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;


@Service
public class CardapioIntegracaoRouterService extends RouteBuilder implements ApplicationContextAware {

	
	@Autowired
	MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource;
	
    @Override
    public void configure() throws Exception {
    	
    	from("timer://rootsmanager?fixedRate=true&delay=1s&period=3600s")
    	
    	//Apagar todo cardapio
    	.setBody(simple("delete from burguer where id >=1"))
    	.to("jdbc:mysql") //usando o componente jdbc que envia o SQL para mysql
    	
    	.to("https://rootsmanager.herokuapp.com/burguer/consulta")	     	
    	.unmarshal(new JacksonDataFormat(Burguer[].class))
    	.split(body())
    	
    	.process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Burguer burguer = exchange.getIn().getBody(Burguer.class);
                exchange.setProperty("nome", burguer.getNome());
                exchange.setProperty("frase", burguer.getFrase());
                exchange.setProperty("ingredientes", burguer.getIngredientes());
                
                //Vendas pelo site tem 10% desconto
                BigDecimal deconto = burguer.getPreco().multiply(new BigDecimal(0.01));
                BigDecimal precoSite = burguer.getPreco().subtract(deconto);
                exchange.setProperty("preco", precoSite.setScale(2,BigDecimal.ROUND_HALF_DOWN));
            }
          })
    			    	
    	.setBody(simple("insert into burguer(nome, frase, ingredientes, preco) values ('${property.nome}', '${property.frase}', '${property.ingredientes}', '${property.preco}')"))
        .log("${body}") //logando o comando esql
        .to("jdbc:mysql"); //usando o componente jdbc que envia o SQL para mysql
    	
    }
    
  
	@Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        final ConfigurableApplicationContext context  = (ConfigurableApplicationContext)applicationContext;         
        final DefaultListableBeanFactory registry = (DefaultListableBeanFactory)context.getBeanFactory();
        registry.registerSingleton("mysql", mysqlConnectionPoolDataSource);
    }
	
}
