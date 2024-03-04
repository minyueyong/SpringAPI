package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.pojo.User;
import com.example.repository.UserRepository;

@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder( ) {
		return new BCryptPasswordEncoder();    //applies bcrypt strong hashing encryption to encode password
	}
/*	Keep user in memory and encode their password
	@Bean
	public InMemoryUserDetailsManager userDetailService ( PasswordEncoder encoder ) {
		List <UserDetails> usersList = new ArrayList<>();
		usersList.add( new User ( "buzz", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
		
		usersList.add( new User ( "woody", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
		
		
		return new InMemoryUserDetailsManager(usersList);
	}  */
	
	//Keep user in DB
	@Bean 
	UserDetailsService userDetailsService ( UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			
			if ( user != null ) return user;
			
			throw new UsernameNotFoundException ( "User " + username + " not found ");
		};
	}
	
	
	
	
	//make sure user is authenticated before designing or placing taco orders
	// unauthenticated users can access home ,login and registration page
	@Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception{
		return http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers("/design" , "/orders").hasRole("USER")
				.requestMatchers("/" , "/**").permitAll()
				
		).httpBasic(Customizer.withDefaults())
			    .formLogin(form -> form
						.loginPage("/login")   //specify a custom login page
						.defaultSuccessUrl("/design", true)    //if successfully authenticated will go to design page
						//Set true is to force the user to go to design page even though they might be navigating other page before login
						.loginProcessingUrl("/authenticate")
						.usernameParameter("user")
						.passwordParameter("pwd"))
			    		.logout((logout) -> logout.logoutUrl("/"))
			    		.build();
		
	}

}
