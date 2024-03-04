package com.example.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
//import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//map to a table called Taco
//@Table // for Spring Data JDBC use
@Entity
public class Taco {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;

	private Date createdAt = new Date();

	@NotNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;

	@NotNull
	@Size(min = 1, message = "You must choose at least 1 ingredient")
	@ManyToMany()
	private List<IngredientRef> ingredients;

	//seems like spring will automatically know how to convert ingredient into ingredient ref here after using the 
	//converter to convert id to Ingredient
	public void addIngredient(Ingredient taco) {
		this.ingredients.add(new IngredientRef(taco.getId()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IngredientRef> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientRef> ingredients) {
		this.ingredients = ingredients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
