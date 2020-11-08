package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;


/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The invoice class compiles data from Person, Products, and
 * Customer, in order to structure invoice data for BumprCars.
 */

public class Invoice {

	private String invoiceCode;
	private Person owner;
	private Customer customerData;
	private ArrayList<Product> productList;

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Person getOwner() {
		return owner;
	}

	public Customer getCustomerData() {
		return customerData;
	}

	public ArrayList<? extends Product> getProductList() {
		return productList;
	}

	public Invoice(String invoiceCode, Person owner, Customer customerData, ArrayList<Product> productList) {
		super();
		this.invoiceCode = invoiceCode;
		this.owner = owner;
		this.customerData = customerData;
		this.productList = productList;
	}

	public static ArrayList<Invoice> importInvoice(String filename, HashMap<String, Person> personMap,
			HashMap<String, Customer> customerMap, HashMap<String, Product> productMap) {
		// Initializes variables and scanner concats filename with filepath.
		ArrayList<Invoice> Invoices = new ArrayList<Invoice>();
		Scanner scanner = null;
		String fileString = "data/" + filename;
		File file = new File(fileString);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Skips line with number of entries.
		scanner.nextLine();
		
		// Loops through lines of the flat file and converts to invoice class.
		while (scanner.hasNext()) {
			String Token = scanner.nextLine();
			
			// Splits token and appropriately assigns strings to variables.
			String[] splitToken = Token.split(";");
			String invoiceCode = splitToken[0];
			Person owner = personMap.get(splitToken[1]);
			Customer customer = customerMap.get(splitToken[2]);
			// Initializes array lists for products in an invoice, and potential associated Repairs.
			ArrayList<Product> productList = new ArrayList<Product>();
			ArrayList<Product> potentialAssociations = new ArrayList<Product>();
			String associatedCode = null;
			String[] splitProducts = splitToken[3].split(",");
			// Loops through the array of product codes, and matches to the given hashmap of products.
			for (String prod : splitProducts) {
				String[] values = prod.split(":");
				Product newProd = null;
				double workVal = Double.parseDouble(values[1]);
				// Deep copies the product, to assign accurate work values to every individual product.
				newProd = Product.deepCopy(productMap.get(values[0]), workVal);
				productList.add(newProd);
				// Checks for an associated repair, and stores the value.  Also stores the potential concessions to make sure
				// discount is only applied to the appropriate concessions.
				if (values.length > 2) {
					associatedCode = values[2];
					potentialAssociations.add(newProd);
				}
			}
			// If an associated repair is found, checks to make sure the repair is in the invoice, and is actually a repair.
			if (associatedCode != null) {
				Concession.associatedRepairCheck(potentialAssociations, productList, associatedCode);
			}
			Invoices.add(new Invoice(invoiceCode, owner, customer, productList));
		}
		scanner.close();
		return Invoices;
	}
	public static ArrayList<Invoice> importInvoiceDB(HashMap<String, Person> personMap,
			HashMap<String, Customer> customerMap, HashMap<String, Product> productMap){
		
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rsProd = null;
		ResultSet rs = null;
		
		String query1 = "select Invoice.invoiceId, Invoice.invoiceCode, Person.personCode as owner, Customer.customerCode as customer from Invoice\r\n"
				+ "left join Person on Person.personId = Invoice.owner\r\n"
				+ "left join Customer on Customer.customerId = Invoice.customer;";
		String query2 = "select InvoiceProduct.invoiceProductId, InvoiceProduct.invoiceId, Product.productCode, InvoiceProduct.workValue, InvoiceProduct.associatedRepair from InvoiceProduct\r\n"
				+ "left join Product on Product.productId = InvoiceProduct.productId\r\n"
				+ "having InvoiceProduct.invoiceId = ?;";
		try {
			pre = conn.prepareStatement(query1);
			rs = pre.executeQuery();
			while(rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				ArrayList<Product> productList = new ArrayList<Product>();
				ArrayList<Product> potentialAssociations = new ArrayList<Product>();
				String associatedCode = null;
				pre = conn.prepareStatement(query2);
				pre.setInt(1,invoiceId);
				rsProd = pre.executeQuery();
				while(rsProd.next()) {
					Product newProd = null;
					double workVal = rsProd.getDouble("workValue");
					newProd = Product.deepCopy(productMap.get(rsProd.getString("productCode")), workVal);
					productList.add(newProd);
					if(rsProd.getString("associatedRepair") != null) {
						associatedCode = rsProd.getString("associatedRepair");
						potentialAssociations.add(newProd);
					}
				}
				String invoiceCode = rs.getString("invoiceCode");
				Person owner = personMap.get(rs.getString("owner"));
				Customer customer = customerMap.get(rs.getString("customer"));
				if (associatedCode != null) {
					Concession.associatedRepairCheck(potentialAssociations, productList, associatedCode);
				}
				invoices.add(new Invoice(invoiceCode, owner, customer, productList));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		DatabaseConnection.close(rs);
		DatabaseConnection.close(rsProd);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
		
		return invoices;
	}
	
	/**
	 * Gets invoice total for use in ordering invoices
	 * @return
	 */
	public double getInvoiceTotal() {

			double invoiceSubtotal = 0;
			double invoiceDiscounts = 0;
			double invoiceFees = 0;
			double invoiceTaxes = 0;
			double invoiceTotal = 0;
			int freeFlag = 0;

			// Checking if Towing is free
			for (Product p : this.getProductList()) {
				if (p instanceof Rental) {
					freeFlag += 1;
				} else if (p instanceof Repair) {
					freeFlag += 1;
				} else if (p instanceof Towing) {
					freeFlag += 1;
				}
			}

			// Calculating totals of 1 invoice
			for (Product p : this.getProductList()) {
				double productSubtotal = p.getSubtotal();
				double productDiscounts = p.getDiscounts(freeFlag);
				double productTaxes = this.getCustomerData().getTaxes(productSubtotal + productDiscounts);
				invoiceSubtotal += productSubtotal;
				invoiceDiscounts += productDiscounts;
				invoiceTaxes += productTaxes;

			}

			// Apply entire invoice fees and discounts
			double businessAccountFee = this.getCustomerData().getFees();
			double loyalDiscount = this.getCustomerData().getLoyalDiscount(invoiceSubtotal + invoiceTaxes);
			invoiceFees += businessAccountFee;
			invoiceDiscounts += loyalDiscount;
			invoiceTotal = invoiceSubtotal + invoiceDiscounts + invoiceFees + invoiceTaxes;

		return invoiceTotal;
	}

}
