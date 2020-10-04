package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import javax.print.attribute.standard.NumberUpSupported;

/**
 * Assignment 3
 * 
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * The invoice class compiles data from Person, Products, and Customer, in order
 * to structure invoice data for Bumpr Cars.
 */

public class Invoice {

	private String invoiceCode;
	private Person owner;
	private Customer customerData;
	private ArrayList<Products> productList;

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

	public Invoice(String invoiceCode, Person owner, Customer customerData, ArrayList<Products> productList) {
		super();
		this.invoiceCode = invoiceCode;
		this.owner = owner;
		this.customerData = customerData;
		this.productList = productList;
	}

	public static ArrayList<Invoice> importInvoice(String filename, HashMap<String, Person> personMap,
			HashMap<String, Customer> customerMap, HashMap<String, Products> productMap) {
		ArrayList<Invoice> Invoices = new ArrayList<Invoice>();
		Scanner scanner = null;
		String fileString = "data/" + filename;
		File file = new File(fileString);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.nextLine();
		while (scanner.hasNext()) {
			String Token = scanner.nextLine();
			String[] splitToken = Token.split(";");
			String invoiceCode = splitToken[0];
			Person owner = personMap.get(splitToken[1]);
			Customer customer = customerMap.get(splitToken[2]);
			ArrayList<Products> productList = new ArrayList<Products>();
			ArrayList<Products> potentialAssociations = new ArrayList<Products>();
			String associatedCode = null;
			String[] splitProducts = splitToken[3].split(",");

			for (String prod : splitProducts) {
				String[] values = prod.split(":");
				Products newProd = null;
				double workVal = Double.parseDouble(values[1]);
				newProd = Products.deepCopy(productMap.get(values[0]), workVal);
				productList.add(newProd);
				if(values.length > 2) {
					associatedCode = values[2]; 
					potentialAssociations.add(newProd);
				}}
			if(associatedCode != null) {
				Concession.associatedRepairCheck(potentialAssociations, productList, associatedCode);
			}
			Invoices.add(new Invoice(invoiceCode, owner, customer, productList));
		}
		scanner.close();
		return Invoices;
	}

	// Print Methods for invoice reports
	public static void printSummary(Collection<Invoice> invoiceList) {

		// Print top of table
		System.out.println("Executive Summary Report:");
		System.out.printf("%n%-10s %-25s %-30s %-10s %-10s  %-10s  %-10s  %-10s %n", "Code", "Owner",
				"Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total");
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------");

		double totalInvoiceSubtotal = 0;double totalInvoiceDiscounts = 0;double totalInvoiceFees = 0;
		double totalInvoiceTaxes = 0;double totalInvoiceTotal = 0;

		// Loop through collection of invoices
		for (Invoice invoice : invoiceList) {

			double invoiceSubtotal = 0;double invoiceDiscounts = 0;double invoiceFees = 0;
			double invoiceTaxes = 0;double invoiceTotal = 0;
			int freeFlag = 0;

			// Checking if Towing is free
			for (Products p : invoice.getProductList()) {
				if (p instanceof Rental) {
					freeFlag += 1;
				} else if (p instanceof Repair) {
					freeFlag += 1;
				} else if (p instanceof Towing) {
					freeFlag += 1;
				}
			}

			// Calculating totals of 1 invoice
			for (Products p : invoice.getProductList()) {
				double productSubtotal = p.getSubtotal();
				double productDiscounts = p.getDiscounts(freeFlag);
				double productTaxes = invoice.getCustomerData().getTaxes(productSubtotal + productDiscounts);
				invoiceSubtotal += productSubtotal;
				invoiceDiscounts += productDiscounts;
				invoiceTaxes += productTaxes;

			}

			// Apply entire invoice fees and discounts
			double businessAccountFee = invoice.getCustomerData().getFees();
			double loyalDiscount = invoice.getCustomerData().getLoyalDiscount(invoiceSubtotal + invoiceTaxes);
			invoiceFees += businessAccountFee;
			invoiceDiscounts += loyalDiscount;
			invoiceTotal = invoiceSubtotal + invoiceDiscounts + invoiceFees + invoiceTaxes;

			// Print one invoice
			System.out.printf("%-10s %-25s %-30s $%10.2f $%10.2f  $%10.2f  $%10.2f $%10.2f%n",
					invoice.getInvoiceCode(), invoice.getOwner(), invoice.getCustomerData().getName(), invoiceSubtotal,
					invoiceDiscounts, invoiceFees, invoiceTaxes, invoiceTotal);

			// Calculating totals of all invoices
			totalInvoiceSubtotal += invoiceSubtotal;
			totalInvoiceDiscounts += invoiceDiscounts;
			totalInvoiceFees += invoiceFees;
			totalInvoiceTaxes += invoiceTaxes;
			totalInvoiceTotal += invoiceTotal;

		}

		System.out.println(
				"============================================================================================================================");
		System.out.printf("%-67s $%10.2f $%10.2f  $%10.2f  $%10.2f $%10.2f%n", "TOTALS", totalInvoiceSubtotal,
				totalInvoiceDiscounts, totalInvoiceFees, totalInvoiceTaxes, totalInvoiceTotal);

	}

