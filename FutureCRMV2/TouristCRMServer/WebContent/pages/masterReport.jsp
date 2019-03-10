<%-- <%@page import="com.database.ConnectionManager"%>
<%@page import="com.helper.PopulationModel"%>
<%@page import="com.helper.DBUtils"%>
<%@page import="com.helper.StringHelper"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../theme/include.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report</title>

<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
table.hovertable {
	font-family: verdana,arial,sans-serif;
	font-size:15px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.hovertable th {
	background-color:#c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.hovertable tr {
	background-color:#d4e3e5;
}
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
.boldtext{
font-weight: bold;
vertical-align:text-bottom;
color:#0B0B61;
}
.myinput class ="inputBox" {
	padding: 5px;
}
input class ="inputBox"{
	height: 17pt;
}

</style>
</head>
<body>
	<div id="wrapper">
		<%@include file="../theme/header.jsp"%>
		<!-- end #header -->
		<%@include file="../theme/menu.jsp"%>

		<div id="welcome">
			<h2 class="title">
				<a href="#">Welcome to <%=ServerConstants.APP_TITLE%></a>
			</h2>
			<div class="entry"></div>
		</div>
		<div id="two-columns">
			<div style="text-align: center;">
				<h2>Statewise Yearly Report</h2>
				<form name="form1" id="form1">
				<table border="0">
				<tr>
				<td class="boldtext" colspan="3">Year&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="year" name="year" title="Please select Year" multiple="multiple" size="4">
									<option value="">Select Year</option>
									<%
										Date date = new Date();
										for (int i = 2000; i <= (1900 + date.getYear() - 1); i++) {
									%>
									<option value="<%=i%>"><%=i%>
									</option>
									<%
										}
									%>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
						
				</td>
				<td class="boldtext" colspan="3">State&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;
				
				<select title="Select State"	type="text" id="state" name="state"  multiple="multiple" size="4" >
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="document.forms[0].submit();" class="boldtext" name="verify"
					   value="Search" style="text-align: center;">
				</td>
				</tr>			
				</table>
				<br/>
				<div id="reportId" style="text-align: left;">
				
<%
List list = null;
String dstate = StringHelper.nullObjectToStringEmpty(request.getParameter("state"));
int dyear=StringHelper.nullObjectToIntegerEmpty(request.getParameter("year"));
System.out.println("State and Year in masterReport.jsp is "+dstate+" and "+dyear);
if(dyear>0){
list = DBUtils.getBeanList(PopulationModel.class, ServerConstants.STATEYEARREPORT, new Object[] { dstate, dyear });
session.setAttribute("LIST2", list);
}
else{
list = DBUtils.getBeanList(PopulationModel.class, ServerConstants.STATEONLYREPORT, new Object[] { dstate });
System.out.println("The Control is in the Statewisereport and list size is " + list.size());
session.setAttribute("LIST2", list);	
}
%>


<%@ taglib uri="/WEB-INF/displaytag-12.tld" prefix="display" %>
<display:table name="sessionScope.LIST2" requestURI="/PopulationPrediction/pages/masterReport.jsp" class="hovertable" defaultorder="descending"  export= "true" pagesize="8" sort="list"   >

<display:setProperty name="export.csv" value="false"></display:setProperty>
<display:setProperty name="export.xml" value="false"></display:setProperty>
<display:setProperty name="export.csv.filename" value="YearWise.xls"></display:setProperty>

	<display:column  title="Year"  sortable="true" property="pyear"  ></display:column>
	<display:column  title="State"  sortable="true" property="statedesc"  ></display:column>
   	<display:column  title="Birth Rate"  sortable="true" property="birthrate" ></display:column>
	<display:column  title="Death Rate"  sortable="true" property="deathrate"  ></display:column>
	<display:column title="Migration Rate"  sortable="true"  property="migrate"  ></display:column>
	<display:column title="Population"  sortable="true"  property="population"  ></display:column>	
</display:table>

	
   
				
				</div>
				</form>
			</div>
			
		</div>
			
		<!-- end #page -->
	</div>

<script>

// For Loading the State in List Box
$(document).ready(function(){
	  $("#state").load("<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=getStates");	  
});

</script>


</body>
</html>
 --%>