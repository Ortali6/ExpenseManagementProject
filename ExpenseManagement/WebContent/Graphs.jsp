<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Graphs</title>
</head>
<body>
<div id="graphs" data-role="page" data-theme="b">
	<header data-role="header" data-theme="a">
	<h1>Graphs</h1>
	</header>
	<div role="main" class="ui-content">
	<script type="text/javascript" src="JS/javascripts.js"></script>
<div data-role="navbar" data-position="fixed" >
	<ul>

		<li><form action='ExpenseManagementController' method='post'>        
		    <input type='hidden' name='command' value='pieChart'> 
		    <input type='submit' data-icon='grid' value ='Pie chart'>
        </form>
	 	</li>
	 	<li><form action='ExpenseManagementController' method='post'>        
		    <input type='hidden' name='command' value='comboChart'> 
		    <input type='submit' data-icon='grid' value ='Combo chart'>
        </form>
		 </li>
	</ul>
</div>
<div id="graphcontent" data-role="content" data-position="fixed">

<%
 if(request.getAttribute("graphtype")!=null)
 {
   String graphType = (String)request.getAttribute("graphtype");%>
	<script>$('#graphcontent').remove();</script>
	<jsp:include page='<%=graphType+".jsp"%>'/>
<%
 }
%>
</div>

<div data-role="footer" data-position="fixed">
			<form action='ExpenseManagementController' method='post'>
			<input type='hidden' name='command' value='backfromgraphs'>
			<input type='submit' data-icon="back" data-corners="false" value='Back'>		 
		 	</form>
</div>
</div>
</div>
</body>
</html>