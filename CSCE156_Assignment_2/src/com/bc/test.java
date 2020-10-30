package com.bc;

import com.bc.ext.InvoiceData;

public class test {

	public static void main(String[] args) {
		// Testing InvoiceData
		InvoiceData.addPerson("12345", "Nick", "Radmilovich", "123 not my Address", "Lincoln", "NE", "68528", "USA");
		InvoiceData.addCustomer("12345", "P", "12345", "My business is personal", "Still not my address", "Lincoln", "NE", "68528", "USA");
		InvoiceData.addInvoice("54321", "12345", "12345");
		InvoiceData.addEmail("12345", "Nradmilovich4@gmail.com");
		// Tested updated Associated Repair with Strings
		InvoiceData.addConcessionToInvoice("54321", "aldkjflaksjdfh", 25, null);
		InvoiceData.addConcessionToInvoice("54321", "aldkjflaksjdfh", 15, "nfein2929");
		InvoiceData.addRepairToInvoice("54321", "nfein2929", 2100.0);
		
		// Testing DB clears
		InvoiceData.removeAllProducts();
		InvoiceData.removeAllPersons();
		

	}

}
