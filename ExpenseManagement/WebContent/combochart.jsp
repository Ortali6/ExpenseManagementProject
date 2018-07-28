<script>
var width1 = $(document).width();
var height1 = $(document).height();
var widthstr = "width: " + width1 + "px; ";
var heightstr = "height: " + height1 + "px;";
var str = "#combochart_3d {overflow-x: scroll; overflow-y: hidden; " + widthstr + heightstr + "}";
var x1 = document.createElement("STYLE");
var t1 = document.createTextNode(str);
x1.appendChild(t1);
document.head.appendChild(x1);
</script>

<div id="combochart_3d"></div>
	 <div id="combochart">
 	<script type="text/javascript">
 	google.charts.load('current', {'packages':['corechart']});
	      google.charts.setOnLoadCallback(drawChart);
	      var theData = ${combrojsonarr};
	      var currYear = ${currentyear};
	      var properyNames = Object.getOwnPropertyNames(theData[0]);
	      
	      function drawChart()
	      {
	    	  var comboChartTable = new google.visualization.DataTable();
	    	  comboChartTable.addColumn('string','Month');
	    	  
	    	  for(var i = 0; i < properyNames.length; i++)
	    	  {
	    		  comboChartTable.addColumn('number', properyNames[i]);
	    	  }

	    	  for (var j = 0; j < 9; j++)
	    	  {
	    		  var dateStr = '0' + (j+1);
	    		  var currDataArr = [dateStr];
	    		  for(var r = 0; r < properyNames.length; r++)
    			  {
	    			  currDataArr.push(theData[j][properyNames[r]]);
    			  }  			  			  
	    		  comboChartTable.addRows([currDataArr]);
	    		  
	    		  
	    	  }
	    	  for (var k = 9; k < 12; k++)
	    	  {
	    		  var dateStr = '' + (k+1);    		  
	    		  var currDataArr = [dateStr];
	    		  for(var t = 0; t < properyNames.length; t++)
    			  {
	    			  currDataArr.push(theData[k][properyNames[t]]);
    			  }  			  			  
	    		  comboChartTable.addRows([currDataArr]);
	    	  }
				
	        var options = 
	        {
	          title: 'My Yearly Expenses - ' + currYear,
	          is3D: true,
	          vAxis: {title: 'Spend'},
	          hAxis: {title: 'Month'},
	          seriesType: 'bars',
	          series: {5: {type: 'line'}},
	          width: '900',
	          height: '500'
	        };

	        var chart = new google.visualization.ComboChart(document.getElementById('combochart_3d'));

	        chart.draw(comboChartTable, options);
	      }
    </script>
    </div>