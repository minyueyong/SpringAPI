package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




//Composite annotation of @SpringBootConfiguration , @EnableAutoConfiguration, @ComponentScan
//@SpringBootConfiguration - Designate this class as the configuration class
//@EnableAutoConfiguration - enable automatic configuration for any components that it thinks you'll need
//@ComponentScan - enables components scanning and automatically discover and register them as components in Spring Application Context
@SpringBootApplication  
@ComponentScan(basePackages = "com.example")   //can be put into a @Configuration file too
public class BookStoreApplication implements WebMvcConfigurer{

	//method that will be run when JAR file is executed
	public static void main(String[] args) {
		
	//this will create Spring Application Context
	SpringApplication.run(BookStoreApplication.class, args);
		
	}
	
	@Override
	public void addViewControllers ( ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}


}

/*Spring Application Context is a container that manages application components also called beans
Container creates and maintain all components and inject into the beans that needs them 
Act of wiring beans together is called dependency injection   */
