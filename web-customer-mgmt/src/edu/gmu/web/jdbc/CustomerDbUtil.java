package edu.gmu.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
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
}
