<!DOCTYPE html>
<html>
<head>
	<title>Update Customer</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-customer-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>George Mason Univ. Info. Science and Tech. Dept.</h2>
		</div>
	</div>
	<div id="container">
		<h3>Update Customer</h3>
		<hr/>
		<form action="CustomerControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="customerId" value="${THE_CUSTOMER.id}" />
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" 
								   value="${THE_CUSTOMER.firstName}" /></td>
					</tr>
					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" 
								   value="${THE_CUSTOMER.lastName}" /></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" 
							  	   value="${THE_CUSTOMER.email}" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="CustomerControllerServlet">Back to List</a>
		</p>
	</div>
</body>
</html>