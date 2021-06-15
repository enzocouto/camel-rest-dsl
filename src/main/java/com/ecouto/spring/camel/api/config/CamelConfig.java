package com.ecouto.spring.camel.api.config;

import javax.annotation.PostConstruct;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

@Configuration
public class CamelConfig {

	@Autowired
    private CamelContext context;
	
	@PostConstruct
    public void init() throws Exception {
        context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
         
    }
	

	@Bean
	private static MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource() {
	    MysqlConnectionPoolDataSource mysqlDs = new MysqlConnectionPoolDataSource();
	    mysqlDs.setDatabaseName("camel");
	    mysqlDs.setServerName("localhost");
	    mysqlDs.setPort(3306);
	    mysqlDs.setUser("camel");
	    mysqlDs.setPassword("Camel123");
	    return mysqlDs;
	}
}
