package edu.gmu.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class CustomerControllerServlet
 */
@WebServlet("/CustomerControllerServlet")
public class CustomerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDbUtil customerDbUtil;

	@Resource(name="jdbc/web_customer_tracker")
	private DataSource dataSource;
	
	// the following init() method is called by the Java EE server or by Tomcat,
	// when this Servlet is first loaded or initialized.
	// this can be customized by us, which is a part of API.
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		// create our customer database util and pass in connection pool (data-source object)
		try {
			customerDbUtil = new CustomerDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			/* read the "command" parameter, which is the hidden-type input of
			   add-customer-form.jsp page and the value is "ADD". */
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default is listing customers.
			if (theCommand == null)  
				theCommand = "LIST";
			// route to the appropriate method.
			switch (theCommand) {
			
			case "LIST":
				// list the customers .. in MVC fashion.
				listCustomers(request, response);
				break;
				
			case "ADD":
				// add a new customer.
				addCustomer(request, response);
				break;
			
			case "LOAD":
				// load a customer with id, which is selected by update link.
				loadCustomer(request, response);
				break;
			
			default:
				listCustomers(request, response);
			}
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private void loadCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read customer id from list-customer.jsp file.
		String aCustomerId = request.getParameter("customerId");
		
		// get a customer from database (CustomerDbUtil.java)
		Customer aCustomer = CustomerDbUtil.getCustomer(aCustomerId);
		
		// place customer in the request attribute.
		request.setAttribute("THE_CUSTOMER", aCustomer);
		
		// send data to JSP page (update-customer-form.jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-customer-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read customer info. from form data.
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
				
		// create a new customer object.
		Customer aCustomer = new Customer(firstName, lastName, email);
		
		// add the customer to the database.
		CustomerDbUtil.addCustomer(aCustomer);
		
		// send back to main page (the customer list)
		listCustomers(request, response);
	}

	private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get the customer data from the Model: helper class (CustomerDbUtil.java)
		List<Customer> customers = CustomerDbUtil.getCustomers();
		
		// add customers to request object.
		request.setAttribute("CUSTOMER_LIST", customers);
		
		// get request dispatcher.
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-customers.jsp");
		
		// forward to JSP.
		dispatcher.forward(request, response);
	}
}
