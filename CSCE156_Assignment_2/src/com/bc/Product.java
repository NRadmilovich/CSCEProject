package com.bc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Products class is a superclass to Rental, Repair, Towing, and Concessions.  It is used by the invoice class
 * to store information about the products offered by BumprCars.
 */
public abstract class Product {
	
	protected String productCode;
	protected String productType;
	protected String productLabel;
	protected double workValue;
	
	// Constructor
	public Product(String productCode, String productType, String productLabel, double workValue) {
		super();
		this.productCode = productCode;
		this.productType = productType;
		this.productLabel = productLabel;
		this.workValue = workValue;
		
	}
	public Product() {
		super();
	}

	// Getters and Setters
	public String getProductCode() {
		return productCode;
	}

	public String getProductType() {
		return productType;
	}


	public String getProductLabel() {
		return productLabel;
	}

	public double getWorkValue() {
		return workValue;
	}
	
	// Added a hashmap conversion for invoices.
	public static HashMap<String,Product> productMap(ArrayList<Product> list){
		HashMap<String,Product> map = new HashMap<String,Product>();
		for(Product p: list) {
			map.put(p.getProductCode(),p);
		}
		return map;
	}
	
	// Creates an arraylist of Products based on input file.
	public static ArrayList<Product> importProducts(){
		ArrayList<Product> product  = new ArrayList<Product>();
		
		String query = "select * from Product;";
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();

		try {
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();
			while (rs.next()) {
				
				String code = rs.getString("productCode");
				String type = rs.getString("productType");
				String label = rs.getString("productLabel");
				
				if (type.compareTo("R") == 0) {
					Rental r = null;
					r = new Rental(code, type, label, 0.0, Double.parseDouble(rs.getString("dailyCost")),
							Double.parseDouble(rs.getString("deposit")), Double.parseDouble(rs.getString("cleaningFee")));		
					product.add(r);
					
				} else if (type.compareTo("F") == 0) {
					Repair f = null;
					f = new Repair(code, type, label, 0.0, Double.parseDouble(rs.getString("partsCost")), 
							Double.parseDouble(rs.getString("hourlyLaborCost")));		
					product.add(f);
					
				} else if (type.compareTo("C") == 0) {
					Concession c = null;
					c = new Concession(code, type, label, 0.0, Double.parseDouble(rs.getString("unitCost")));		
					product.add(c);
					
				} else if (type.compareTo("T") == 0) {
					Towing t = null;
					t = new Towing(code, type, label, 0.0, Double.parseDouble(rs.getString("costPerMile")));		
					product.add(t);		
				}			
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close Connections
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);
		
		return product;

	}
	
	// Creates a deep copy of a Products, based on the input type
	public static Product deepCopy(Product oldProd, double workVal) {
		Product newProd = null;
		if(oldProd instanceof Rental) {
			Rental old = (Rental) oldProd;
			newProd = new Rental(old, workVal);
		}else if(oldProd instanceof Towing) {
			Towing old = (Towing) oldProd;
			newProd = new Towing(old, workVal);
		}else if(oldProd instanceof Repair) {
			Repair old = (Repair) oldProd;
			newProd = new Repair(old,workVal);
		}else {
			Concession old = (Concession) oldProd;
			newProd = new Concession(old,workVal);
		}return newProd;
	}
	
	// Product Calculations
	public abstract double getSubtotal();
	public abstract double getDiscounts(int freeFlag);
	public abstract double getTotal();
	public abstract String costPrint();
	public abstract String feePrint();
	public static void associatedRepairCheck(ArrayList<Product> potentials, ArrayList<Product> product, String repairVal) {
		
	}
	

	}
