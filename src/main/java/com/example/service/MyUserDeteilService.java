package com.example.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.UserPrinciple;
import com.example.model.Users;
import com.example.repo.UserRepo;

@Service
public class MyUserDeteilService implements UserDetailsService{
	
	private UserRepo ur;

	public MyUserDeteilService(UserRepo ur) {
		super();
		this.ur = ur;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = ur.findByUsername(username);
		if(user == null) {
			System.err.println("username is not present"+username);
			throw new UsernameNotFoundException(username);
		}
	
		return new UserPrinciple(user);
	}

}
