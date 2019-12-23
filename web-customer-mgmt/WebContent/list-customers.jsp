<%@ page import="java.util.*, edu.gmu.web.jdbc.*" %>

<!DOCTYPE html>
<html>
<head>
	<title>Customer Tracker App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<style>
		th, td {
			padding: 10px;
			text-align: center;
		}
	</style>
</head>
<%
	// get students from the request object (sent by servlet)
	List<Customer> customers = (List<Customer>) request.getAttribute("CUSTOMER_LIST");
%>
<body>
	<div id="wrapper">
		<div id="header" style="padding: 15px;">
			<h2>George Mason Univ. Info. Science and Tech. Dept.</h2>
		</div>
		<div id="container" style="padding: 15px;">
			<div id="content">
				<table border="1">
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email Address</th>
					</tr>
					<% for (Customer aCustomer : customers) { %>
					<tr>
						<td> <%= aCustomer.getFirstName() %> </td>
						<td> <%= aCustomer.getLastName() %> </td>
						<td> <%= aCustomer.getEmail() %> </td>
					</tr>
					<% } %>
				</table>
			</div>
		</div>
	</div>
</body>
</html>