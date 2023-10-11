package com.example.jdbcrepository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.pojo.IngredientRef;
import com.example.pojo.Taco;
import com.example.pojo.TacoOrder;
import com.example.repository.OrderRepository;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private JdbcOperations jdbcOperations;

	@Autowired
	public JdbcOrderRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	@Transactional   //
	public TacoOrder save(TacoOrder order) {  //the tacoOrder here inside already have list of tacos
		
		// put in query  and values type
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Taco_Order" + "(delivery_name, delivery_street, delivery_city,"
						+ "delivery_state, delivery_zip, cc_number, " + "cc_expiration, cc_cvv, placed at ) "
						+ "values(? , ? , ? ,?,?,?,?,?,?)",

				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.TIMESTAMP);

		pscf.setReturnGeneratedKeys(true);

		order.setPlacedAt(new Date());

		//Put the values inside TacoOrder into the query
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(order.getDeliveryName(),
				order.getDeliveryStreet(), order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(),
				order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt()

		)

		);
		
		//will store the id of the taco order after it has been saved
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder ();
		jdbcOperations.update(psc, keyHolder);
		
		//after calling jdbc update we return the order id and set it to the order
		long orderId = keyHolder.getKey().longValue();	
		order.setId(orderId);
		
		List<Taco> tacos = order.getTacos();
		
		int i=0;
		for ( Taco taco : tacos) {
			saveTaco( orderId, i++, taco);    //now will save the taco one by one also label each tacos with their order id
		}
		
		return order;
		
	}
	
	private Long saveTaco ( Long orderId, int orderKey, Taco taco) {
		taco.setCreatedAt(new Date());
		
		PreparedStatementCreatorFactory pscf =
				new PreparedStatementCreatorFactory (
						
					"insert into Taco"
					+"(name , created_at, taco_order, taco_order_key"
					+"values ( ?,?,?,?)",
					
					Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
						
				);
		
		pscf.setReturnGeneratedKeys(true);
		
		//insert all the tacos values inside the query
		PreparedStatementCreator psc = 
				pscf.newPreparedStatementCreator(Arrays.asList(
						
						taco.getName(),
						taco.getCreatedAt(),
						orderId,
						orderKey           //order key is the chronological order of the taco order e.g. 1 , 2 , 3
						
				));
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder ();
		jdbcOperations.update(psc, keyHolder);
		long tacoId = keyHolder.getKey().longValue();
		taco.setId(tacoId);
		
		
		
		saveIngredientRefs( tacoId, taco.getIngredients());
		
		return tacoId;
				
	}
	
	private void saveIngredientRefs ( long tacoId, List <IngredientRef> ingredientRefs ) {
		
		int key = 0 ; 
		
		for ( IngredientRef ingredientRef: ingredientRefs) {
			jdbcOperations.update(
					"insert into Ingredient_Ref ( ingredient, taco, taco_key ) "  //taco key is the chronological order of the taco e.g. 1 , 2 , 3
					+ "values ( ? , ? , ? )",
					ingredientRef.getIngredient() , tacoId, key++ );
					
		}
	}

	@Override
	public <S extends TacoOrder> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TacoOrder> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<TacoOrder> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TacoOrder> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TacoOrder entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends TacoOrder> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TacoOrder> findByDeliveryZip(String deliveryZip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TacoOrder> readOrdersDeliveredInSeattle() {
		// TODO Auto-generated method stub
		return null;
	}

}
