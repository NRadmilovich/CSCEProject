package com.bc;


public class Address {
	private String Street;
	private String City;
	private String State;
	private String Zip;
	private String Country;
	
	// Getters/Setters
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		this.Street = street;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		this.City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		this.State = state;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		this.Zip = zip;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	
	// Constructor
	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.Street = street;
		this.City = city;
		this.State = state;
		this.Zip = zip;
		this.Country = country;
	}
	public Address() {
		super();
	}
	// Method
	public static Address stringToAddress(String Address) {
		Address out = new Address();
		String[] Tokens = Address.split(",");
		out.Street = Tokens[0];
		out.City = Tokens[1];
		out.State = Tokens[2];
		out.Country = Tokens[4];
		if(Tokens[3] != null) {
			out.Zip = Tokens[3];
		}
		return out;
	}
	
	
}
