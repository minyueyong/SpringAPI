package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.jdbcrepository.JdbcIngredientRepository;
import com.example.pojo.Ingredient;
import com.example.pojo.Ingredient.Type;
import com.example.pojo.Taco;
import com.example.pojo.TacoOrder;
import com.example.repository.IngredientRepository;

import jakarta.validation.Valid;

//This controller provide model attribute for the design form

//mark it as controller for component scanning
@Controller
@RequestMapping("/design") // general purpose request handling, can be POST or GET
@SessionAttributes("tacoOrder") // make sure that tacoOrder is maintained in session so that it can span
								// multiple request
//if tacoOrder is used across multiple HTML pages or steps in a process, 
//@SessionAttributes("tacoOrder") ensures that the tacoOrder attribute is maintained in the session and can be accessed and updated as needed as the user interacts with different parts of your application.
public class DesignTacoController {

//---------------------------------------------------------------------------------------------------------

	private final IngredientRepository ingredientRepo; // this is for us to retrieve Ingredient from the DB

	@Autowired // inject this dependency into Spring Bean
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

//-------------------------------------------------------------------------------------------------------

	@ModelAttribute
	public void addIngredientsToModel(Model model) {

		// retrieve all ingredients from DB
		Iterable<Ingredient> ingredients = ingredientRepo.findAll();

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {

			// this will return ( type , List of ingredients for that type )
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

	}

	// once you call path /design will auto power up empty taco order
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {

		return new TacoOrder();

	}

	// once you call path /design will auto power up empty taco
	//provide an empty taco object to design.html for form submission
	@ModelAttribute(name = "taco")
	public Taco taco() {

		return new Taco();
	}

	// Return a list based on the types, example if the type is wrap , then return { Flour Totilla , Corn Totilla }
	private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
		return StreamSupport.stream(ingredients.spliterator(), false)
				.filter(i -> i.getType().equals(type))
				.collect(Collectors.toList());
	}

//---------------------------------------------------------------------------------------------

	// this method will handle the /design request
	@GetMapping
	public String showDesignForm() {
		return "design";
	}

	// Add taco object inside tacoOrder
	@PostMapping
	// add @valid is to perform validation, validation rules inside the Taco class
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {

		if (errors.hasErrors()) {

			System.out.println("process taco has errors " + errors.getFieldErrors());
			return "design";
		}

		tacoOrder.addTaco(taco);
		// redirect to a page with mapping /orders/current
		// see OrderController.java
		return "redirect:/orders/current";
	}

}
