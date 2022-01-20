package com.learn.HashingWithAVLTree;

public class Student {

	private int Id;
	private String details;
	public Student(int id, String details) {
		super();
		Id = id;
		this.details = details;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
