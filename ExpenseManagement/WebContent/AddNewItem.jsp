<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body>
<!-- add new item page -->
<div id="addnewitempage" data-role="page" data-theme="b">
	<header data-role="header">
	 <h1>Add new item</h1>
	 </header>
	 <div role="main" class="ui-content">
	 <script type="text/javascript" src="JS/javascripts.js"></script>
 	<div data-role="content" data-position="fixed"> 		
 		<form action='ExpenseManagementController' method='post'>
		<label>Category:</label>
		<input type="text" name="category" id="category" data-clear-btn="true" placeholder="Enter Category...">
 	
 		<label>Description:</label>
		<input type="text" name="description" id="description" data-clear-btn="true" placeholder="Enter Description...">
 		
		<label>Expenditure Amount:</label>
		<input type="text" name="expenditureamount" id="expenditureamount" data-clear-btn="true" class="onlydouble" placeholder="Enter Expenditure Amount...">
				
		<label for="date">Date</label>
		<input type="date" name="date" id="date" data-clearbtn="true" placeholder="YYYY-MM-DD">	
		
		<input type='hidden' name='command' value='okadditem'> 
		
		<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		%>
		
		<input type='submit' data-icon='check' value='ok'>
		</form>
		
		<form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='cancleadditem'> 
	        <input type='submit' data-theme="e" data-icon='back' value='Cancle'>
        </form>	
	</div>
</div>
</div>
</body>
</html>