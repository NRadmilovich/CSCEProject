package com.bc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * CSCE 156
 * 
 * Authors: Caden Kirby Nick Radmilovich
 * 
 * 10/1/2020
 * 
 * Description: The Address class stores address information, which is passed to the Person class.
 */

public class Address {
	private String Street;
	private String City;
	private String State;
	private String Zip;
	private String Country;
	
	// Getters/Setters
	public String getStreet() {
		return Street;
	}
	public String getCity() {
		return City;
	}
	public String getState() {
		return State;
	}
	public String getZip() {
		return Zip;
	}
	public String getCountry() {
		return Country;
	}
	// Constructor
	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.Street = street;
		this.City = city;
		this.State = state;
		this.Zip = zip;
		this.Country = country;
	}
	public Address() {
		super();
	}
	// Method
	public static Address stringToAddress(String Address) {
		Address out = new Address();
		String[] Tokens = Address.split(",");
		for(int i=0; i < Tokens.length; i++) {
			Tokens[i] = Tokens[i].trim();
		}
		out.Street = Tokens[0];
		out.City = Tokens[1];
		out.State = Tokens[2];
		out.Country = Tokens[4];
		if(Tokens[3] != null) {
			out.Zip = Tokens[3];
		}
		return out;
	}
	// Retrieves Address from DB
	public static Address getAddressDB(int addressId){
		Address address= null;
		// Build Connections
		Connection conn = DatabaseConnection.connectionBuilder();
		PreparedStatement pre = null;
		ResultSet rs = null;
		String query = "Select Address.addressId, Address.street, Address.city, Address.zip, State.stateName, Country.countryName from Address\r\n"
				+ "left join State on State.stateId = Address.state\r\n"
				+ "left join Country on Country.countryId = Address.country\r\n"
				+ "having Address.addressId = ?";
		try {
			pre = conn.prepareStatement(query);
			pre.setInt(1, addressId);
			rs = pre.executeQuery();
			rs.next();
			address = new Address(rs.getString("street"),rs.getString("city"), rs.getString("stateName"),rs.getString("zip"),rs.getString("countryName"));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pre);
		DatabaseConnection.close(conn);
		return address;
	}
	@Override
	public String toString() {
		return Street + "\n\t" + City + ", " + State +" " + Country + " "
				+ Zip;
	}
	
	
}
