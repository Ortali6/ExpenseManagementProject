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
	 <h1>Edit item</h1>
	 </header>
	 <div role="main" class="ui-content">
	 <script type="text/javascript" src="JS/javascripts.js"></script>
 	<div data-role="content" data-position="fixed">

 		<form action='ExpenseManagementController' method='post'>
		<label>Category:</label>
		<input type="text" name="category" id="category" data-clear-btn="true" value="<%=session.getAttribute("itemcategory")%>" placeholder="Enter Category...">
 	
 		<label>Description:</label>
		<input type="text" name="description" id="description" data-clear-btn="true" value="<%=session.getAttribute("itemdescription")%>" placeholder="Enter description...">
 		
		<label>Expenditure Amount:</label>
		<input type="text" name="expenditureamount" id="expenditureamount" data-clear-btn="true" class="onlydouble" value="<%=session.getAttribute("itemprice")%>" placeholder="Enter Expenditure Amount...">
				
		<label for="date">Date</label>
		<input type="date" name="date" id="date" data-clear-btn="true" value="<%=session.getAttribute("itemdate")%>" placeholder="yyyy-mm-dd">
		
		<input type='hidden' name='command' value='okedititem'> 
		<input type='submit' data-icon='check' value ='ok'>
		<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		%>
		</form>
		<form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='cancleedititem'> 
	        <input type='submit' data-theme="e" data-icon='back' value ='Cancle'>
        </form>	
	</div>
</div>
</div>
</body>
</html>