package com.bc;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Person {
	private String personCode;
	private String Name;
	private Address Address;
	private String[] Email;
	
	// Getters/ Setters
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public Address getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		this.Address = new Address(address);
	}
	public void setAddress(Address address) {
		this.Address = address;
	}
	public String[] getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		this.Email = email.split(",");
	}
	
	//Constructors
	//Constructor for String Address input, with email.
	public Person(String personCode, String Name, String Address, String email) {
		super();
		this.personCode = personCode;
		this.Name = Name;
		this.Address = new Address(Address);
		this.Email = email.split(",");
	}	
	//Constructor for Address class address, with email.
	public Person(String personCode, String Name, Address Address, String Email) {
		super();
		this.personCode = personCode;
		this.Name = Name;
		this.Address = Address;
		this.Email = Email.split(",");
	}	
	//Constructor for String Address, and no email.	
	public Person(String personCode, String Name, String Address) {
		super();
		this.Email[0] = "";
		this.personCode = personCode;
		this.Name = Name;
		this.Address = new Address(Address);
		
	}
	//Constructor for Address class address, and no email.
	public Person(String personCode, String Name, Address Address) {
		super();
		this.Email[0] = "";
		this.personCode = personCode;
		this.Name = Name;
		this.Address = Address;
		
	}

	public static ArrayList<Person> importPerson(String filename) {
		ArrayList<Person> people = new ArrayList<Person>();
		Scanner scanner = null;
		String fileString = "data/" + filename;
		File file = new File(fileString);
		try {
		scanner = new Scanner(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.nextInt();
		while(scanner.hasNext()) {
			String Token = scanner.nextLine();
			String[] splitToken = Token.split(";");
			// Checks for email address, and creates person to add based on result.
			if(splitToken[3].contains("@"))
				people.add(new Person(splitToken[0], splitToken[1], splitToken[2], splitToken[3]));
			else {
				people.add(new Person(splitToken[0], splitToken[1], splitToken[2]));
			}
			
		}
		scanner.close();
		return people;
	}
	
}
