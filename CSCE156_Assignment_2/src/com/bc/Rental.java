package com.bc;

public class Rental extends Products{

	private Double dailyCost;
	private Double deposit;
	private Double cleaningFee;
	
	// Constructor
	public Rental(String productCode, String productType, String productLabel, Double dailyCost, Double deposit,
			Double cleaningFee) {
		super(productCode, productType, productLabel);
		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
	}

	// Getters and Setters
	public Double getDailyCost() {
		return dailyCost;
	}

	public void setDailyCost(Double dailyCost) {
		this.dailyCost = dailyCost;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getCleaningFee() {
		return cleaningFee;
	}

	public void setCleaningFee(Double cleaningFee) {
		this.cleaningFee = cleaningFee;
	}
	
	
	
	
	
	

}