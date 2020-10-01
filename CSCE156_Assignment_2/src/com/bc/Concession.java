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
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag, Boolean associatedRepair, Number workValue) {
		// Check if a concession has an associated repair
		if (associatedRepair) {
			return -.1 * getSubtotal(workValue);
		}
		return 0;
	}
	
	

}
