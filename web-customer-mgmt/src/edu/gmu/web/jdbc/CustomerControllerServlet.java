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
			// list the students .. in MVC fashion.
			listCustomers(request, response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
