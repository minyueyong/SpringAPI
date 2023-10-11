package com.example.pojo;

//import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;

//link taco to an ingredient Row
//map to a table called Ingredient_Ref
@Entity //for Spring Data JDBC use
public class IngredientRef {
	
	private final String ingredient = "";

	public IngredientRef(String id) {
		// TODO Auto-generated constructor stub
	}

	public String getIngredient() {
		return ingredient;
	}

}
