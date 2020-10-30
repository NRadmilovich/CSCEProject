package com.bc;

import com.bc.ext.InvoiceData;

public class test {

	public static void main(String[] args) {
		// Testing DB clears
		InvoiceData.removeAllProducts();
		InvoiceData.removeAllInvoices();
		InvoiceData.removeAllCusomters();
		InvoiceData.removeAllPersons();
		

	}

}
