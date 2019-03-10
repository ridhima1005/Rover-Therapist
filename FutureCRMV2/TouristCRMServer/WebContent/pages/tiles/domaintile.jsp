<%@page import="com.helper.DomainInfo"%>
<%@ taglib uri="/WEB-INF/displaytag-12.tld" prefix="display"%>
<display:table class="simple" name="sessionScope.RESULT"
	requestURI="/pages/domaintile.jsp" id="searchTableId" pagesize="20"
	style=" width:100%;" defaultsort="1" defaultorder="ascending"
	export="false" sort="list">
	<display:setProperty name="export.csv" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.xls" value="false" />
	<display:setProperty name="paging.banner.placement" value="bottom" />


	<display:column title="DomainId" sortable="true"
		property="iddomainInfo">
	</display:column>
	<display:column title="Description" sortable="true"
		property="description">
	</display:column>
	<display:column title="Location" sortable="true" property="location">
	</display:column>
	<display:column title="PinCode" sortable="true" property="pincode">
	</display:column>

	<display:column sortable="true" title="Delete " media="html">
		<a href="#"
			onclick="fnDeleteDetails('<%=((DomainInfo) (pageContext
							.getAttribute("searchTableId"))).getIddomainInfo()%>')">Delete</a>
	</display:column>

</display:table>




<script>
function fnDeleteDetails(id){
	$.ajax({
		  type: "POST",
		  url: "<%=request.getContextPath()%>
	/pages/tiles/ajax.jsp?method=deleteAdds&id="
									+ id,
							dataType : "text",
							data : dataString
						}).done(function(msg) {
					alert("Data Deleted");
				});
	}
</script>
