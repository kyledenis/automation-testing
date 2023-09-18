package com.planittesting.jupitertoys.model.data;

public record ContactData (
	String forename, 
	String surname, 
	String email, 
	String telephone, 
	String message) {}
