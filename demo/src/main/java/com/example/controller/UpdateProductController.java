package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import min.yue.data.Product;
import min.yue.repository.ProductRepository;

@Controller
public class UpdateProductController {

	private final ProductRepository productsRepo; // this is for us to retrieve Products from the DB

	@Autowired // inject this dependency into Spring Bean
	public UpdateProductController(ProductRepository productsRepo) {
		this.productsRepo = productsRepo;
	}

	@GetMapping("/add_product")
	public String showAddProductForm() {
		return "add_product"; // Assuming "add-product" is the Thymeleaf template name
	}

	// have to put empty products in the form first
	@ModelAttribute(name = "products")
	public Product order() {

		return new Product();

	}

	@PostMapping("/add_product")
	public String addProduct(@Valid @ModelAttribute("products") Product product, Errors errors) {

		if (errors.hasErrors()) {
			return "add_product";
		}

		productsRepo.save(product);

		return "redirect:/product"; // Redirect to the product listing page
	}
	
	
	@PostMapping("/edit_product_page")
	public String showEditProductForm(@RequestParam("productId") String itemId, Model model) {
		
		Product product = productsRepo.findById(itemId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + itemId));
		
		model.addAttribute("product", product);
		
		return "edit_product"; 
	}
	
	
	@PostMapping("/edit_product")
	public String editProduct(@Valid @ModelAttribute("products") Product product, Errors errors) {

		if (errors.hasErrors()) {
			return "edit_product";
		}
		
		
		Product editedProduct = productsRepo.findById(String.valueOf(product.getId()))
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + product.getId()));
		
        // Update fields with the values from the updatedEntity
		editedProduct.setName(product.getName());
		editedProduct.setDescription(product.getDescription());
		editedProduct.setBrand(product.getBrand());
		editedProduct.setCategory(product.getCategory());
		editedProduct.setType(product.getType());
		

        // Save the updated entity
   



		return "redirect:/product"; // Redirect to the product listing page
	}
}
