package com.bc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/**
 * CSCE 156
 * 
 * Authors:
 * Caden Kirby
 * Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * The invoice report class will import data from a Person, Customer, Invoice, and Product flat file,
 * and will convert the data into an ArrayList of Invoices.  It will then do appropriate invoice calculations
 * based on customer info, and print a summary and detailed report.
 */

public class InvoiceReport {

	public static void main(String[] args) {
		//Data conversion for Person, Customer, and Products.
		ArrayList<Person> people = Person.importPersonDB();
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomerDB(personMap);
		ArrayList<Product> product = Product.importProducts();
		// Creates Product and Customer Hashmaps
		HashMap<String,Product> productMap = Product.productMap(product);
		HashMap<String,Customer> customerMap = Customer.customerMap(customers);
		// Creates Invoice ArrayList based on invoice flat file
		LinkList<Invoice> invoices = Invoice.importInvoiceDB(personMap, customerMap, productMap);
		// Prints Invoices
		InvoiceReport.printSummary(invoices);
		System.out.printf("\n\n\n");
		InvoiceReport.printDetailed(invoices);

	}
	/**
	 * Prints a summary of all invoices in a given input of invoices.
	 * @param invoiceList
	 */
	public static void printSummary(LinkList<Invoice> invoiceList) {

		// Print top of table
		System.out.println("Executive Summary Report:");
		System.out.printf("%n%-15s %-25s %-30s %-11s %-11s  %-11s  %-10s  %-10s %n", "Code", "Owner",
				"Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total");
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------");

		double totalInvoiceSubtotal = 0;
		double totalInvoiceDiscounts = 0;
		double totalInvoiceFees = 0;
		double totalInvoiceTaxes = 0;
		double totalInvoiceTotal = 0;

		// Loop through collection of invoices
		for (Invoice invoice : invoiceList) {

			double invoiceSubtotal = 0;
			double invoiceDiscounts = 0;
			double invoiceFees = 0;
			double invoiceTaxes = 0;
			double invoiceTotal = 0;
			int freeFlag = 0;

			// Checking if Towing is free
			for (Product p : invoice.getProductList()) {
				if (p instanceof Rental) {
					freeFlag += 1;
				} else if (p instanceof Repair) {
					freeFlag += 1;
				} else if (p instanceof Towing) {
					freeFlag += 1;
				}
			}

			// Calculating totals of 1 invoice
			for (Product p : invoice.getProductList()) {
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
			System.out.printf("%-15s %-25s %-30s $%10.2f $%10.2f  $%10.2f  $%10.2f $%10.2f%n", invoice.getInvoiceCode(),
					invoice.getOwner(), invoice.getCustomerData().getName(), invoiceSubtotal, invoiceDiscounts,
					invoiceFees, invoiceTaxes, invoiceTotal);

			// Calculating totals of all invoices
			totalInvoiceSubtotal += invoiceSubtotal;
			totalInvoiceDiscounts += invoiceDiscounts;
			totalInvoiceFees += invoiceFees;
			totalInvoiceTaxes += invoiceTaxes;
			totalInvoiceTotal += invoiceTotal;

		}

		System.out.println(
				"======================================================================================================================================");
		System.out.printf("%-72s $%10.2f $%10.2f  $%10.2f  $%10.2f $%10.2f%n", "TOTALS", totalInvoiceSubtotal,
				totalInvoiceDiscounts, totalInvoiceFees, totalInvoiceTaxes, totalInvoiceTotal);
	}
	/**
	 * Prints detailed descriptions of all invoices within a group of invoices.
	 * @param invoiceList
	 */
	public static void printDetailed(LinkList<Invoice> invoiceList) {
		System.out.println("Invoice Details:");
		for (Invoice invoice : invoiceList) {
			
			// Initializing vars
			double invoiceSubTotal = 0;
			double invoiceDiscounts = 0;
			double invoiceTaxes = 0;
			double invoiceTotal = 0;

			// Prints Header
			System.out.println(
					"=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
			System.out.printf("Invoice %s \n------------------------------------------ \n", invoice.getInvoiceCode());

			// Print owner
			Person owner = invoice.getOwner();
			Address ownerAddress = owner.getAddress();
			int emails = owner.getEmail().size();
			String emailArray = Arrays.toString(owner.getEmail().toArray());
			if(emails == 0) {
				emailArray = "[No emails on record]";
			}
			System.out.println("Owner:");
			System.out.printf("\t%s \n\t%s \n\t%s \n", owner.toString(), emailArray,
					ownerAddress.toString());

			// Prints customer info
			Customer customer = invoice.getCustomerData();
			Address customerAddress = customer.getAddress();
			String acctType = "";
			if(customer.getCustomerType().compareTo("B") == 0) {
				acctType = "(Business Account)";
			}else {
				acctType = "(Personal Account)";
			}
			double extraFee = customer.getFees();
			double loyalDiscount = 0;
			System.out.println("Customer:");
			System.out.printf("\t%s %s \n\t%s \n", customer.getName(), acctType, customerAddress.toString());
			
			// Print product info
			System.out.println("Product:");
			System.out.printf("  %s %18s %67s %12s %9s %12s \n", "Code", "Description", "Subtotal", "Discount", "Taxes",
					"Total");
			System.out.println(
					"  -------------------------------------------------------------------------------------------------------------------------------------");
			ArrayList<? extends Product> productList = invoice.getProductList();
			int freeFlag = 0;

			// Counts freeflag for calculations
			for (Product prod : productList) {
				if (prod instanceof Rental) {
					freeFlag += 1;
				} else if (prod instanceof Repair) {
					freeFlag += 1;
				} else if (prod instanceof Towing) {
					freeFlag += 1;
				}
			}
			for (Product prod : productList) {
				
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

				System.out.printf("  %-12s%-70s $ %9.2f  $ %9.2f  $ %9.2f  $ %9.2f \n", prod.getProductCode(),
						description, productSubtotal, productDiscounts, productTaxes, productTotal);
				
				// Checks for fees to print under detailed description.
				extraVal = prod.feePrint();
				if (extraVal != null) {
					System.out.printf("\t      %s\n", extraVal);
				}
			}
			System.out.println(
					"=======================================================================================================================================");

			System.out.printf("%-84s $ %9.2f  $ %9.2f  $ %9.2f  $ %9.2f \n", "ITEM TOTALS:", invoiceSubTotal,
					invoiceDiscounts, invoiceTaxes, invoiceTotal);
			
			// Calculated loyalty discount, and prints value for loyalty discount or business fee, if applicable.
			loyalDiscount = customer.getLoyalDiscount(invoiceTotal);
			if (loyalDiscount != 0 && customer.getCustomerType().contains("P")) {
				System.out.printf("LOYAL CUSTOMER DISCOUT (5%% OFF) %93s %9.2f \n", "$", loyalDiscount);
			} else if (extraFee != 0 && customer.getCustomerType().contains("B")) {
				System.out.printf("BUSINESS ACCOUNT FEE: %103s %9.2f\n", "$", extraFee);
			}
			
			// Prints grand total.
			double grandTotal = invoiceTotal + loyalDiscount + extraFee;
			System.out.printf("%-123s $ %9.2f \n", "GRAND TOTAL:", grandTotal);
			
			// Print closer
			System.out.printf("\n\n\t\t THANK YOU FOR YOUR BUSINESS WITH US! \n\n\n\n");

			freeFlag = 0;

		}
	}

}
