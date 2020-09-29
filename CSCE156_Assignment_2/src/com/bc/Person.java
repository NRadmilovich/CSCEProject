package com.bc;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
/**
 * 
 * @author nradm
 * Person Class, which includes file input, and HashMap conversion for use in Customer.
 */

public class Person  {
	private String personCode;
	private String firstName;
	private String lastName;
	private Address Address;
	private String[] Email;
	
	// Getters/ Setters
	public String getPersonCode() {
		return personCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return Address;
	}

	public String[] getEmail() {
		return Email;
	}
	// One setter kept, in order to assign an empty person to the customer class.  This allows the customers person code
	//to be retained, when no matching person is present.
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	//Constructors
	//General Constructor
	public Person() {
		super();
	}
	//Test constructor for variable arguments.
	public Person(Object[] args) {
		
	}
	//Constructor for String Address input, with email.
	public Person(String personCode, String Name, String address, String email) {
		super();
		String[] names = Name.split(",");
		this.personCode = personCode;
		this.Address = Address.stringToAddress(address);
		this.lastName = names[0];
		this.firstName = names[1];
		this.Email = email.split(",");
	}	
	//Constructor for Address class address, with email.
	public Person(String personCode, String Name, Address address, String Email) {
		super();
		String[] names = Name.split(",");
		this.personCode = personCode;
		this.lastName = names[0];
		this.firstName = names[1];
		this.Address = address;
		this.Email = Email.split(",");
	}	
	//Constructor for String Address, and no email.	
	public Person(String personCode, String Name, String address) {
		super();
		String[] names = Name.split(",");
		this.Email = null;
		this.personCode = personCode;
		this.lastName = names[0];
		this.firstName = names[1];
		this.Address = Address.stringToAddress(address);
		
	}
	//Constructor for Address class address, and no email.
	public Person(String personCode, String Name, Address Address) {
		super();
		String[] names = Name.split(",");
		this.Email[0] = "";
		this.personCode = personCode;
		this.lastName = names[0];
		this.firstName = names[1];
		this.Address = Address;
		
	}
	
	//Methods
	//Converts string to Person, while calling appropriate constructor.
 	public static Person stringToPerson(String input) {
		Person out = null;
		//Breaks string apart into token.
		String[] splitToken = input.split(";");
		// Checks for email address, and creates person to add based on result.
		if(splitToken[3].contains("@"))
			out = new Person(splitToken[0], splitToken[1], splitToken[2], splitToken[3]);
		else {
			out = new Person(splitToken[0], splitToken[1], splitToken[2]);
		}
		return out;
	}
	//Imports a file, and converts it into an ArrayList of class Person
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
		scanner.nextLine();
		while(scanner.hasNext()) {
			String Token = scanner.nextLine();
			String[] splitToken = Token.split(";");
			// Checks for the optional email address input, and creates a Person based on result.
			if(splitToken.length > 3)
				people.add(new Person(splitToken[0], splitToken[1], splitToken[2], splitToken[3]));
			else {
				people.add(new Person(splitToken[0], splitToken[1], splitToken[2]));
			}
			
		}
		scanner.close();
		return people;
	}
	/**Converts an ArrayList input to a HashMap, with the key being the personCode.
	 * This is for use with the Customer class, to assign primary contact info.
	 * @param list
	 * @return
	 */
	public static HashMap<String,Person> personMap(ArrayList<Person> list){
		HashMap<String,Person> map = new HashMap<String,Person>();
		for(Person p: list) {
			map.put(p.getPersonCode(),p);
		}
		return map;
	}
	@Override
	public String toString() {
		return "Person [personCode=" + personCode + ", firstName=" + firstName + ", lastName=" + lastName + ", Address="
				+ Address + ", Email=" + Arrays.toString(Email) + "]";
	}
	
}
