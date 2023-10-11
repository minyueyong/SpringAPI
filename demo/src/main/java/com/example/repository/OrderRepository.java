package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.pojo.TacoOrder;

											//use them with Spring Data JDBC
public interface OrderRepository extends CrudRepository <TacoOrder, Long> {  //<entity , data type of the @Id >
	
	//Spring Data JDBC automatically generate implementations for you
	TacoOrder save ( TacoOrder order);
	
	//you can add custom implementation besides CRUD
	//Spring is like an AI . It can guess the method purpose by the text
	List <TacoOrder> findByDeliveryZip ( String deliveryZip );
	
	List <TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween ( String deliveryZip, Date startDate, Date endDate);

	//If your query is too complicated for Spring to guess. You can do this 
	@Query("Order o where o.deliveryCity = 'Seattle'")
	List < TacoOrder > readOrdersDeliveredInSeattle ( );
}
