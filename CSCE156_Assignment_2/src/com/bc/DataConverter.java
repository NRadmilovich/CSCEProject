package com.bc;


import java.util.ArrayList;
import java.util.HashMap;

import com.bc.ext.InvoiceData;
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
		// Testing InvoiceData
		InvoiceData.addPerson("12345", "Nick", "Radmilovich", "123 not my Address", "Lincoln", "NE", "68528", "USA");
		InvoiceData.addCustomer("12345", "P", "12345", "My business is personal", "Still not my address", "Lincoln", "NE", "68528", "USA");
		InvoiceData.addInvoice("54321", "12345", "12345");
		InvoiceData.addEmail("12345", "Nradmilovich4@gmail.com");
		// Tested updated Associated Repair with Strings
		InvoiceData.addConcessionToInvoice("54321", "aldkjflaksjdfh", 25, null);
		InvoiceData.addConcessionToInvoice("54321", "aldkjflaksjdfh", 15, "nfein2929");
		
		//Data conversion for Person, Customer, and Products.
		ArrayList<Person> people = Person.importPersonDB();
		JsonWrite.printJSON("data/Persons.json", people, "Person");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomerDB(personMap);
		ArrayList<Products> products = Products.importProducts();
		// Creates Product and Customer Hashmaps
		HashMap<String,Products> productMap = Products.productMap(products);
		HashMap<String,Customer> customerMap = Customer.customerMap(customers);
		// Creates Invoice ArrayList
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
		
	}

}
