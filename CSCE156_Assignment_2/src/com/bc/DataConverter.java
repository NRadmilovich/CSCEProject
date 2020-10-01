package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.HashMap;
/*
 * CSCE Project
 * Authors:
 * Nick Radmilovich
 * Caden J Kirby
 */
public class DataConverter {

	public static void main(String[] args) {
		//Data conversion for Person, Customer.
		ArrayList<Person> people = Person.importPerson("Persons.dat");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomer("Customers.dat", personMap);

		
		// Data Conversion for Products
		ArrayList<Products> products  = new ArrayList<Products>();
		
		Scanner s;
		
		try {
			
			s = new Scanner(new File("data/Products.dat"));
			int productNum = Integer.parseInt(s.nextLine());
			while (s.hasNext()) {
				
				String[] tokens = s.nextLine().split(";");
				
				if (tokens[1].compareTo("R") == 0) {
					Rental r = null;
					r = new Rental(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]));		
					products.add(r);
					
				} else if (tokens[1].compareTo("F") == 0) {
					Repair f = null;
					f = new Repair(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));		
					products.add(f);
					
				} else if (tokens[1].compareTo("C") == 0) {
					Concession c = null;
					c = new Concession(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));		
					products.add(c);
					
				} else if (tokens[1].compareTo("T") == 0) {
					Towing t = null;
					t = new Towing(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));		
					products.add(t);
					
				}
				
			}
			s.close();
			
		} catch(FileNotFoundException e) {
			System.err.print("File not Found");
			e.printStackTrace();
		}
		// Testing invoice creator and product hashmap
		HashMap<String,Products> productMap = Products.productMap(products);
		HashMap<String,Customer> customerMap = Customer.customerMap(customers);
		ArrayList<Invoice> invoices = Invoice.importInvoice("Invoices.dat", personMap, customerMap, productMap);
		
		//Print xml to file
		XMLPrint.printXML("data/Persons.xml", people, "Person");
		XMLPrint.printXML("data/Customers.xml", customers, "Customer");
		XMLPrint.printXML("data/Products.xml", products, "Products");
		XMLPrint.printXML("data/Invoices.xml", invoices, "Invoice");
		// Print json to file
		JsonWrite.printJSON("data/Products.json", products, "Products");
		JsonWrite.printJSON("data/Persons.json", people, "Person");
		JsonWrite.printJSON("data/Customers.json", customers, "Customer");
		JsonWrite.printJSON("data/Invoices.json", invoices, "Invoice");
		
		// Testing summary print
		Invoice.printSummary(invoices);
		
	}

}
