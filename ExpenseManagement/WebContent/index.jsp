<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Expense Management App</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!--External Styles-->
	<link rel="stylesheet" href="//code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
	<link rel="stylesheet" href="//demos.jquerymobile.com/1.4.0/theme-classic/theme-classic.css" />
	<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="//code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	<script type="text/javascript" src="//www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript" src="JS/javascripts.js"></script>
	<link rel="stylesheet" type="text/css" href="CSS/style.css">
</head>
<body>
<!-- index page -->
<div id="indexpage" data-role="page" data-theme="b">
	<header data-role="header">
		<h4>Expense Management</h4>
	</header>
	<div role="main" class="ui-content">
    <div data-role="content" data-position="fixed">
        <h2>Expense Management App</h2>
        <h3>To follow your money</h3>
        <br>
        <p><b>Existing Users?</b></p>
        <form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='loginwindow'> 
	        <input type='submit' data-icon='user' value ='Login'>
        </form>
        <p><b>New User?</b></p>
        <form action='ExpenseManagementController' method='post'>
	        <input type='hidden' name='command' value='registerwindow'> 
	        <input type='submit' data-theme="e" data-icon='edit' value ='Register'>
        </form>
    </div>
    </div>
</div>
</body>
</html>