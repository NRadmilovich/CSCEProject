package com.bc;

/**
 * @author nradm
 * 
 */
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

	// Getters and setters for Customer class.
	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getName() {
		return name;
	}

	public Person getPrimaryContact() {
		return primaryContact;
	}

	public Address getAddress() {
		return address;
	}

	// Constructors
	// Multiple constructors created to account for most input cases.
	// Constructor based on All Strings
	public Customer(String code, String type, String name, String contact, String address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = Person.stringToPerson(contact);
		this.address = Address.stringToAddress(address);
	}

	// Constructor based on Person input, String address
	public Customer(String code, String type, String name, Person contact, String address) {
		super();
		this.customerCode = code;
		this.customerType = type;
		this.name = name;
		this.primaryContact = contact;
		this.address = Address.stringToAddress(address);
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
			/**
			 * Uses the input HashMap of Persons, to assign primary contact info to the customer.  If no matching person is found,
			 * A new empty person is created with the appropriate person code for later assignment.
			 */
			
			if (contacts.get(splitToken[3]) != null) {
				Person primaryContact = contacts.get(splitToken[3]);
				customers.add(new Customer(splitToken[0], splitToken[1], splitToken[2], primaryContact,
						splitToken[4]));
			} else {
				Person p = new Person();
				p.setPersonCode(splitToken[3]);
				customers.add(new Customer(splitToken[0], splitToken[1], splitToken[2], p, splitToken[4]));
			}
		}
		s.close();
		return customers;
	}
	// Creates a HashMap of customers mapped to their customer codes.
	public static HashMap<String,Customer> customerMap(ArrayList<Customer> list){
		HashMap<String,Customer> map = new HashMap<String,Customer>();
		for(Customer c: list) {
			map.put(c.getCustomerCode(),c);
		}
		return map;
	}
	public static Customer stringToCustomer(String input) {
		Customer out = null;
		//Breaks string apart into token.
		String[] splitToken = input.split(";");
		// Checks for email address, and creates person to add based on result.
		Person primaryContact = Person.stringToPerson(splitToken[3]);
		Address address = Address.stringToAddress(splitToken[4]);
		out = new Customer(splitToken[0], splitToken[1],splitToken[2],primaryContact, address);
		return out;
	}
	
	public double getLoyalDiscount(double totalAfterTax) {
		
		// Check if primary contact has more than 1 email
		// System.out.println(this.primaryContact.getEmail().length);
		if (this.primaryContact.getEmail().length > 1) {
			return (-.05 * totalAfterTax);
		} 
		
		return 0;
	}

	public double getFees() {
		
		// Determine fee based on Customer Type
		if (this.customerType.equals("B")) {
			return 75.50;
			
		}else if (this.customerType.equals("P")) {
			return 0;
		}
		return 0;
	}

	public double getTaxes(double totalBeforeTax) {
		
		// Determine Taxes based on Customer Type
		if (this.customerType.equals("B")) {
			return .0425 * totalBeforeTax;
			
		}else if (this.customerType.equals("P")) {
			return .08 * totalBeforeTax;
		}
		return 0;
		
	}
}
