package com.bc;

public class Towing extends Products{

	private Double costPerMile;
	
	// Constructor
	public Towing(String productCode, String productType, String productLabel, Double costPerMile) {
		super(productCode, productType, productLabel);
		this.setCostPerMile(costPerMile);
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
	public double getSubtotal(double milesTowed) {
		
		return this.costPerMile * milesTowed;
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		if (freeFlag >= 3) {
			return -getSubtotal(3);
		} else {
			return 0;
		}
	}
	
	


}
