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
	public double getSubtotal(double unitQuantity) {
		return this.unitCost * unitQuantity;
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		// How can I check if a concession has an associated repair?
		// I just fixed associated repair.  Its a boolean, but can be switched to a string if youd rather have that.
		return 0;
	}
	
	

}
