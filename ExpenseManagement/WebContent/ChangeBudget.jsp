<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<!-- change budget page -->
<div id="budgetsettingspage" data-role="page" data-theme="b">
	<header data-role="header">
	 <h1>Change budget</h1>
	 </header>
	 <div role="main" class="ui-content">
	 <script type="text/javascript" src="JS/javascripts.js"></script>
 	<div data-role="content" data-position="fixed">
		<form action='ExpenseManagementController' method='post'>
			<label for="budget" class="ui-btn-text label center">New budget</label>
			<input type="text" name="newbudget" id="newbudget" class="onlydouble" placeholder="Enter new budget..."/>
			<input type='hidden' name='command' value='okchangebudget'>
			
			<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h3 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h3>");
		    }
		    %>
			
			<input type='submit' data-icon='check' value='Save'>
		</form>
		
		<form action='ExpenseManagementController' method='post'>
			<input type='hidden' name='command' value='canclechangebudget'>
			<input type='submit' data-theme="e" data-icon='back' value='Cancle'>
		</form>
		
	</div>
</div>
</div>
</body>
</html>