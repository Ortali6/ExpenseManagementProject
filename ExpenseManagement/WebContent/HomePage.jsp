<%@ page language="java" import="il.ac.hit.model.*,il.ac.hit.controller.*,java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<!-- home page -->

<div id="homepage" data-role="page" data-theme="b">
	<header data-role="header" data-theme="a">
	<h1>Cost Item App</h1>
	<script type="text/javascript" src="JS/javascripts.js"></script>
	<div data-role="navbar" data-position="fixed">
	<ul>
		<li>
		<a href="#popupMenu" data-rel="popup" data-transition="pop" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-icon-bars ui-btn-icon-left ui-btn-a">Settings</a>
		</li>
		<li>
		<form action='ExpenseManagementController' method='post'>
				<input type='hidden' name='command' value='additem'>
				<input type='submit' data-icon="plus" value='Add'>		
	 	</form>
	 	</li>
	 	<li>
	 	<form action='ExpenseManagementController' method='post'>
			    <input type='hidden' name='command' value='graphs'>
				<input type='submit' data-icon="grid" value='Graphs'>
		 </form>
		 </li>
		</ul>

			<ul>
				<li>
				<form action='ExpenseManagementController' method='post'>
				  <input type='hidden' name='command' value='backmonth'>
				  <input type='submit' data-icon="arrow-l" class="ui-btn-left" value='Back'>
				 </form>
		 		</li>
				<li><div id="currentmonth" align="center" style="font-size:73%"><%=session.getAttribute("currentmonthinwords")%><br><%=session.getAttribute("currentyear")%></div></li>
				<li>
				<form action='ExpenseManagementController' method='post'>
				  <input type='hidden' name='command' value='nextmonth'>
				  <input type='submit' data-icon="arrow-r" class="ui-btn-right" value='Next'>
				 </form>
				</li>
			</ul>
	 </div>
</header>

<div role="main" id="homepagemainrole" class="ui-content">
			<%
		    if(request.getAttribute("requestmessage") != null)
		    {
		    	out.write("<h6 style='color:red;text-align:left;'>"+(String)request.getAttribute("requestmessage")+"</h6>");
		    }
			double currBudg =  (double)session.getAttribute("currentbudget");
			double currExpens = (double)session.getAttribute("currenteexpenses");
			if(currBudg < currExpens)
			{
				%>
				<script>
				 var x = document.createElement("STYLE");
				 var t = document.createTextNode("#expensessum {color:red;}");
				 x.appendChild(t);
				 document.head.appendChild(x);
				</script>
				<%
			}
			else
			{
				%>
				<script>
				 var x = document.createElement("STYLE");
				 var t = document.createTextNode("#expensessum {color:white;}");
				 x.appendChild(t);
				 document.head.appendChild(x);
				</script>
				<%
			}
		    %>

	<div data-role="navbar">
		 <ul>
		<li><h4><a href="#" id="budget">Budget: <%= session.getAttribute("currentbudget") %></a></h4></li>
		<li><h4><a href="#" id="expensessum">Expenses: <%= session.getAttribute("currenteexpenses") %></a></h4></li>
		</ul>
	</div>
	
    	<h3>Welcome <% User user = (User)session.getAttribute("loginUser");
    											  out.print(user.getUserName()); %></h3>


<div data-role="popup" id="popupMenu" data-theme="a" data-history="false">
        <div data-role="collapsible" data-iconpos="right">
        <h4>Month To Go:</h4>
            <ul data-role="listview">
                <li>
				<form id="changeyearmonthform" action='ExpenseManagementController' method='post'>
				<input type='hidden' name='command' value='changeyearmonth'>
				<input type="month" name="monthtogo" id="monthtogo" value="" class="onlyint" data-clear-btn="true" placeholder="YYYY-MM">
				<input type='submit' data-icon="arrow-r" class="ui-btn-right" value='Go'>
				</form>
				</li>
				</ul>
        </div>
     	 <ul data-role="listview" data-inset="true" style="min-width:210px;">
     	 <li>
		<form id="changepasswordform" action='ExpenseManagementController' method='post'>
		<input type='hidden' name='command' value='changepassword'>
		 <input type='submit' value='Change password'>
		</form>
   		</li>
   		
   		<li>
		<form id="changeemailform" action='ExpenseManagementController' method='post'>
		<input type='hidden' name='command' value='changeemail'>
		<input type='submit' value='Change email'>
		</form>
		</li>
		
		<li>
		<form id="changebudgetform" action='ExpenseManagementController' method='post'>
		<input type='hidden' name='command' value='changebudget'>
		<input type='submit' value='Change budget'>
		</form>
		</li>
		
		<li>
		<form id="logoutform" action='ExpenseManagementController' method='post'>
		<input type='hidden' name='command' value='logout'>
		<input type='submit' value='Logout'>
		</form>
		</li>
    </ul>
</div>
	
	<div class="ui-content">
    	<ul data-role="listview" data-filter="true">
    	<%
			List<CostItem> userItems = (List<CostItem>)session.getAttribute("userItems");
    		
    		for(CostItem item : userItems) {
    			out.println("<li> <ul> <h2 style='text-align:left;'><b> Category: "+item.getCategory()+"</b></h2>"
    					+"<p><b> Description: "+item.getDescription()+"</b></p>"
    					+"<p><b> Date: "+item.getDate()+"</p></b>"
    					+"<p><b> Spending amount: "+item.getExpenditureAmount()+"</p></b>"
    					+"<div data-role='navbar' data-position='fixed'>"
    					+"<ul><form action='ExpenseManagementController' method='post'>"
    					+"<input type='hidden' name='command' value='deleteitem'>"
    					+"<input type='hidden' name='itemid' value='" +item.getCostItemID()+ "'>" 
    					+"<input type='submit' data-icon='minus' data-corners='false' value='Delete'>"	
    		 			+"</form>"
    		 			+"<form action='ExpenseManagementController' method='post'>"
    		 			+"<input type='hidden' name='command' value='edititem'>"
    					+"<input type='hidden' name='itemid' value='" +item.getCostItemID()+ "'>"
    					+"<input type='submit' data-icon='edit' style='font-size:73%' data-corners='false' value='Edit'>"
    					+"</form></ul></div></ul></li>");
    		}	
    	%>
    	</ul>
    	<br>
    </div>

</div>

</div>

</body>
</html>