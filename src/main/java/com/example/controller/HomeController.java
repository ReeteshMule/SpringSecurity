package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
	
	@GetMapping("/")
//	HttpServletResquest object 
	public String gree(HttpServletRequest request) {
		return "Hello Its Me HomeController" + request.getSession().getId();
	}

}
