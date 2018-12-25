package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.example.demo.dao",
entityManagerFactoryRef="emf",
transactionManagerRef="transactionManager2")
public class H2Config {
	
	@Autowired Environment env;
    @Bean
    public DataSource dataSource1() {
  
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
 
        return dataSource;
    }
 
	  @Bean("emf")
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory2(
	  ) {
		  LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
		  HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	        em.setJpaVendorAdapter(vendorAdapter);
		  Map<String,String> properties=  new HashMap<>();
		  properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		  properties.put( "spring.jpa.show_sql","true");
		  properties.put(  "hibernate.hbm2ddl.auto","create");
		  properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQL9Dialect");
		  properties.put("hibernate.jdbc.lob.non_contextual_creation","true"); 
		  em.setDataSource(dataSource1());
	      em.setPackagesToScan("com.example.demo.dto");
	     em.setJpaPropertyMap(properties);
	     em.setPersistenceUnitName("postgres");
		  em.afterPropertiesSet();
		  return em;
	  }
	    
	  @Bean(name = "transactionManager2")
	  public PlatformTransactionManager transactionManager(
	  ) {
	    return new JpaTransactionManager(entityManagerFactory2().getObject());
	  }
}
