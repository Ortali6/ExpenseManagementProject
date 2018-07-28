<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
</head>
<body>
<!-- change email page -->
<div id="changeemailpage" data-role="page" data-theme="b">
	<header data-role="header">
	 <h1>Change Email</h1>
	 </header>
	 <div role="main" class="ui-content">
	 <div data-role="content" data-position="fixed">
	 
	 <form action='ExpenseManagementController' method='post'>
		<label for="oldemail">Old Email</label>
		<input type="email" name="oldemail" id="oldemail" placeholder="Enter old email..."/>
		
		<label for="newemail">New Email</label>
		<input type="email" name="newemail" id="newemail" placeholder="Enter new email..."/>
		
		<label for="renewemail">Confirm Email</label>
		<input type="email" name="renewemail" id="renewemail" placeholder="Re-Enter new email..."/>
		
		<input type='hidden' name='command' value='okchangeemail'>
		
		<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		%>
		
		<input type="submit" name="save" data-icon="check" value="Save">
		</form>
		
		<form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='canclechangeemail'> 
	        <input type='submit' data-theme="e" data-icon='back' value ='Cancle'>
        </form>
        
        	
        </div>
</div>
</div>
</body>
</html>