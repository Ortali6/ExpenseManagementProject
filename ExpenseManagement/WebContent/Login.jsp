<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<!-- login page -->
<div id="loginpage" data-role="page" data-theme="b">
	<header data-role="header">
	 <h1>Login</h1>
	 </header>
	 <div role="main" class="ui-content">
 	<div data-role="content" data-position="fixed">
 		 <%  
	    	Cookie cookie[]=request.getCookies();  
        	if(cookie != null) 
        	{   		
        		for(int i = 0; i < cookie.length; i++) 
        		{
	            	String username = cookie[i].getName();
	           		if(username.equals("userName")) 
	           		{
	           			%>
	           			<%@ taglib uri="/WEB-INF/tlds/tag.tld" prefix="test" %>
	           			<test:theusername userName="<%= cookie[i].getValue()%>"/>
						<%
	           		}
        		}
             }
        %>
        <form action='ExpenseManagementController' method='post'>
	        
	        <label for="username">User name:</label>
			<input type="text" name="username" id="username" data-clear-btn="true" placeholder="Enter Username...">

			<label for="password">Password</label>
			<input type="password" name="password" id="password" data-clear-btn="true" placeholder="Enter password...">
				
		    <input type='hidden' name='command' value='login'> 
		    <input type='submit' data-icon='check' value ='Login'>
			<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		    %>
        </form>
        <form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='canclelogin'> 
	        <input type='submit' data-theme="e" data-icon='back' value='Cancle'>
        </form>

	</div>
</div>
</div>
</body>
</html>