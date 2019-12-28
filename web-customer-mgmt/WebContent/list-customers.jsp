<%-- the following is needed only for JSP Scriptlet code, not for JSTL tags. --%>
<%-- @ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Customer Tracker App</title>
	<link rel="stylesheet" href="css/style.css" >
</head>

<%-- 
    When we replace JSP Scriptlet code with JSTL tags, we don't need the following codes.
    For JSTL code will do it for us behind the scene.
    So, we put the variable, CUSTOMER_LIST directly in <c:forEach> iteration.
    
	// get students from the request object (sent by servlet)
	List<Customer> customers = (List<Customer>) request.getAttribute("CUSTOMER_LIST"); 
--%>

<body>
	
	<div id="wrapper">
		<div id="header">
			<h2>George Mason Univ. Info. Science and Tech. Dept.</h2>
		</div>
		<hr/>
		<div id="container">
			<div id="content">
			
				<!-- put new button to add a customer. -->
				<input type="button" value="Add Customer"
			   		   onclick="window.location.href='add-customer-form.jsp'; return false;"
			   		   class="add-customer-button"
				/>
				
				<table>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email Address</th>
						<th>Action</th>
					</tr>
					<c:forEach var="aCustomer" items="${CUSTOMER_LIST}">
						<!--  Setup update link for each customer using JSTL 
						      It defines a link string assigned to variable: tempLink -->
						<c:url var="updateLink" value="CustomerControllerServlet" >
							<c:param name="command" value="LOAD" />
							<c:param name="customerId" value="${aCustomer.id}" />
						</c:url>
						<!--  Setup delete link for each customer using JSTL 
						      It defines a link string assigned to variable: tempLink2 -->
						<c:url var="deleteLink" value="CustomerControllerServlet" >
							<c:param name="command" value="DELETE" />
							<c:param name="customerId" value="${aCustomer.id}" />
						</c:url>
						<tr>
							<td> ${aCustomer.firstName} </td>
							<td> ${aCustomer.lastName} </td>
							<td> ${aCustomer.email} </td>
							<td>
								<a href="${updateLink}">Update</a> |	
								<a href="${deleteLink}"
								   onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false" 
								>
									Delete
								</a>	
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>