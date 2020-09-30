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

	@Override
	public double getSubtotal(double milesTowed) {
		
		return getCostPerMile() * milesTowed;
	}

	@Override
	public double getDiscounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFees() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTaxes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	


}
