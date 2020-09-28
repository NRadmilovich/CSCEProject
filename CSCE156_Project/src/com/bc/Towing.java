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
	
	


}
