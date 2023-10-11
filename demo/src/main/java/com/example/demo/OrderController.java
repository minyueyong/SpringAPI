package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.jdbcrepository.JdbcOrderRepository;
import com.example.pojo.TacoOrder;

import jakarta.validation.Valid;

@Controller /*when you add this annotation Spring Component Scanning will automatically discovers it and create a OrderController
as a bean in the Application Context */
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	
	private JdbcOrderRepository orderRepo;
	
	@GetMapping("/current")
	public String orderForm ( ) {
		return"orderForm";
	}
	
	@PostMapping
	public String processOrder ( @Valid TacoOrder order , Errors errors, SessionStatus sessionStatus) {
		
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		orderRepo.save(order);
		//ensure that session is cleaned up and ready for a new order next time
		sessionStatus.setComplete();
		
		
		//here will redirect to design form
		return "redirect:/";
	}

}
