package com.bc;
import java.util.ArrayList;

public class Concession extends Products {
	
	private Double unitCost;
	private Boolean associatedRepair;

	// Constructor
	public Concession(String productCode, String productType, String productLabel, Double workVal, Double unitCost) {
		super(productCode, productType, productLabel, workVal);
		this.unitCost = unitCost;
		this.associatedRepair = false;
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
		if (this.associatedRepair) {
			return -.1 * this.getSubtotal();
		}
		return 0;
	}
	public String costPrint() {
		String out = " ("+ this.workValue + " units @ $" + this.getUnitCost() + "/unit)";
		return out;
	}
	public String feePrint() {
		return null;
	}
	public static void associatedRepairCheck(ArrayList<Products> potentials, ArrayList<Products> products, String repairVal) {
		for(Products prod: potentials) {
			if(prod instanceof Concession) {
				Concession test = (Concession) prod;
				for(Products prodList: products) {
					if(prodList.getProductCode().contentEquals(repairVal) && prodList instanceof Repair) {
						test.associatedRepair = true;
				}
			}}
		}
		}
	}
