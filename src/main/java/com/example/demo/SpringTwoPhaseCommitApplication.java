package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class SpringTwoPhaseCommitApplication {

	@Autowired @Qualifier("transactionManager2")PlatformTransactionManager manager;
	@Autowired @Qualifier("transactionManager1")PlatformTransactionManager manager1;
	public static void main(String[] args) {
		SpringApplication.run(SpringTwoPhaseCommitApplication.class, args);
	}
	
	@Bean("transactionManager")
	public PlatformTransactionManager transactionManager() {
		ChainedTransactionManager tx= new ChainedTransactionManager(new PlatformTransactionManager[]{ manager, manager1});
				return tx;
	}

}

