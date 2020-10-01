package com.bc;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Invoice {

	private String invoiceCode;
	private Person owner;
	private Customer customerData;
	private ArrayList<Products> productList;
	private HashMap<String,Number> workValue;
	private Boolean associatedRepair;
	
	
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public Person getOwner() {
		return owner;
	}
	public Customer getCustomerData() {
		return customerData;
	}
	public ArrayList<? extends Products> getProductList() {
		return productList;
	}
	public HashMap<String,Number> getWorkValue(){
		return workValue;
	}
	
	public Invoice(String invoiceCode, Person owner, Customer customerData, ArrayList<Products> productList, HashMap<String,Number> workValues, Boolean associatedRepair) {
		super();
		this.invoiceCode = invoiceCode;
		this.owner = owner;
		this.customerData = customerData;
		this.productList = productList;
		this.workValue = workValues;
		this.associatedRepair = associatedRepair;
	}
	
	public static ArrayList<Invoice> importInvoice(String filename, HashMap<String,Person> personMap, HashMap<String,Customer> customerMap, HashMap<String,Products> productMap) {
		ArrayList<Invoice> Invoices = new ArrayList<Invoice>();
		Scanner scanner = null;
		String fileString = "data/" + filename;
		File file = new File(fileString);
		try {
		scanner = new Scanner(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.nextLine();
		while(scanner.hasNext()) {
			String Token = scanner.nextLine();
			String[] splitToken = Token.split(";");
			String invoiceCode = splitToken[0];
			Person owner = personMap.get(splitToken[1]);
			Customer customer = customerMap.get(splitToken[2]);
			HashMap<String,Number> workValues = new HashMap<String,Number>();
			ArrayList<Products> productList = new ArrayList<Products>();
			String[] splitProducts = splitToken[3].split(",");
			Boolean associatedRepair = false;
			for(String prod: splitProducts) {
				String[] values = prod.split(":");
				productList.add(productMap.get(values[0]));
				workValues.put(values[0], Integer.parseInt(values[1]));
				if(values.length > 2) {
					associatedRepair = true;
				}
			}
			Invoices.add(new Invoice(invoiceCode, owner, customer, productList, workValues, associatedRepair));
		}
		scanner.close();
		return Invoices;
	}
}
