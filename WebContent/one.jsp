<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ATM</title>
</head>
<body>
	<form action="/ATM/CaptureFormData" method="post">
		<div class="container">
			Account Number is <%= request.getAttribute("accountNumber") %> <br>
			PIN value is <%= request.getAttribute("pin") %><br> Amount value is <%= request.getAttribute("amount") %> 
				<br>


			
		</div>



	</form>
</body>
</html>