	public static void printDetailed(Collection<Invoice> invoiceList) {
		System.out.println("Invoice Details:");
		for (Invoice invoice : invoiceList) {
			//Initializing var
			double invoiceSubTotal = 0;double invoiceDiscounts = 0;double invoiceTaxes = 0;double invoiceTotal = 0;
			
			// Prints Header
			System.out.println(
					"=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
			System.out.printf("Invoice %s \n------------------------------------------ \n", invoice.getInvoiceCode());

			// Print owner
			Person owner = invoice.getOwner();
			Address ownerAddress = owner.getAddress();
			System.out.println("Owner:");
			System.out.printf("\t%s \n\t%s \n\t%s \n", owner.toString(), Arrays.toString(owner.getEmail().toArray()),
					ownerAddress.toString());

			// Prints customer info
			Customer customer = invoice.getCustomerData();
			Address customerAddress = customer.getAddress();
			String acctType = "";
			double extraFee = customer.getFees();
			double loyalDiscount = 0;
			System.out.println("Customer:");
			System.out.printf("\t%s %s \n\t%s \n", customer.getName(), acctType, customerAddress.toString());
			// Print product info
			System.out.println("Product:");
			System.out.printf("  %s %18s %67s %12s %9s %12s \n","Code","Description","Subtotal","Discount","Taxes","Total");
			System.out.println("  ------------------------------------------------------------------------------------------------------------------------------------");
			ArrayList<? extends Products> productList = invoice.getProductList();
			int freeFlag = 0;
			
			// Counts freeflag for calculations
			for (Products prod : productList) {
				if (prod instanceof Rental) {
					freeFlag += 1;
				} else if (prod instanceof Repair) {
					freeFlag += 1;
				} else if (prod instanceof Towing) {
					freeFlag += 1;
				}
			}
			for(Products prod: productList) {
				// Computes product calculations and creates strings for printing.
				String extraVal = null;

				String description = prod.getProductLabel() + prod.costPrint();
				
				// Cost Calculations
				double productSubtotal = prod.getSubtotal();
				double productDiscounts = prod.getDiscounts(freeFlag);
				double productTaxes = invoice.getCustomerData().getTaxes(productSubtotal + productDiscounts);
				double productTotal = productSubtotal + productDiscounts + productTaxes;
				invoiceSubTotal += productSubtotal;
				invoiceDiscounts += productDiscounts;
				invoiceTaxes += productTaxes;
				invoiceTotal += productTotal;

			System.out.printf("  %-12s%-69s $ %9.2f  $ %9.2f  $ %9.2f  $ %9.2f \n",prod.getProductCode(), description, productSubtotal,productDiscounts,productTaxes,productTotal);
			extraVal = prod.feePrint();
			if(extraVal != null) {
				System.out.printf("\t      %s\n",extraVal);
			}
			}
			System.out.println("======================================================================================================================================");

			System.out.printf("%-83s $ %9.2f  $ %9.2f  $ %9.2f  $ %9.2f \n","ITEM TOTALS:",invoiceSubTotal, invoiceDiscounts, invoiceTaxes, invoiceTotal);
			loyalDiscount = customer.getLoyalDiscount(invoiceTotal);

			if(loyalDiscount != 0 && customer.getCustomerType().contains("P")) {
				System.out.printf("LOYAL CUSTOMER DISCOUT (5 OFF) %93s %9.2f \n", "$", loyalDiscount);
			}else if(extraFee != 0 && customer.getCustomerType().contains("B")) {
				System.out.printf("BUSINESS ACCOUNT FEE: %102s %9.2f\n", "$",extraFee);
			}
			
			double grandTotal = invoiceTotal + loyalDiscount + extraFee;
			System.out.printf("%-122s $ %9.2f \n","GRAND TOTAL:",grandTotal);
			// Print closer
			System.out.printf("\n\n\t\t THANK YOU FOR YOUR BUSINESS WITH US! \n\n\n\n");

			freeFlag = 0;
			
		}
	}
}
