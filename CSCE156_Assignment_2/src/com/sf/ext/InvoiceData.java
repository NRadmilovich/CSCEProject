package com.sf.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bc.DatabaseConnection;
/* DO NOT change or remove the import statements beneath this.
 * They are required for the webgrader to run this phase of the project.
 * These lines may be giving you an error; this is fine. 
 * These are webgrader code imports, you do not need to have these packages.
 */
import com.bc.model.Concession;
import com.bc.model.Invoice;
import com.bc.model.Customer;
import com.bc.model.Towing;
import com.bc.model.Person;
import com.bc.model.Product;
import com.bc.model.Rental;
import com.bc.model.Repair;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 16 methods in total, add more if required.
 * Do not change any method signatures or the package name.
 * 
 * Nick - 1,2,3,4,5,11,12,15
 * 
 * Adapted from Dr. Hasan's original version of this file
 * @author Chloe
 *
 */
public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 *  Delete person and address
	 *  Do they want the customer contact null, or
	 *  to keep the person code on file?
	 */
	public static void removeAllPersons() {
		/* TODO*/
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		/* TODO*/
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		/* TODO*/
	}

	/**
	 * 4. Method that removes every customer record from the database
	 * Do they want customer Id saved in invoices, or null?
	 */
	public static void removeAllCusomters() {
		/* TODO*/
	}
	
	/**
	 * 5. Method to add a customer record to the database with the provided data
	 * 
	 * If customer exists, do they want us to update info or throw error?
	 * 
	 * @param customerCode
	 * @param customerType
	 * @param primaryContactPersonCode
	 * @param name
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode, String name, String street, String city, String state, String zip, String country) {
		/* TODO*/
	}
	
	/**
	 * 6. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		/* TODO*/
	}

	/**
	 * 7. Adds a concession record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param unitCost
	 */
	public static void addConcession(String productCode, String productLabel, double unitCost) {
	
		String query = "insert into Product (productCode, productType, productLabel, unitCost) values (?,'C',?,?);";
		PreparedStatement pre = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, productCode);
			pre.setString(2, productLabel);
			pre.setDouble(3, unitCost);
			pre.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
	}

	/**
	 * 8. Adds a repair record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param proudctLabel
	 * @param partsCost
	 * @param laborRate
	 */
	public static void addRepair(String productCode, String productLabel, double partsCost, double laborRate) {
		
		String query = "insert into Product (productCode, productType, productLabel, partsCost, hourlyLaborCost) values (?,'F',?,?,?);";
		PreparedStatement pre = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, productCode);
			pre.setString(2, productLabel);
			pre.setDouble(3, partsCost);
			pre.setDouble(4, laborRate);
			pre.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
	}

	/**
	 * 9. Adds a towing record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param costPerMile
	 */
	public static void addTowing(String productCode, String productLabel, double costPerMile) {
		
		String query = "insert into Product (productCode, productType, productLabel, costPerMile) values (?,'T',?,?);";
		PreparedStatement pre = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, productCode);
			pre.setString(2, productLabel);
			pre.setDouble(3, costPerMile);
			pre.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
	}

	/**
	 * 10. Adds a rental record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param dailyCost
	 * @param deposit
	 * @param cleaningFee
	 */
	public static void addRental(String productCode, String productLabel, double dailyCost, double deposit, double cleaningFee) {
		
		String query = "insert into Product (productCode, productType, productLabel, dailyCost, deposit, cleaningFee) values (?,'R',?,?,?,?);";
		PreparedStatement pre = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, productCode);
			pre.setString(2, productLabel);
			pre.setDouble(3, dailyCost);
			pre.setDouble(4, deposit);
			pre.setDouble(5, cleaningFee);
			pre.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
	}

	/**
	 * Drop tables?
	 * 
	 * 11. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
        /* TODO*/
	}

	/**
	 * 12. Adds an invoice record to the database with the given data.
	 * 
	 * @param invoiceCode
	 * @param ownerCode
	 * @param customertCode
	 */
	public static void addInvoice(String invoiceCode, String ownerCode, String customerCode) {
		/* TODO*/
	}

	/**
	 * 13. Adds a particular Towing (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of miles towed
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param milesTowed
	 */
	public static void addTowingToInvoice(String invoiceCode, String productCode, double milesTowed) {	
		InvoiceData.addProductToInvoice(invoiceCode, productCode, milesTowed);
	}

	/**
	 * 14. Adds a particular Repair (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of hours worked
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param hoursWorked
	 */
	public static void addRepairToInvoice(String invoiceCode, String productCode, double hoursWorked) {
		InvoiceData.addProductToInvoice(invoiceCode, productCode, hoursWorked);
	}

     /**
      * 15. Adds a particular Concession (corresponding to <code>productCode</code> to an 
      * invoice corresponding to the provided <code>invoiceCode</code> with the given
      * number of quantity.
      * NOTE: repairCode may be null
      * 
      * 
      * Change associated repair from boolean to string
      * 
      * @param invoiceCode
      * @param productCode
      * @param quantity
      * @param repairCode
      */
    public static void addConcession(String invoiceCode, String productCode, int quantity, String repairCode) {
    	/* TODO*/
    }
	
    /**
     * 16. Adds a particular Rental (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of days rented. 
     * 
     * @param invoiceCode
     * @param productCode
     * @param daysRented
     */
    public static void addRentalToInvoice(String invoiceCode, String productCode, double daysRented) {
		InvoiceData.addProductToInvoice(invoiceCode, productCode, daysRented);
    }
    
    public static void addProductToInvoice(String invoiceCode, String productCode, double workValue) {
    	String query = "select productId from Product where productCode = ?;";
		String query2 = "select invoiceId from Invoice where invoiceCode = ?;";
		int productId = 0, invoiceId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, productCode);
			rs = pre.executeQuery();
			if (rs.next()) {
				productId = rs.getInt("productId");
			} 	
			pre = conn.prepareStatement(query2);
			pre.setString(1, invoiceCode);
			rs = pre.executeQuery();
			if (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
			} 		
			query = "insert into InvoiceProduct(invoiceId, productId, workValue) values (?,?,?);";
			pre = conn.prepareStatement(query);
			pre.setInt(1, invoiceId);
			pre.setInt(2, productId);
			pre.setDouble(3, workValue);
			pre.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);
    }
}
