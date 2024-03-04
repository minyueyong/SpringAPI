package com.example.service;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.example.pojo.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;



@Service // discover by spring component scanning and instantiated as a bean
public class ProductService  {

	
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Page<Product> getProductList (Pageable pageable){
		
	
		return productRepository.findAll( pageable);
	}
	
	



}
