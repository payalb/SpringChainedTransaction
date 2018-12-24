package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.PersonRepository;
import com.example.demo.dao1.PersonRepository1;
import com.example.demo.dto.Account;
import com.example.demo.dto.Person;

@RestController
public class MyController {
	@Autowired
	PersonRepository rep1;
	@Autowired
	PersonRepository1 rep2;

	@Transactional(propagation=Propagation.REQUIRES_NEW, transactionManager="txManager")
	@GetMapping("/save")
	public String save() {
		Person p = new Person(1, "Payal", new Account(123, 35353333.54f));
		rep1.save(p);
		rep2.save(p);
		return "Save success";
	}

	@GetMapping("/get")
	public String get() {
		System.out.println(rep1.findById(1));
		System.out.println(rep2.findById(1));
		return "Save success";
	}

}
