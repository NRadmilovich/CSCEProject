package com.bc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class InvoiceSummary {
	
	public static void printInvoice(Collection <Invoice> invoiceList) {

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
				
				invoiceSubtotal += p.getSubtotal(workValueMap.get(p.productCode));
				invoiceDiscounts += p.getDiscounts(freeFlag);
				invoiceTaxes += invoice.getCustomerData().getTaxes(p.getSubtotal(workValueMap.get(p.productCode)) + p.getDiscounts(freeFlag));
				
				// Test
				System.out.printf("%n%.2f %.2f %.2f%n", p.getSubtotal(workValueMap.get(p.productCode)), invoiceDiscounts, invoice.getCustomerData().getTaxes(p.getSubtotal(workValueMap.get(p.productCode))));
			}
			invoiceFees += invoice.getCustomerData().getFees();
			invoiceDiscounts += invoice.getCustomerData().getLoyalDiscount(invoiceSubtotal + invoiceTaxes);
			invoiceTotal = invoiceSubtotal + invoiceDiscounts + invoiceFees + invoiceTaxes;
			
			// Calculating totals of all invoices
			totalInvoiceSubtotal += invoiceSubtotal;
			totalInvoiceDiscounts += invoiceDiscounts;
			totalInvoiceFees += invoiceFees;
			totalInvoiceTaxes += invoiceTaxes;
			totalInvoiceTotal += invoiceTotal;
			
			// Print one invoice  
			System.out.printf("%n%-10s %-25s %-30s $%-10.2f $%-10.2f  $%-10.2f  $%-10.2f $%-10.2f%n", invoice.getInvoiceCode(), invoice.getOwner(), invoice.getCustomerData().getName(), invoiceSubtotal, invoiceDiscounts, invoiceFees, invoiceTaxes, invoiceTotal);
	
		}
		
		System.out.println("============================================================================================================================");
		System.out.printf("%-67s $%-10.2f $%-10.2f  $%-10.2f  $%-10.2f $%-10.2f%n", "TOTALS", totalInvoiceSubtotal, totalInvoiceDiscounts, totalInvoiceFees, totalInvoiceTaxes, totalInvoiceTotal);

	}

}
