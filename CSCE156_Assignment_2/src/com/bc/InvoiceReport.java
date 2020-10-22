package com.bc;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * CSCE 156
 * 
 * Authors:
 * Caden Kirby
 * Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * The invoice report class will import data from a Person, Customer, Invoice, and Product flat file,
 * and will convert the data into an ArrayList of Invoices.  It will then do appropriate invoice calculations
 * based on customer info, and print a summary and detailed report.
 */

public class InvoiceReport {

	public static void main(String[] args) {
		//Data conversion for Person, Customer, and Products.
		ArrayList<Person> people = Person.importPerson("Persons.dat");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomer("Customers.dat", personMap);
		ArrayList<Products> products = Products.importProducts();
		// Creates Product and Customer Hashmaps
		HashMap<String,Products> productMap = Products.productMap(products);
		HashMap<String,Customer> customerMap = Customer.customerMap(customers);
		// Creates Invoice ArrayList based on invoice flat file
		ArrayList<Invoice> invoices = Invoice.importInvoice("Invoices.dat", personMap, customerMap, productMap);
		// Prints Invoices
		Invoice.printSummary(invoices);
		System.out.printf("\n\n\n");
		Invoice.printDetailed(invoices);
	}

}
