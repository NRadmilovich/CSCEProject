package com.bc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
/**
 * Assignment 3
 * 
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Person class stores primary contact information for BumprCars.  It can be passed to an Invoice or Customer class.
 */

public class Person  {
	private String personCode;
	private String firstName;
	private String lastName;
	private Address Address;
	private ArrayList<String> Email;
	
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

	public ArrayList<String> getEmail() {
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
	//Constructor
	public Person(String personCode, String lastName,String firstName, Address address, ArrayList<String> email) {
		super();
		this.personCode = personCode;
		this.Address = address;
		this.lastName = lastName;
		this.firstName = firstName;
		this.Email = email;
	}	
	
	//Methods
	//Converts string to Person, while calling appropriate constructor.
 	public static Person stringToPerson(String input) {
		Person out = null;
		//Breaks string apart into token.
		String[] splitToken = input.split(";");
		String[] emailStrings = null;
		ArrayList<String> emails = new ArrayList<String>();
		if(splitToken.length > 3) {
			emailStrings = splitToken[3].split(",");
			List<String> convert = Arrays.asList(emailStrings);
			emails.addAll(convert);
		}
		String[] names = splitToken[1].split(",");
		Address address = com.bc.Address.stringToAddress(splitToken[2]);
		// Checks for email address, and creates person to add based on result.
		out = new Person(splitToken[0], names[0], names[1], address, emails);
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
			people.add(stringToPerson(Token));	
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
		return lastName + ", " + firstName;
	}
	
}
