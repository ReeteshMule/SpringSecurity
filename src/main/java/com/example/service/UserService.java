package com.example.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Users;
import com.example.repo.UserRepo;

@Service
public class UserService {
	
	private UserRepo ur;
	public UserService(UserRepo ur) {
		super();
		this.ur = ur;
	}
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwts;
	
	
	private BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(12);

	public Users register(Users user) {
		user.setPassword(bpe.encode(user.getPassword()));
		return ur.save(user);
	}

	public Users update(int id, Users user) {
		Users users = ur.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
//		users.setId(user.getId());
//		users.setUsername(user.getUsername());
		users.setPassword(bpe.encode(user.getPassword()));
//		users.setPassword(bpe.encode(password));
		return ur.save(users);
	}

	public String verify(Users user) {
	    Authentication authentication =
	    		authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                    user.getUsername(),
	                    user.getPassword()
	                ));
		
		if(authentication.isAuthenticated()) {
			return jwts.generateToken(user.getUsername());
		}
		return "Fail";
			
	}


}
