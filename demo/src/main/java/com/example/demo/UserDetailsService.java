package com.example.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//user store for authentication purpose
public interface UserDetailsService {

	UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException;
}
