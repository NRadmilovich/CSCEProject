package com.bc;

import java.util.ArrayList;
/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Towing class is a subclass of Products, and stores towing information for BumprCars.
 */
public class Towing extends Product{

	private Double costPerMile;
	
	// Constructor
	public Towing(String productCode, String productType, String productLabel, double workVal, Double costPerMile) {
		super(productCode, productType, productLabel, workVal);
		this.setCostPerMile(costPerMile);
	}
	public Towing(Towing old, double workVal) {
		super();
		this.productCode = old.getProductCode();
		this.productType = old.getProductType();
		this.productLabel = old.productLabel;
		this.workValue = workVal;
		this.costPerMile = old.costPerMile;
	}

	// Setters and Getters
	public Double getCostPerMile() {
		return costPerMile;
	}

	public void setCostPerMile(Double costPerMile) {
		this.costPerMile = costPerMile;
	}

	// Price Calculations
	@Override
	public double getSubtotal() {
		
		return this.costPerMile * this.workValue;
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		if (freeFlag >= 3) {
			return -this.getSubtotal();
		} else {
			return 0;
		}
	}
	// Makes a string, with cost info, for the detailed print.
	public String costPrint() {
		String out = " ("+ this.workValue + " miles @ $" + this.getCostPerMile() + "/mile)";
		return out;
	}
	public String feePrint() {
		return null;
	}
	public static void associatedRepairCheck(ArrayList<Product> potentials, ArrayList<Product> product, String repairVal) {
		
	}	


}
