package com.bc;

import java.util.Collection;
import java.util.HashMap;


public class InvoiceSummary {
	
	public static void printInvoice(Collection <Invoice> invoiceList) {

		
		double invoiceSubtotal = 0;
		double invoiceDiscounts = 0;
		double invoiceFees = 0;
		double invoiceTaxes = 0;
		double invoiceTotal = 0;

		
		// Print top of table
		System.out.println("Executive Summary Report:");
		System.out.printf("%n%-10s %-25s %-30s %-10s %-10s  %-10s  %-10s  %-10s %n", "Code", "Owner", "Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		
		// Loop through collection of invoices
		for (Invoice invoice: invoiceList) {
			
			// Access Product List
//			for (Products p: invoice.getProductList()) {
//				
//				invoiceSubtotal += p.getSubtotal(10.0);
//				invoiceSubtotal += p.getSubtotal(10.0);
//				invoiceSubtotal += p.getSubtotal(10.0);
//				invoiceSubtotal += p.getSubtotal(10.0);
//			
//			}
			
			
				
			
			
			// Format table  
			System.out.printf("%n%-10s %-25s %-30s %-10f %-10f  %-10f  %-10f %-10f%n", invoice.getInvoiceCode(), invoice.getOwner(), invoice.getCustomerData().getName(), invoiceSubtotal, invoiceDiscounts, invoiceFees, invoiceTaxes, invoiceTotal);

		}
	
		
		
		System.out.println("============================================================================================================================");

	}

}
