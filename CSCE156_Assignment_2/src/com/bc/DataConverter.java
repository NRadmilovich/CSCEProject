package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.*;
import java.util.HashMap;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Data conversion for Person, Customer.
		ArrayList<Person> people = Person.importPerson("Persons.dat");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomer("Customers.dat", personMap);
		
		
		// Data Conversion for Products
		ArrayList<Rental> rentals = null;
		ArrayList<Repair> repairs = null;
		ArrayList<Concession> concessions = null;
		ArrayList<Towing> tows = null;
		
		Scanner s;
		
		try {
			
			s = new Scanner(new File("data/Products.dat"));
			
			while (s.hasNext()) {
				
				String[] tokens = s.nextLine().split(",");
				
				if (tokens[1].compareTo("R") == 0) {
					Rental r = null;
					r = new Rental(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]));		
					rentals.add(r);
					
				} else if (tokens[1].compareTo("F") == 0) {
					Repair f = null;
					f = new Repair(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));		
					repairs.add(f);
					
				} else if (tokens[1].compareTo("C") == 0) {
					Concession c = null;
					c = new Concession(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));		
					concessions.add(c);
					
				} else if (tokens[1].compareTo("T") == 0) {
					Towing t = null;
					t = new Towing(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));		
					tows.add(t);
					
				}
				
			}
			s.close();
			
		} catch(FileNotFoundException e) {
			System.err.print("File not Found");
			e.printStackTrace();
		}
		//Print xml to file
		XMLPrint.printXML("data/Persons.xml", people, "Person");
		XMLPrint.printXML("data/Customers.xml", customers, "Customer");
		// Print json to file
		JsonWrite.printJSON("data/Products.json", rentals);
		JsonWrite.printJSON("data/Products.json", repairs);
		JsonWrite.printJSON("data/Products.json", concessions);
		JsonWrite.printJSON("data/Products.json", tows);
	}

}
