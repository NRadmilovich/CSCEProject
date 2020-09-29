package com.bc;
import java.util.ArrayList;

public class Invoice <T extends Products> {

	private String invoiceCode;
	private Person owner;
	private Customer customerData;
	private ArrayList<T> productList;
	
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public Person getOwner() {
		return owner;
	}
	public Customer getCustomerData() {
		return customerData;
	}
	public ArrayList<T> getProductList() {
		return productList;
	}
	
	public Invoice(String invoiceCode, Person owner, Customer customerData, ArrayList<T> productList) {
		super();
		this.invoiceCode = invoiceCode;
		this.owner = owner;
		this.customerData = customerData;
		this.productList = productList;
	}
	
	
	
	
}
