<script>
var width1 = $(document).width();
var height1 = $(document).height();
var widthstr = "width: " + width1 + "px; ";
var heightstr = "height: " + height1 + "px;";
var str = "#piechart_3d {overflow-x: scroll; overflow-y: hidden; " + widthstr + heightstr + "}";
var x1 = document.createElement("STYLE");
var t1 = document.createTextNode(str);
x1.appendChild(t1);
document.head.appendChild(x1);
</script>

<div id="piechart_3d"></div>
	 <div id="piechart">
	<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
	      google.charts.setOnLoadCallback(drawChart);
	      var theData = ${piejsonarr};
	      var currMonth = ${currentmonth};
	      var currYear = ${currentyear};
	      var properyNames = Object.getOwnPropertyNames(theData[0]);
	      function drawChart()
	      {
	    	  var pieChart = new google.visualization.DataTable();
	    	  pieChart.addColumn('string','Category');
	    	  pieChart.addColumn('number','spent');

	    	  for (var i = 0; i < properyNames.length; i++) 
	    	  {
	    		  pieChart.addRows([[properyNames[i], parseFloat(theData[0][properyNames[i]])]]);
	    	  }
				
	        var options = 
	        {
	          title: 'My Monthly Expenses - ' + currMonth + '/' + currYear,
	          is3D: true,
	          width: '900',
	          height: '500'
	        };

	        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));

	        chart.draw(pieChart, options);
	      }
</script>
</div>
