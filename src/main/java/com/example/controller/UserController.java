package com.example.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Users;
import com.example.service.UserService;

@RestController
public class UserController {
	
	private UserService us;
	
	public UserController(UserService us) {
		super();
		this.us = us;
	}

	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return us.register(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
//		System.out.println("user is "+user);
		return us.verify(user);
		
	}
	
	@PutMapping("/update/{id}")
	public Users update(@PathVariable int id, @RequestBody Users user) {
		return us.update(id,user);
	}

}
