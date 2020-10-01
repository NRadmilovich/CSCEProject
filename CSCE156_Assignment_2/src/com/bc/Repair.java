package com.bc;

public class Repair extends Products{
	
	private Double partsCost;
	private Double hourlyLaborCost;
	
	// Constructor
	public Repair(String productCode, String productType, String productLabel, Double partsCost,
			Double hourlyLaborCost) {
		super(productCode, productType, productLabel);
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
	}

	// Getters and Setters
	public Double getPartsCost() {
		return partsCost;
	}

	public void setPartsCost(Double partsCost) {
		this.partsCost = partsCost;
	}

	public Double getHourlyLaborCost() {
		return hourlyLaborCost;
	}

	public void setHourlyLaborCost(Double hourlyLaborCost) {
		this.hourlyLaborCost = hourlyLaborCost;
	}

	// Price Calculations
	@Override
	public double getSubtotal(double hoursWorked) {
		return this.partsCost + (this.hourlyLaborCost * hoursWorked);
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	

	

}
