<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="com.constants.ServerConstants"%>
<%@page import="com.util.WebConnectionManager"%>
<%@page import="com.helper.StringHelperNew"%>
<%@page import="com.helper.DBUtils"%>
<%@page import="com.helper.DomainInfo"%>
<%@ taglib uri="/WEB-INF/displaytag-12.tld" prefix="display"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../theme/include.jsp" />
<style type="text/css">
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 15px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table.hovertable th {
	background-color: #c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr {
	background-color: #d4e3e5;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@include file="../theme/header.jsp"%>
		<!-- end #header -->
		<%@include file="../theme/menu.jsp"%>

		<div id="two-columns" style="text-align: center;">
			<center>
			<h3>Advertisement Report</h3>
			</center>
			<br />
			<div id="col1" style="text-align: center;">
				<form id="commentForm" method="post" name="myform">
					<fieldset>
						<table style="width: 90%; text-align: left;">
							<tr style="font-family: verdana; font-size: 19">
								<td>Domain&nbsp<em>*</em></td>
								<td><select validate="required"
									title="Please Select Domain" id="domain" name="domain">

								</select></td>
							</tr>


							<tr>
								<td colspan=2 style="text-align: center;"><input
									type="submit" name="Fetch" value="Search"></td>
							</tr>


						</table>

					</fieldset>

					<fieldset>

						<script>
 	  		document.getElementById('domain').value='<%=request.getParameter("domain")%>';
 	  </script>

						<%
							if (request.getParameter("Fetch") != null) {
								List list = null;
								String domain = StringHelperNew.n2s(request
										.getParameter("domain"));

								list = DBUtils.getBeanList(DomainInfo.class,
										ServerConstants.SELECT_QUERY, new Object[]{domain});
								System.out
										.println("The Control is in the delete adds and list size is "
												+ list.size());
								session.setAttribute("LIST2", list);
						%>
						<div style="width: 100%;">
							<display:table class="simple" name="sessionScope.LIST2"
								requestURI="/pages/deleteAdds.jsp" id="searchTableId"
								pagesize="20" style=" width:100%;" defaultsort="1"
								defaultorder="ascending" export="false" sort="list">
								<display:setProperty name="export.csv" value="false" />
								<display:setProperty name="export.xml" value="false" />
								<display:setProperty name="export.xls" value="false" />
								<display:setProperty name="paging.banner.placement"
									value="bottom" />


								<display:column title="Name" sortable="true"
									property="description">
								</display:column>
								<display:column title="Description" sortable="true"
									property="review">
								</display:column>
								<display:column title="Location" sortable="true"
									property="location">
								</display:column>
								<display:column title="Owner" sortable="true"
									property="ratinguserName">
								</display:column>
								<display:column title="Phone Number" sortable="true"
									property="phoneNumber">
								</display:column>
								<display:column title="Rating" sortable="true" property="rating">
								</display:column>

								<display:column sortable="true" title="Delete " media="html">
									<a href="#"
										onclick="fnDeleteDetails('<%=((DomainInfo) (pageContext
								.getAttribute("searchTableId")))
								.getIddomainInfo()%>')">Delete</a>
								</display:column>

							</display:table>
						</div>

						<%
							}
						%>

					</fieldset>
				</form>
			</div>
			<div id="col2" style="float: right;">
				<img
					src="<%=request.getContextPath()%>/theme/images/map_location.jpg"
					alt="" width="120" height="120" class="image-style" />
			</div>
		</div>

		<!-- end #page -->
	</div>

	<script>
function fnDeleteDetails(id){
	if(confirm("Are you sure?")){
	$.ajax({
		  type: "POST",
		  url: "<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=deleteAdds&id="+id,
 			dataType: "text",
		  data:""
	}).done(function( msg ) {  
		alert("Data Deleted");
		window.location.reload();
	});	
	}
}
</script>




	<script>

  
  function fnFetchDetails() {
		 dataString = $("#commentForm").serialize();
			$.ajax({
				  type: "POST",
				  url: "<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=fetchDetails",
				  dataType: "text",
				  data:dataString
				  }).done(function(msg){
					$("#resultId").html(msg);				
					$('#commentForm')[0].reset();
			});

   
  }
  
  $(document).ready(function(){
	  $("#domain").load("<%=request.getContextPath()%>
		/pages/tiles/ajax.jsp?method=getDomain");

						});
	</script>

</body>
</html>
