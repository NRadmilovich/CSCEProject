package com.bc;


import java.util.ArrayList;
import java.util.HashMap;
/*
 * CSCE Project
 * Authors:
 * Nick Radmilovich
 * Caden J Kirby
 */
public class DataConverter {

	public static void main(String[] args) {
		//Data conversion for Person, Customer, and Products.
		ArrayList<Person> people = Person.importPerson("Persons.dat");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomer("Customers.dat", personMap);
		ArrayList<Products> products = Products.importProducts("Products.dat");
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
		
		// Testing summary print
		Invoice.printSummary(invoices);
		
	}

}
