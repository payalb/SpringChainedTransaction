package com.example.demo.dto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Person {
	@Id
	private int id;
	private String name;
	@Embedded
	private Account account;
}
