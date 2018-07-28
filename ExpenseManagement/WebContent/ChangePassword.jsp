<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
</head>
<body>
<div id="changepoasswordpage" data-role="page" data-theme="b">
	<header data-role="header">
	 <h1>Change Password</h1>
	 </header>
	 <div role="main" class="ui-content">
	 <div data-role="content" data-position="fixed">
	 
	 <form action='ExpenseManagementController' method='post'>
	 
		<label for="oldpassword">Old Password</label>
		<input type="password" name="oldpassword" id="oldpassword" placeholder="Enter old password..."/>
		
		<label for="newpassword">New Password</label>
		<input type="password" name="newpassword" id="newpassword" placeholder="Enter new password..."/>
		
		<label for="renewpassword">Confirm Password</label>
		<input type="password" name="renewpassword" id="renewpassword" placeholder="Re-Enter new password..."/>
		
		<input type='hidden' name='command' value='okchangepassword'>
		
		<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		%>
		
		<input type="submit" data-icon="check" value="save">
		</form>
		
		<form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='canclecnagepassword'> 
	        <input type='submit' data-theme="e" data-icon='back' value='Cancle'>
        </form>
        
        	
        </div>
</div>	 
</div>
</body>
</html>