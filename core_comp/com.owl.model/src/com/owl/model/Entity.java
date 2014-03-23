package com.owl.model;

public class Entity {
	
	private String name;
	private String address;
	private String category;
	private String review;
	private float rating;
	
	public Entity(String name, String address, String category, String review, float rating) {
		this.name = name;
		this.address = address;
		this.category = category;
		this.review = review;
		this.rating = rating;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	
}
