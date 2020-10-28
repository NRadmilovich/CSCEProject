package com.bc;
import java.util.ArrayList;
/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Concession class is a subclass of Products, and stores information regarding concessions sold at BumprCars.
 */
public class Concession extends Products {
	
	private Double unitCost;
	private String associatedRepair;

	// Constructor
	public Concession(String productCode, String productType, String productLabel, Double workVal, Double unitCost) {
		super(productCode, productType, productLabel, workVal);
		this.unitCost = unitCost;
		this.associatedRepair = null;
	}
	// Copy Constructor
	public Concession(Concession old, double workVal) {
		super();
		this.productCode = old.getProductCode();
		this.productType = old.getProductType();
		this.productLabel = old.productLabel;
		this.workValue = workVal;
		this.unitCost = old.unitCost;
		this.associatedRepair = old.associatedRepair;
	}
	// Getters and Setters
	public Double getUnitCost() {
		return unitCost;
	}

	// Price Calculations
	@Override
	public double getSubtotal() {
		return this.unitCost * this.workValue;
	}

	@Override
	public double getTotal() {
		return 0;
	}

	@Override
	public double getDiscounts(int freeFlag) {
		// Check if a concession has an associated repair
		if (this.associatedRepair != null) {
			return -.1 * this.getSubtotal();
		}
		return 0;
	}
	// Makes a string for use by detailed print, to display detailed cost info.
	@Override
	public String costPrint() {
		String out = " ("+ this.workValue + " units @ $" + this.getUnitCost() + "/unit)";
		return out;
	}
	// Makes a string, with fee info, for the detailed print.
	@Override
	public String feePrint() {
		return null;
	}
	// Checks a list of concessions with associated repairs, to make sure the repair is in the same invoice.
	public static void associatedRepairCheck(ArrayList<Products> potentials, ArrayList<Products> products, String repairVal) {
		for(Products prod: potentials) {
			// Makes sure the potential is a concession.
			if(prod instanceof Concession) {
				Concession test = (Concession) prod;
				for(Products prodList: products) {
					// Checks against the associated repair code, and ensures it is a Repair.
					if(prodList.getProductCode().contentEquals(repairVal) && prodList instanceof Repair) {
						test.associatedRepair = repairVal;
				}
			}}
		}
		}
	}
