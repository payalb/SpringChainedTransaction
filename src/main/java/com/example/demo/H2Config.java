package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.example.demo.dao")
public class H2Config {
	
	 @Primary
	  @Bean(name = "dataSource")
	  @ConfigurationProperties(prefix = "spring.datasource")
	  public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	  }
	  
	  @Primary
	  @Bean(name = "entityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier("dataSource") DataSource dataSource
	  ) {
		  Map<String,String> properties=  new HashMap<>();
		  properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		  properties.put( "spring.jpa.show-sql","true");
		  properties.put(  "hibernate.hbm2ddl.auto","create");
		  return builder
	      .dataSource(dataSource)
	      .packages("com.example.demo.dto")
	      .properties(properties)
	      .persistenceUnit("h2")
	      .build();
	  }
	    
	  @Primary
	  @Bean(name = "transactionManager")
	  public PlatformTransactionManager transactionManager(
	    @Qualifier("entityManagerFactory") EntityManagerFactory 
	    entityManagerFactory
	  ) {
	    return new JpaTransactionManager(entityManagerFactory);
	  }
}
