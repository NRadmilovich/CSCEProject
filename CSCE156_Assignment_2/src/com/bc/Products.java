package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Products {
	
	protected String productCode;
	protected String productType;
	protected String productLabel;
	
	// Constructor
	public Products(String productCode, String productType, String productLabel) {
		super();
		this.productCode = productCode;
		this.productType = productType;
		this.productLabel = productLabel;
	}
	public Products() {
		super();
	}

	// Getters and Setters
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	
	// Added a hashmap conversion for invoices.
	public static HashMap<String,Products> productMap(ArrayList<Products> list){
		HashMap<String,Products> map = new HashMap<String,Products>();
		for(Products p: list) {
			map.put(p.getProductCode(),p);
		}
		return map;
	}
	public static ArrayList<Products> importProducts(String filename){
	ArrayList<Products> products  = new ArrayList<Products>();
	
	Scanner s;
	String file = "data/" + filename;
	try {
		
		s = new Scanner(new File(file));
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
	return products;
	}
	
	// Product Calculations
	public abstract double getSubtotal(Number workValue);
	public abstract double getDiscounts(int freeFlag, Number workValue);
	public abstract double getTotal();
	public abstract String costPrint(Number num);
	public abstract String feePrint();
	public abstract void associatedRepairCheck(Products repairCheck, String string);

		
	
	
	
	
	
}
