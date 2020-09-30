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

	@Override
	public double getSubtotal(double unitQuantity) {
		return getUnitCost() * unitQuantity;
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
