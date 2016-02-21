<%@ page language="java" import="java.sql.*"%>
<html>
<head>
<title>Read from mySQL Database</title>
</head>
<body>
	<form action="/ATM/CaptureAccountDetails" method="post">
		<p align="center">
			<b>Trasacton details</b><br>&nbsp;
		</p>
		<div class="top-row">
			<label for="accountNumber">Account Number: <input type="text"
				size="10" maxlength="15" name="accountNumber" /></label><br> <br>
			<label for="pin">Pin : <input type="text" size="10"
				maxlength="15" name="pin" id="pin" /></label><br> <br> <label
				for="amount">Amount: <input type="amount" size="10"
				maxlength="15" name="amount" /></label>
			<button type="submit" />
			Get Started
			</button>
			<br> <br> Credit:<input type="radio" name="transactionType"
				value="C" checked="checked"> <br> Debit:<input
				type="radio" name="transactionType" value="D"> <br>
		</div>
		</form>
		<div align="center" width="85%">
			<center>
				<table border="1" borderColor="#ffe9bf" cellPadding="0"
					cellSpacing="0" width="658" height="63">
					<tbody>
						<td bgColor="#008080" width="47" align="center" height="19"><font
							color="#ffffff"><b>Sr. No.</b></font></td>
						<td bgColor="#008080" width="107" height="19"><font
							color="#ffffff"><b>Transaction ID</b></font></td>
						<td bgColor="#008080" width="224" height="19"><font
							color="#ffffff"><b>Amount</b></font></td>
						<td bgColor="#008080" width="270" height="19"><font
							color="#ffffff"><b>Account Details ID</b></font></td>

						<%
							String DRIVER = "com.mysql.jdbc.Driver";
							Class.forName(DRIVER).newInstance();

							Connection con = null;
							ResultSet rst = null;
							Statement stmt = null;

							try {
								String url = "jdbc:mysql://localhost/USA?user=root&password=root";

								int i = 1;
								con = DriverManager.getConnection(url);
								stmt = con.createStatement();
								rst = stmt.executeQuery("SELECT * FROM TRANSACTION ");
								while (rst.next()) {

									if (i == (i / 2) * 2) {
						%>
						<tr>
							<td bgColor="#ffff98" vAlign="top" width="47" align="center"
								height="19"><%=i%>.</td>
							<td bgColor="#ffff98" vAlign="top" width="107" height="19"><%=rst.getString(1)%></td>
							<td bgColor="#ffff98" vAlign="top" width="224" height="19"><a
								href="<%=rst.getString(3)%>"><%=rst.getString(2)%></a>&nbsp;</td>
							<td bgColor="#ffff98" vAlign="top" width="270" height="19"><%=rst.getString(3)%></td>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td bgColor="#ffcc68" vAlign="top" width="47" align="center"
								height="19"><%=i%>.</td>
							<td bgColor="#ffcc68" vAlign="top" width="107" height="19"><%=rst.getString(1)%></td>
							<td bgColor="#ffcc68" vAlign="top" width="224" height="19"><a
								href="<%=rst.getString(3)%>"><%=rst.getString(2)%></a>&nbsp;</td>
							<td bgColor="#ffcc68" vAlign="top" width="270" height="19"><%=rst.getString(3)%></td>
						</tr>
						<%
							}

									i++;
								}
								rst.close();
								stmt.close();
								con.close();
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						%>

					</tbody>
				</table>
			</center>
		</div>
</body>
</html>