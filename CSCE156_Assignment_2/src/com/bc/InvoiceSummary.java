package com.bc;

import java.util.Collection;
import java.util.HashMap;


public class InvoiceSummary {
	
	public static void printInvoice(Collection <Invoice> invoiceList) {

		// Print top of table
		System.out.println("Executive Summary Report:");
		System.out.printf("%n%-10s %-25s %-30s %-10s %-10s  %-10s  %-10s  %-10s %n", "Code", "Owner", "Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		
		// Loop through collection of invoices
		for (Invoice invoice: invoiceList) {
			
			double invoiceSubtotal = 0;
			double invoiceDiscounts = 0;
			double invoiceFees = 0;
			double invoiceTaxes = 0;
			double invoiceTotal = 0;
			int freeFlag = 0; 
			int rentalFlag = 0; 
			int repairFlag = 0; 
			int towingFlag = 0; 

			// Access Product List
			for (Products p: invoice.getProductList()) {
				
				// Checking to see if towing is free
				if (p instanceof Rental && rentalFlag == 0) {
					freeFlag += 1;
					rentalFlag += 1;
				} else if (p instanceof Repair && repairFlag == 0) {
					freeFlag += 1;
					repairFlag += 1;
				} else if (p instanceof Towing && towingFlag == 0) {
					freeFlag += 1;
					towingFlag += 1;
				}
			}
			// Make calculations
			for (Products p: invoice.getProductList()) {
				
				// How can I access workValue to pass as an argument?
				invoiceSubtotal += p.getSubtotal(4);
				invoiceDiscounts += p.getDiscounts(freeFlag);
				invoiceTaxes += invoice.getCustomerData().getTaxes(0);
			
			}
			
			// Calculating total of 1 invoice
			invoiceFees += invoice.getCustomerData().getFees();
			invoiceTotal = invoiceSubtotal + invoiceDiscounts + invoiceFees + invoiceTaxes;
			
			// Print one invoice  
			System.out.printf("%n%-10s %-25s %-30s $%-10.2f $%-10.2f  $%-10.2f  $%-10.2f $%-10.2f%n", invoice.getInvoiceCode(), invoice.getOwner(), invoice.getCustomerData().getName(), invoiceSubtotal, invoiceDiscounts, invoiceFees, invoiceTaxes, invoiceTotal);

		}
	
		
		
		System.out.println("============================================================================================================================");

	}

}
