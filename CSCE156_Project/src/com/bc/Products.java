package com.bc;

public class Products {
	
	protected String productCode;
	protected String productType;
	protected String productLabel;
	
	// Constructor
	public Products(String productCode, String productType, String productLabel) {
		super();
		this.productCode = productCode;
		this.productType = productType;
		this.productLabel = productLabel;
	}

	// Getters and Setters
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	
	
	
	
	
}
