package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.pojo.User;

														//<entity , data type of the @Id 
public interface UserRepository extends CrudRepository <User, Long>{
	
	User findByUsername ( String username );

}
