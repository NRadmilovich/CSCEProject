package com.bc;
import java.util.ArrayList;

public class Concession extends Products {
	
	private Double unitCost;
	private Boolean associatedRepair;

	// Constructor
	public Concession(String productCode, String productType, String productLabel, Double unitCost) {
		super(productCode, productType, productLabel);
		this.setUnitCost(unitCost);
		this.associatedRepair = false;
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
	public double getDiscounts(int freeFlag, Number workValue) {
		// Check if a concession has an associated repair
		if (this.associatedRepair) {
			return -.1 * getSubtotal(workValue);
		}
		return 0;
	}
	public String costPrint(Number val) {
		String out = " ("+ val.intValue() + " units @ $" + this.getUnitCost() + "/unit)";
		return out;
	}
	public String feePrint() {
		return null;
	}
	public void associatedRepairCheck(Products repair, String repairVal) {
			if(repair.getProductCode().contentEquals(repairVal)) {
				this.associatedRepair = true;
			}else {
				this.associatedRepair = false;
			}
		}
	}
