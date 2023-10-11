package com.example.pojo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.repository.IngredientRepository;

//because ingredient check box gives string  th:value="${ingredient.id}" and we want to map the string into List<Ingredient>
//so we use converter
@Component // make it discoverable as Spring Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private final IngredientRepository ingredientRepo; // this is for us to retrieve Ingredient from the DB
	

	@Autowired // inject this dependency into Spring Bean
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
//Seems like Spring will automatically detect this converter and use it
	  @Override
	  public Ingredient convert(String id) {
	    return ingredientRepo.findById(id).orElse(null);
	  }

}
