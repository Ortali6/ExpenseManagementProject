<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Error page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div data-role="page" data-theme="b">
	<div role="main" class="ui-content">
    	<h1 id="h1">Error has Occured!</h1> 
    	<form action="ExpenseManagementController" method="post">
        	<input type="hidden" name="command" value="okerrorpage">
            <input type="Submit" data-inline="true" data-icon='home' value="Ok">
        </form> 
    </div>  
</div>
</body>
</html>