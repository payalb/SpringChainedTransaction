package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.Person;
@Transactional(propagation=Propagation.REQUIRED, transactionManager="transactionManager2")
public interface PersonRepository extends CrudRepository<Person, Integer>{

}
