package com.bc;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
	private String customerCode;
	private String customerType;
	private String name;
	private Person primaryContact;
	private Address address;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Person primaryContact) {
		this.primaryContact = primaryContact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// Constructors
	// Constructor based on HashMap
	public Customer(String code, String type, String name, HashMap<String, Person> map, String contactCode,
			String address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = map.get(contactCode);
		this.address = new Address(address);

	}

	// Constructor based on All Strings
	public Customer(String code, String type, String name, String contact, String address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = Person.stringtoPerson(contact);
		this.address = new Address(address);
	}

	// Constructor based on Person input, String address
	public Customer(String code, String type, String name, Person contact, String address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = contact;
		this.address = new Address(address);
	}

	// Constructor based on Person input, Address input.
	public Customer(String code, String type, String name, Person contact, Address address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = contact;
		this.address = address;
	}

	// Constructor for no included primary contact
	public Customer(String code, String type, String name, String address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = new Person();
		System.out.println(address);
		this.address = new Address(address);
	}

	// Methods
	// Imports file and converts to ArrayList of Customers
	public static ArrayList<Customer> importCustomer(String filename, HashMap<String, Person> contacts) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Scanner s = null;
		String file = "data/" + filename;
		try {
			s = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		s.nextLine();
		while (s.hasNext()) {
			String Token = s.nextLine();
			String[] splitToken = Token.split(";");
			if (contacts.get(splitToken[3]) != null) {
				customers.add(new Customer(splitToken[0], splitToken[1], splitToken[2], contacts, splitToken[3],
						splitToken[4]));
			} else {
				Person p = new Person();
				p.setPersonCode(splitToken[3]);
				customers.add(new Customer(splitToken[0], splitToken[1], splitToken[2],p, splitToken[4]));
			}
		}
		s.close();
		return customers;
	}
}
