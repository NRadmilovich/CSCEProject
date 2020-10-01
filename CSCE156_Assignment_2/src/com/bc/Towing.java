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
	public double getSubtotal(Number milesTowed) {
		
		return this.costPerMile * milesTowed.doubleValue();
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag, Boolean associatedRepair, Number workValue) {
		if (freeFlag >= 3) {
			return -getSubtotal(workValue);
		} else {
			return 0;
		}
	}
	
	


}
