package com.bc;

import java.util.ArrayList;
/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Rental class is a subclass of Products, and stores rental data for BumprCars.
 */
public class Rental extends Products{

	private Double dailyCost;
	private Double deposit;
	private Double cleaningFee;
	
	// Constructor
	public Rental(String productCode, String productType, String productLabel, double workVal, Double dailyCost, Double deposit,
			Double cleaningFee) {
		super(productCode, productType, productLabel, workVal);
		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
	}
	// Copy Constructor
	public Rental(Rental old, double workVal) {
		super();
		this.productCode = old.getProductCode();
		this.productType = old.getProductType();
		this.productLabel = old.productLabel;
		this.workValue = workVal;
		this.dailyCost = old.getDailyCost();
		this.deposit = old.getDeposit();
		this.cleaningFee = old.getCleaningFee();
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

	// Price Calculations
	@Override
	public double getSubtotal() {
		// System.out.printf("%nWorkValue:%.2f DailyCost: %.2f%n", daysRented.doubleValue(), this.dailyCost);
		return (this.dailyCost * this.workValue) - this.deposit + this.cleaningFee;

	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		return 0;
	}	
	// Makes a string, with cost info, for the detailed print.
	public String costPrint() {
		String out = " ("+ this.workValue + " days @ $" + this.getDailyCost() + "/day)";
		return out;
	}
	// Makes a string, with fee info, for the detailed print.
	public String feePrint() {
		String fees = "(+ $" + this.cleaningFee + " cleaning fee, -$" + this.getDeposit() + " deposit refund)";
		return fees;
	}
	public static void associatedRepairCheck(ArrayList<Products> potentials, ArrayList<Products> products, String repairVal) {
		
	}
}
