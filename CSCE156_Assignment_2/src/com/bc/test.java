package com.bc;

import java.util.ArrayList;
import java.util.Collections;
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
 * Test class to work with specific methods.
 */

public class test {

	public static void main(String[] args) {
		ArrayList<Person> people = Person.importPersonDB();
		System.out.println("People retrieved!");
		HashMap<String,Person> personMap = Person.personMap(people);
		ArrayList<Customer> customers = Customer.importCustomerDB(personMap);
		System.out.println("Customers retrieved!");
		ArrayList<Product> product = Product.importProducts();
		System.out.println("Products retrieved!");
		// Creates Product and Customer Hashmaps
		HashMap<String,Product> productMap = Product.productMap(product);
		HashMap<String,Customer> customerMap = Customer.customerMap(customers);
		// Creates Invoice ArrayList based on invoice flat file
		ArrayList<Invoice> invoices = Invoice.importInvoiceDBAL(personMap, customerMap, productMap);
		Collections.shuffle(invoices);
		test.printSummaryAL(invoices);
		LinkList<Invoice> test = new LinkList<Invoice>();
		for(Invoice invoice:invoices) {
			test.add(invoice);
		}
		InvoiceReport.printSummary(test);
		
		

	}
	public static void printSummaryAL(ArrayList<Invoice> invoiceList) {

		// Print top of table
		System.out.println("Executive Summary Report:");
		System.out.printf("%n%-10s %-25s %-30s %-11s %-11s  %-11s  %-10s  %-10s %n", "Code", "Owner",
				"Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------");

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
			System.out.printf("%-10s %-25s %-30s $%10.2f $%10.2f  $%10.2f  $%10.2f $%10.2f%n", invoice.getInvoiceCode(),
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
				"=================================================================================================================================");
		System.out.printf("%-67s $%10.2f $%10.2f  $%10.2f  $%10.2f $%10.2f%n", "TOTALS", totalInvoiceSubtotal,
				totalInvoiceDiscounts, totalInvoiceFees, totalInvoiceTaxes, totalInvoiceTotal);
	}

}
