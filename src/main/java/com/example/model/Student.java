package com.example.model;

public class Student {
	
	private Integer roll_no;
	private String name;
	private Integer marks;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(Integer roll_no, String name, Integer marks) {
		super();
		this.roll_no = roll_no;
		this.name = name;
		this.marks = marks;
	}
	public Integer getRoll_no() {
		return roll_no;
	}
	public void setRoll_no(Integer roll_no) {
		this.roll_no = roll_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMarks() {
		return marks;
	}
	public void setMarks(Integer marks) {
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "Student [roll_no=" + roll_no + ", name=" + name + ", marks=" + marks + "]";
	}
	
	

}
