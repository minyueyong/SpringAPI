package com.example.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


import com.example.pojo.Ingredient;

												//use them with Spring Data JDBC
public interface IngredientRepository  extends CrudRepository <Ingredient, String>{    //fetches and save Ingredient data ,<entity , data type of the @Id >
	
	//Spring Data JDBC automatically generate implementations for you
	
	Iterable <Ingredient> findAll();
	Optional <Ingredient> findById ( String id );   //might return ingredient that match the id if found , return Optional if not found
	Ingredient save (Ingredient ingredient);

}
