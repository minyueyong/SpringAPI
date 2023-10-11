package com.example.jdbcrepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.pojo.Ingredient;
import com.example.repository.IngredientRepository;

//jdbcTemplate is preferable over jdbcRepository but Spring Data JDBC is better
@Repository    //discover by spring component scanning and instantiated as a bean
public class JdbcIngredientRepository implements IngredientRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	
	//Autowired automatically  inject a dependency into Spring Bean
	@Autowired   //spring will automatically look for bean type JDBCTemplate  and inject it into constructor
	public JdbcIngredientRepository ( JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Ingredient> findAll() {
		// Accept SQL query and a RowMapper
		//Result return by this query is then pass to the mapRowToIngredient method
		return jdbcTemplate.query("select id , name,type from Ingredient", this::mapRowToIngredient);
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		// Parameters are ( query, Row Mapper, id )
		//after getting result of the query for that id , pass it to row mapper
		List <Ingredient> results = jdbcTemplate.query("select id, name, type from Ingredient where id=? ", this::mapRowToIngredient, id);
		return results.size() == 0 ? Optional.empty(): Optional.of(results.get(0));
	}
	
	
	//Purpose of RowMapper is to map each row in the Result Set to an object
	private Ingredient mapRowToIngredient ( ResultSet row, int rowNum) throws SQLException {
			return new Ingredient (
				row.getString("id"),
				row.getString("name"),
				Ingredient.Type.valueOf(row.getString("type")));
			}
		
	

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbcTemplate.update("insert into Ingredient ( id, name, type ) values ( ? , ?, ?)",
							ingredient.getId(),
							ingredient.getName(),
							ingredient.getType().toString());
		return ingredient;
	}

	@Override
	public <S extends Ingredient> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Ingredient> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Ingredient entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Ingredient> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
