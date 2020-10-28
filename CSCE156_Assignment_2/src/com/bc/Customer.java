package com.bc;

/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Customer class stores data about BumprCars customers.  It uses the Person class and Address class to accomplish this.
 */
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	// Getters for Customer class.
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
	// Constructor
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
			Address address = Address.stringToAddress(splitToken[4]);
			if (contacts.get(splitToken[3]) != null) {
				Person primaryContact = contacts.get(splitToken[3]);
				customers.add(new Customer(splitToken[0], splitToken[1], splitToken[2], primaryContact,
						address));
			} else {
				Person p = new Person();
				p.setPersonCode(splitToken[3]);
				customers.add(new Customer(splitToken[0], splitToken[1], splitToken[2], p, address));
			}
		}
		s.close();
		return customers;
	}
	public static ArrayList<Customer> importCustomerDB(HashMap<String,Person> personMap){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		// Build connections
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		String query = "Select customerCode, customerType, customerName, Person.personCode, Customer.address from Customer\r\n"
				+ "left join Person on Person.personId = Customer.primaryContact";
		try {
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();
			while(rs.next()) {
				Address address = com.bc.Address.getAddressDB(rs.getInt("address"));
				Customer customer = new Customer(rs.getString("customerCode"),rs.getString("customerType"),rs.getString("customerName"),personMap.get(rs.getString("personCode")),address);
				customers.add(customer);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
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
	// Takes a string and breaks it into a customer.
	public static Customer stringToCustomer(String input) {
		Customer out = null;
		//Breaks string apart into token.
		String[] splitToken = input.split(";");
		Person primaryContact = Person.stringToPerson(splitToken[3]);
		Address address = Address.stringToAddress(splitToken[4]);
		out = new Customer(splitToken[0], splitToken[1],splitToken[2],primaryContact, address);
		return out;
	}
	
	public double getLoyalDiscount(double totalAfterTax) {
		
		if (this.customerType.equals("P")) {	
			// Check if primary contact has more than 1 email
			ArrayList<String> emailCheck = this.primaryContact.getEmail();
			if (emailCheck != null && emailCheck.size() > 1) {
				return (-.05 * totalAfterTax);
			}		
		}
		return 0;
	}


	@Override
	public String toString() {
		return "Customer [customerCode=" + customerCode + ", customerType=" + customerType + ", name=" + name
				+ ", primaryContact=" + primaryContact + ", address=" + address + "]";
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
