package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQLDialect;
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
@EnableJpaRepositories(basePackages="com.example.demo.dao1")
public class PostgresConfig {
	
	  @Bean(name = "dataSource1")
	  @ConfigurationProperties(prefix = "spring.datasource1")
	  public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	  }
	  
	  @Bean(name = "entityManagerFactory1")
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier("dataSource1") DataSource dataSource
	  ) {
		  Map<String,String> properties=  new HashMap<>();
		  properties.put( "spring.jpa.show-sql","true");
		  properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		  properties.put(  "hibernate.hbm2ddl.auto","create");
		  return builder
	      .dataSource(dataSource)
	      .packages("com.example.demo.dto")
	      .properties(properties)
	      .persistenceUnit("postgres")
	      .build();
	  }
	    
	  @Primary
	  @Bean(name = "transactionManager1")
	  public PlatformTransactionManager transactionManager(
	    @Qualifier("entityManagerFactory1") EntityManagerFactory 
	    entityManagerFactory
	  ) {
	    return new JpaTransactionManager(entityManagerFactory);
	  }
}
