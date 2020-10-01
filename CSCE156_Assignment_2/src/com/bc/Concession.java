package com.bc;

public class Concession extends Products {
	
	private Double unitCost;

	// Constructor
	public Concession(String productCode, String productType, String productLabel, Double unitCost) {
		super(productCode, productType, productLabel);
		this.setUnitCost(unitCost);
	}

	// Getters and Setters
	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	// Price Calculations
	@Override
	public double getSubtotal(Number unitQuantity) {
		return this.unitCost * unitQuantity.doubleValue();
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		// How can I check if a concession has an associated repair?
		
		return 0;
	}
	
	

}
