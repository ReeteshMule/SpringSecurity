package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	
	private List<Student> students = new ArrayList<>(List.of(
			new Student(1,"reetesh",60),
			new Student(2, "akash", 70)
			));
	
	@GetMapping("/student")
	public List<Student> getListStudent(){
		return students ;
		
	}
	
	@GetMapping("/csrf-Token")
	public CsrfToken ct(HttpServletRequest hrs) {
		return (CsrfToken) hrs.getAttribute("_csrf");		
	}
	
	
	@PostMapping("/student")
	public Student addStudent(@RequestBody Student student){
		students.add(student);
		return student;
	}

}
