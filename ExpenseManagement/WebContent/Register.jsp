<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<!-- register page -->
<div id="register page" data-role="page" data-theme="b">
	<header data-role="header">
	 <h1>Register</h1>
	 </header>
 	<div data-role="content" data-position="fixed">
		<form action='ExpenseManagementController' method='post'>
		<label for="username">User name:</label>
		<input type="text" name="username" id="username" data-clear-btn="true" placeholder="Enter Username...">
		
		<label for="password">Password</label>
		<input type="password" name="password" id="password" data-clear-btn="true" placeholder="Enter password...">
		
		<label for="password">Re-Enter Password</label>
		<input type="password" name="repassword" id="repassword" data-clear-btn="true" placeholder="Re-Enter password...">
		
		<label for="email">Email</label>
		<input type="email" name="email" id="email" data-clear-btn="true" placeholder="Enter email...">
		
		<input type='hidden' name='command' value='okregister'> 
		<input type='submit' data-icon='check' value ='ok'>
		<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		%>
		</form>
		<form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='cancleregister'> 
	        <input type='submit' data-theme="e" data-icon='back' value ='Cancle'>
        </form>
		
	</div>
</div>
</body>
</html>