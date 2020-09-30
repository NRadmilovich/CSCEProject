package com.bc;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	// Product Calculations
	public abstract double getSubtotal(double workValue);
	public abstract double getDiscounts();
	public abstract double getFees();
	public abstract double getTaxes();
	public abstract double getTotal();

		
	
	
	
	
	
}
