package com.example.controller;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/product")
public class ProductsController {

	Integer size = 10;
	private final ProductService productService;
	
	public ProductsController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public String showProductsList(@RequestParam(name = "page",defaultValue = "0") Integer page, Model model) {

		// retrieve all ingredients from DB
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> products = productService.getProductList(pageable);
				
		model.addAttribute("products", products.getContent());
				
		return "products_table";
	}

	

}