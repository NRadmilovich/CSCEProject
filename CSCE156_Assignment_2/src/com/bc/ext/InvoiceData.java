package com.bc.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * interacting with the database supporting this application. 16 methods in
 * total, add more if required. Do not change any method signatures or the
 * package name.
 * 
 * Adapted from Dr. Hasan's original version of this file
 * 
 * @author Chloe
 *
 */
public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		InvoiceData.removeAllInvoices();
		InvoiceData.removeAllCusomters();
		InvoiceData.removeAllEmails();
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;

		String query = "select address from Person";
		try {
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();
			query = "delete from Person";
			pre = conn.prepareStatement(query);
			pre.executeUpdate();
			while (rs.next()) {
				InvoiceData.deleteAddress(rs.getInt("address"),"P");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
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
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		// Build connections
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		String query = null;
		// Search for duplicate entries and adds to tables.
		int personId = InvoiceData.getPersonId(personCode);

		int addressId = InvoiceData.addAddress(street, city, zip, state, country);
		// Insert into DB if no matching person was returned.
		if (personId == 0) {
			query = "insert into Person(personCode, firstName, lastName, address) values (?,?,?,?)";
			try {
				pre = conn.prepareStatement(query);
				pre.setString(1, personCode);
				pre.setString(2, firstName);
				pre.setString(3, lastName);
				pre.setInt(4, addressId);
				pre.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;

		String query = "select Email.emailId from Email where email like ? ";
		String emailTest = "%" + email + "%";
		int personId = 0;

		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, emailTest);
			rs = pre.executeQuery();
			if (!rs.next()) {
				query = "select Person.personId from Person where personCode like ? ";
				pre = conn.prepareStatement(query);
				pre.setString(1, "%" + personCode + "%");
				rs = pre.executeQuery();
				if (!rs.next()) {
					throw new SQLException("Person does not exist!");
				} else {
					personId = rs.getInt("personId");
					query = "insert into Email(personId,email) values (?,?)";
					pre = conn.prepareStatement(query);
					pre.setInt(1, personId);
					pre.setString(2, email);
					pre.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCusomters() {
		InvoiceData.removeAllInvoices();
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;

		String query = "select address from Customer";
		try {
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();
			query = "delete from Customer";
			pre = conn.prepareStatement(query);
			pre.executeUpdate();
			while (rs.next()) {
				InvoiceData.deleteAddress(rs.getInt("address"),"C");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * 5. Method to add a customer record to the database with the provided data
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
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
			String name, String street, String city, String state, String zip, String country) {
		int customerId = InvoiceData.getCustomerId(customerCode);
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		int addressId = InvoiceData.addAddress(street, city, zip, state, country);
		int primaryContactId = InvoiceData.getPersonId(primaryContactPersonCode);
		if (customerId == 0) {
			String query = "insert into Customer(customerCode, customerType, customerName, primaryContact, address) values (?,?,?,?,?)";
			try {
				pre = conn.prepareStatement(query);
				pre.setString(1, customerCode);
				pre.setString(2, customerType);
				pre.setString(3, name);
				pre.setInt(4, primaryContactId);
				pre.setInt(5, addressId);
				pre.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * 6. Removes all product records from the database
	 */
	public static void removeAllProducts() {

		String query = "delete from InvoiceProduct";
		String query2 = "delete from Product";
		PreparedStatement pre = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.executeUpdate();
			pre = conn.prepareStatement(query2);
			pre.executeUpdate();
//			while(rs.next()) {
//				int productId = rs.getInt("productId");
//				
//				query = "delete from InvoiceProduct where productId = ?";
//				pre = conn.prepareStatement(query);
//				pre.setInt(1, productId);
//				pre.executeUpdate();
//			}
//			query = "delete from Product";
//			pre = conn.prepareStatement(query);
//			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
	}

	/**
	 * 7. Adds a concession record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param unitCost
	 */
	public static void addConcession(String productCode, String productLabel, double unitCost) {

		// Checks for duplicate
		int productId = InvoiceData.getProductId(productCode);
		if (productId != 0) {
			return;
		}
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

		// Checks for duplicate
		int productId = InvoiceData.getProductId(productCode);
		if (productId != 0) {
			return;
		}
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

		// Checks for duplicate
		int productId = InvoiceData.getProductId(productCode);
		if (productId != 0) {
			return;
		}
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
	public static void addRental(String productCode, String productLabel, double dailyCost, double deposit,
			double cleaningFee) {

		// Checks for duplicate
		int productId = InvoiceData.getProductId(productCode);
		if (productId != 0) {
			return;
		}
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
	 * 
	 * 11. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		// Establish connections
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		// Delete all ProductInvoice entries
		int iPCount = 0;
		String query = "select count(InvoiceProduct.invoiceProductId) as count from InvoiceProduct;";
		try {
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();
			if (rs.next()) {
				iPCount = rs.getInt("count");
			}
			if (iPCount > 0) {
				query = "delete from InvoiceProduct where InvoiceProduct.invoiceProductId = ?";
				while (iPCount > 0) {
					pre = conn.prepareStatement(query);
					pre.setInt(1, iPCount);
					pre.executeUpdate();
					iPCount--;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Delete all invoices.
		int prodCount = 0;
		query = "select count(Invoice.invoiceId) as count from Invoice;";
		try {
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();
			if (rs.next()) {
				prodCount = rs.getInt("count");
			}
			if (prodCount > 0) {
				query = "delete from Invoice where Invoice.invoiceId = ?";
				while (prodCount > 0) {
					pre = conn.prepareStatement(query);
					pre.setInt(1, prodCount);
					pre.executeUpdate();
					prodCount--;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * 12. Adds an invoice record to the database with the given data.
	 * 
	 * @param invoiceCode
	 * @param ownerCode
	 * @param customertCode
	 */
	public static void addInvoice(String invoiceCode, String ownerCode, String customerCode) {
		/* TODO */
		// Checks for duplicate
		int invoiceId = InvoiceData.getInvoiceId(invoiceCode);
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		String query = null;
		if (invoiceId == 0) {
			query = "insert into Invoice(invoiceCode, owner, customer) values (?,?,?);";
			int ownerId = InvoiceData.getPersonId(ownerCode);
			int customerId = InvoiceData.getCustomerId(customerCode);
			try {
				pre = conn.prepareStatement(query);
				pre.setString(1, invoiceCode);
				pre.setInt(2, ownerId);
				pre.setInt(3, customerId);
				pre.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * 13. Adds a particular Towing (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of miles towed
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param milesTowed
	 */
	public static void addTowingToInvoice(String invoiceCode, String productCode, double milesTowed) {
		String repairCode = null;
		InvoiceData.addProductToInvoice(invoiceCode, productCode, milesTowed, repairCode);
	}

	/**
	 * 14. Adds a particular Repair (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of hours worked
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param hoursWorked
	 */
	public static void addRepairToInvoice(String invoiceCode, String productCode, double hoursWorked) {
		String repairCode = null;
		InvoiceData.addProductToInvoice(invoiceCode, productCode, hoursWorked, repairCode);
	}

	/**
	 * 15. Adds a particular Concession (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given number of quantity. NOTE: repairCode may be null
	 * 
	 * 
	 * Change associated repair from boolean to string
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param quantity
	 * @param repairCode
	 */
	public static void addConcessionToInvoice(String invoiceCode, String productCode, int quantity, String repairCode) {
		InvoiceData.addProductToInvoice(invoiceCode, productCode, quantity, repairCode);
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
		String repairCode = null;
		InvoiceData.addProductToInvoice(invoiceCode, productCode, daysRented, repairCode);
	}

	/**
	 * Checks for duplicates, and adds country.
	 * 
	 * @return countryId
	 * @param country
	 * 
	 */
	public static int addCountry(String country) {
		// Checks for duplicate country
		int countryId = InvoiceData.getCountryId(country);
		// If no duplicate is found, adds country.
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		if (countryId == 0) {
			String query = "insert into Country(countryName) values (?)";
			try {
				pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pre.setString(1, country);
				pre.executeUpdate();
				rs = pre.getGeneratedKeys();
				rs.next();
				countryId = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
		return countryId;
	}

	/**
	 * Checks for duplicates, and adds country.
	 * 
	 * @return stateId
	 * @param state
	 * 
	 */
	public static int addState(String state) {
		// Checks for duplicate State
		int stateId = InvoiceData.getStateId(state);
		// If no duplicate, adds state
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		if (stateId == 0) {
			String query = "insert into State(stateName) values (?)";
			try {
				pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pre.setString(1, state);
				pre.executeUpdate();
				rs = pre.getGeneratedKeys();
				rs.next();
				stateId = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
		return stateId;
	}

	/**
	 * Checks for duplicates, and adds country.
	 * 
	 * @return stateId
	 * @param state
	 * 
	 */
	public static int addAddress(String street, String city, String zip, String state, String country) {
		// Gets Id for State and country
		int stateId = InvoiceData.addState(state);
		int countryId = InvoiceData.addCountry(country);
		// Checks for Duplicate Address
		int addressId = InvoiceData.getAddressId(street, city, stateId);
		// If no duplicate, adds address
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		if (addressId == 0) {
			String query = "insert into Address(street, city, state, zip, country) values (?,?,?,?,?)";
			try {
				pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pre.setString(1, street);
				pre.setString(2, city);
				pre.setString(4, zip);
				pre.setInt(3, stateId);
				pre.setInt(5, countryId);
				pre.executeUpdate();
				rs = pre.getGeneratedKeys();
				rs.next();
				addressId = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
		return addressId;
	}

/**Helper to delete an address entry.
 * Checks Person or Customer tables for matching entries.
 * 
 * @param index
 * @param table = Table who values SHOULD be deleted.  C for Customer, P for Person
 */
	private static void deleteAddress(int index, String table) {
		// Checks for which table to delete
		if(table.contains("C")) {
			table = "Person";
		}else {
			table = "Customer";
		}
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		String query = "select Address.addressId, Person.address as Person, Customer.address as Customer from Address\r\n"
				+ "left join Person on Person.address = Address.addressId\r\n"
				+ "left join Customer on Customer.address = Address.addressId\r\n" + "having addressId = ?;";
		try {
			pre = conn.prepareStatement(query);
			pre.setInt(1, index);
			rs = pre.executeQuery();
			rs.next();
			if (rs.getInt(table) == 0) {
				query = "delete from Address where Address.addressId = ?";
				pre = conn.prepareStatement(query);
				pre.setInt(1, index);
				pre.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}
	public static void removeAllEmails() {
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;

		String query = "delete from Email";
		try {
			pre = conn.prepareStatement(query);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
	}

	/**
	 * Helper: generalizes adding a product and is called by each addToInvoice
	 * method for products
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param workValue
	 */
	private static void addProductToInvoice(String invoiceCode, String productCode, double workValue,
			String repairCode) {

		String query = "insert into InvoiceProduct(invoiceId, productId, workValue, associatedRepair) values (?,?,?,?);";
		int productId = InvoiceData.getProductId(productCode);
		int invoiceId = InvoiceData.getInvoiceId(invoiceCode);
		PreparedStatement pre = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setInt(1, invoiceId);
			pre.setInt(2, productId);
			pre.setDouble(3, workValue);
			// Checks for the existence of an associatedRepair
			if (repairCode != null) {
				pre.setString(4, repairCode);
			} else {
				pre.setString(4, null);
			}
			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
	}

	/**
	 * Helper: gets productId for use in checking for duplication and adding
	 * Products to an Invoice
	 * 
	 * @param productCode
	 * @return
	 */
	private static int getProductId(String productCode) {
		String query = "select productId from Product where productCode = ?;";
		int productId = 0;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);

		return productId;
	}

	/**
	 * Helper: gets invoiceId for use in checking for duplication and adding things
	 * to an Invoice
	 * 
	 * @param productCode
	 * @return
	 */
	private static int getInvoiceId(String invoiceCode) {
		String query = "select invoiceId from Invoice where invoiceCode = ?;";
		int invoiceId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, invoiceCode);
			rs = pre.executeQuery();
			if (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);

		return invoiceId;
	}

	/**
	 * Helper: gets personId for use in checking for duplication and adding things
	 * to a person
	 * 
	 * @param personCode
	 * @return
	 */
	private static int getPersonId(String personCode) {
		String query = "select personId from Person where personCode = ?;";
		int personId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, personCode);
			rs = pre.executeQuery();
			if (rs.next()) {
				personId = rs.getInt("personId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);

		return personId;
	}

	/**
	 * Helper: gets countryId for use in checking for duplication and adding things
	 * to a Country
	 * 
	 * @param productCode
	 * @return
	 */
	private static int getCountryId(String countryName) {
		String query = "select countryId from Country where countryName = ?;";
		int countryId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, countryName);
			rs = pre.executeQuery();
			if (rs.next()) {
				countryId = rs.getInt("countryId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);

		return countryId;
	}

	/**
	 * Helper: gets customerId for use in checking for duplication and adding things
	 * to a Customer
	 * 
	 * @param productCode
	 * @return
	 */
	private static int getCustomerId(String customerCode) {
		String query = "select customerId from Customer where customerCode = ?;";
		int customerId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, customerCode);
			rs = pre.executeQuery();
			if (rs.next()) {
				customerId = rs.getInt("customerId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);

		return customerId;
	}

	/**
	 * Helper: gets customerId for use in checking for duplication and adding things
	 * to a Customer
	 * 
	 * @param productCode
	 * @return
	 */
	private static int getStateId(String stateName) {
		String query = "select stateId from State where stateName = ?;";
		int stateId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, stateName);
			rs = pre.executeQuery();
			if (rs.next()) {
				stateId = rs.getInt("stateId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);

		return stateId;
	}

	private static int getAddressId(String street, String city, int stateId) {
		String query = "Select addressId from Address where (Address.street like ?) and (Address.city like ?) and (Address.state = ?);";
		int addressId = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = DatabaseConnection.connectionBuilder();
		try {
			pre = conn.prepareStatement(query);
			pre.setString(1, "%" + street + "%");
			pre.setString(2, "%" + city + "%");
			pre.setInt(3, stateId);
			rs = pre.executeQuery();
			if (rs.next()) {
				addressId = rs.getInt("addressId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(conn);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(rs);
		return addressId;
	}

}
