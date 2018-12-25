package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Transactional( transactionManager="transactionManager")
	@GetMapping("/save")
	public String save() {
		Person p = new Person(1, "Payal", new Account(123, 35353333.54f));
		rep2.save(p);
		rep1.save(p);
		return "Save success";
	}

	@GetMapping("/get")
	public List<Person> get() {
		Person p1=rep1.findById(1).get();
		Person p2=rep2.findById(1).get();
		return Arrays.asList(p1,p2);
	}

}
