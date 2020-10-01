package com.bc;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
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
	
	// Testing method in invoice class
	public static void printSummary(Collection <Invoice> invoiceList) {

		// Print top of table
		System.out.println("Executive Summary Report:");
		System.out.printf("%n%-10s %-25s %-30s %-10s %-10s  %-10s  %-10s  %-10s %n", "Code", "Owner", "Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		
		double totalInvoiceSubtotal = 0;
		double totalInvoiceDiscounts = 0;
		double totalInvoiceFees = 0;
		double totalInvoiceTaxes = 0;
		double totalInvoiceTotal = 0;
		
		// Loop through collection of invoices
		for (Invoice invoice: invoiceList) {
			
			HashMap<String,Number> workValueMap = invoice.getWorkValue();
			double invoiceSubtotal = 0;
			double invoiceDiscounts = 0;
			double invoiceFees = 0;
			double invoiceTaxes = 0;
			double invoiceTotal = 0;
			int freeFlag = 0; 
			
			// Checking if Towing is free
			for (Products p: invoice.getProductList()) {			
				if (p instanceof Rental) {
					freeFlag += 1;
				} else if (p instanceof Repair) {
					freeFlag += 1;
				} else if (p instanceof Towing) {
					freeFlag += 1;
				}
			}
			
			// Calculating totals of 1 invoice
			for (Products p: invoice.getProductList()) {
				
				Number workValue = workValueMap.get(p.productCode);
				double productSubtotal = p.getSubtotal(workValue);
				double productDiscounts = p.getDiscounts(freeFlag, invoice.associatedRepair, workValue);
				double productTaxes = invoice.getCustomerData().getTaxes(productSubtotal + productDiscounts);
				invoiceSubtotal += productSubtotal;
				invoiceDiscounts += productDiscounts;
				invoiceTaxes += productTaxes;
				
				// Can use this line for Detailed
				// System.out.printf("%n%.2f %.2f %.2f%n", productSubtotal, productDiscounts, productTaxes);
			}
			
			// Apply entire invoice fees and discounts
			double businessAccountFee = invoice.getCustomerData().getFees();
			double loyalDiscount =  invoice.getCustomerData().getLoyalDiscount(invoiceSubtotal + invoiceTaxes);
			invoiceFees += businessAccountFee;
			invoiceDiscounts += loyalDiscount;
			invoiceTotal = invoiceSubtotal + invoiceDiscounts + invoiceFees + invoiceTaxes;
			
			// Print one invoice  
			System.out.printf("%n%-10s %-25s %-30s $%-10.2f $%-10.2f  $%-10.2f  $%-10.2f $%-10.2f%n", invoice.getInvoiceCode(), invoice.getOwner(), invoice.getCustomerData().getName(), invoiceSubtotal, invoiceDiscounts, invoiceFees, invoiceTaxes, invoiceTotal);
				
			// Calculating totals of all invoices
			totalInvoiceSubtotal += invoiceSubtotal;
			totalInvoiceDiscounts += invoiceDiscounts;
			totalInvoiceFees += invoiceFees;
			totalInvoiceTaxes += invoiceTaxes;
			totalInvoiceTotal += invoiceTotal;
		
		}
		
		System.out.println("============================================================================================================================");
		System.out.printf("%-67s $%-10.2f $%-10.2f  $%-10.2f  $%-10.2f $%-10.2f%n", "TOTALS", totalInvoiceSubtotal, totalInvoiceDiscounts, totalInvoiceFees, totalInvoiceTaxes, totalInvoiceTotal);

	}

}
