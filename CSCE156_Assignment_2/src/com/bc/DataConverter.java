package com.bc;


import java.util.ArrayList;
import java.util.HashMap;


/**
 * Assignment 3
 * 
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The DataConverter class uses methods from Invoice, Products, Person, and Customer classes to convert 
 * BumprCars flat files into JSON and XML files.
 */
public class DataConverter {

	public static void main(String[] args) {

		//Data conversion for Person, Customer, and Products.
		ArrayList<Person> people = Person.importPersonDB();
		JsonWrite.printJSON("data/Persons.json", people, "Person");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomerDB(personMap);
		ArrayList<Product> product = Product.importProducts();
		// Creates Product and Customer Hashmaps
		HashMap<String,Product> productMap = Product.productMap(product);
		HashMap<String,Customer> customerMap = Customer.customerMap(customers);
		// Creates Invoice ArrayList
		ArrayList<Invoice> invoices = Invoice.importInvoiceDBAL( personMap, customerMap, productMap);
		//Print xml to file
		XMLPrint.printXML("data/Persons.xml", people, "Person");
		XMLPrint.printXML("data/Customers.xml", customers, "Customer");
		XMLPrint.printXML("data/Products.xml", product, "Product");
		XMLPrint.printXML("data/Invoices.xml", invoices, "Invoice");
		// Print json to file
		JsonWrite.printJSON("data/Products.json", product, "Product");
		JsonWrite.printJSON("data/Persons.json", people, "Person");
		JsonWrite.printJSON("data/Customers.json", customers, "Customer");
		JsonWrite.printJSON("data/Invoices.json", invoices, "Invoice");

	}

}
