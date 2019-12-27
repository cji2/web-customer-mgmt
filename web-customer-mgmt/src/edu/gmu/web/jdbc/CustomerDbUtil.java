package edu.gmu.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import edu.gmu.web.jdbc.Customer;

public class CustomerDbUtil {
	
	// Servlet will deal with 'DataSource' object
	private static DataSource dataSource;
	
	// constructor
	public CustomerDbUtil(DataSource aDataSource) {
		dataSource = aDataSource;
	}
	
	public static List<Customer> getCustomers() throws Exception {
		
		// create an empty array list of Customer.
		List<Customer> customers = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection to the database.
			myConn = dataSource.getConnection();
			
			// create SQL statements.
			String sql = "select * from customer order by last_name";
			myStmt = myConn.createStatement();
			
			// execute SQL query.
			myRs = myStmt.executeQuery(sql);
			
			// process query result set.
			while(myRs.next()) {
				
				// retrieve data from result set row.
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// create new customer object.
				Customer aCustomer = new Customer(id, firstName, lastName, email);
				
				// add it to the list of customers
				customers.add(aCustomer);
			}
			
			// return the list
			return customers;
		}
		finally {
			// close JDBC objects, which prevents from memory leak.
			close(myConn, myStmt, myRs);
		}
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				/* it doesn't really close it ... just puts back in connection pool
				   this will make the connection pool available for other application. */
				myConn.close();   
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public static void addCustomer(Customer aCustomer) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get DB connection
			myConn = dataSource.getConnection();
			
			/* Create SQL for insert into customer, which is the name of table. 
			   we provide column names, and values with place holders (?) */
			String sql = "insert into customer "
						+ "(first_name, last_name, email) "
						+ "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			/* Set the param values for the customer. 
			   We already get an object of Customer class as a parameter. 
			   And the param begins with one, not zero (we have three ?: place holders). */
			myStmt.setString(1, aCustomer.getFirstName());
			myStmt.setString(2, aCustomer.getLastName());
			myStmt.setString(3, aCustomer.getEmail());
			
			// execute SQL insert.
			myStmt.execute();
		}
		finally {
			// close JDBC objects, which prevents from memory leak.
			close(myConn, myStmt, null);
		}
		
	}

	public static Customer getCustomer(String aCustomerId) throws Exception {
		
		Customer aCustomer = null;
		int customerId;
		
		// JDBC object setting.
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// convert aCustomerId to int.
			customerId = Integer.parseInt(aCustomerId);
			
			// get connection to database.
			myConn = dataSource.getConnection();
			
			// create SQL to get selected customer (? means place holder).
			String sql = "select * from customer where id=?";
					
			// create prepared statement based on SQL.
			myStmt = myConn.prepareStatement(sql);
			
			// set params (we have only one ?: place holder).
			myStmt.setInt(1, customerId);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row.
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// create a Customer object with the customer id.
				aCustomer = new Customer(customerId, firstName, lastName, email);
			}
			else {
				throw new Exception("Could not find customer id: " + customerId);
			}
			return aCustomer;
		}
		finally {
			// close JDBC objects, which prevents from memory leak.
			close(myConn, myStmt, myRs);
		}
	}
}
