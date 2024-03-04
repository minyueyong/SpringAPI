package com.example.pojo;

import org.springframework.data.annotation.Id;
//import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;

//map to a table called Ingredient
//@Table  for Spring Data JDBC use
@Entity  //for spring data jpa
public class Ingredient {
	
	@Id
	private final String id;
	private final String name;
	private final Type type;
	
	public enum Type { 
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
	
	private Ingredient() {
		this.id = "";
		this.name = "";
		this.type = null;
		
	}
	
	public Ingredient(String id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}



}
