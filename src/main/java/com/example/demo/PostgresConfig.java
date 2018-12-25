package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages="com.example.demo.dao1",
entityManagerFactoryRef="entityManagerFactory",
transactionManagerRef="transactionManager1")
public class PostgresConfig {

	@Autowired Environment env;
	@Primary
    @Bean
    public DataSource dataSource2() {
  
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("spring.datasource1.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource1.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.datasource1.username"));
        dataSource.setPassword(env.getProperty("spring.datasource1.password"));
 
        return dataSource;
    }
 
	  @Primary
	  @Bean("entityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory1(
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
		  em.setDataSource(dataSource2());
	      em.setPackagesToScan("com.example.demo.dto");
	     em.setJpaPropertyMap(properties);
	     em.setPersistenceUnitName("postgres1");
	    
		  em.afterPropertiesSet();
		  return em;
	  }
	    
	  @Bean(name = "transactionManager1")
	  public PlatformTransactionManager transactionManager(
	  ) {
	    return new JpaTransactionManager(entityManagerFactory1().getObject());
	  }
}
