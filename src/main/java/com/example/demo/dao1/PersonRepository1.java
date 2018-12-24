package com.example.demo.dao1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.Person;

@Transactional(propagation=Propagation.REQUIRED, transactionManager="transactionManager1")
public interface PersonRepository1 extends CrudRepository<Person, Integer>{

}
