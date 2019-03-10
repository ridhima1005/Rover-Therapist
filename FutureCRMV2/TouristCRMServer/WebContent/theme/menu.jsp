
<%@page import="com.helper.UserAccount"%>

<%
	boolean doesUserExists = false;
	UserAccount ua1 = null;

	if (request.getSession().getAttribute("USER_MODEL") != null) {
		doesUserExists = true;
		ua1 = (UserAccount) request.getSession().getAttribute(
				"USER_MODEL");
	}
%>

<div id="menu1">
	<ul class="navigation">


		<%
			if (doesUserExists) {
		%>
		<li class="current_page_item"><a
			href="<%=request.getContextPath()%>/pages/home.jsp">Home</a></li>
		<%
			}
		%>
		<%
			if (!doesUserExists) {
		%>
		<li class="current_page_item"><a
			href="<%=request.getContextPath()%>/pages/index.jsp">Home</a></li>
		<%
			}
		%>
		<!-- <li><a href="#">About</a></li> -->

		<%
			if (doesUserExists) {

				/* if(um!=null&&um.getAuthority().equalsIgnoreCase("1")){ */
		%>


		<!-- <li><a href="#">Admin</a> -->

		<div class="clear"></div>
		</li>
		<%
			}
		%>
		<%
			if (ua1 != null) {
		%>


		<li><a href="<%=request.getContextPath()%>/pages/adddetails.jsp">Add
				Details</a></li>
		<li><a href="<%=request.getContextPath()%>/pages/viewAreas.jsp">View
				Areas</a></li>
		<li><a href="<%=request.getContextPath()%>/pages/users.jsp">View
				Users</a></li>




		<%-- <%} %> --%>

		<li><a
			href="<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=logout">Logout</a></li>
		<li><a>&nbsp;</a></li>
		<%
			}
		%>

	</ul>
</div>

<div class="clear"></div>

<script type="text/javascript">
	// Executes the function when DOM will be loaded fully
	$(document).ready(function() {
		// hover property will help us set the events for mouse enter and mouse leave
		$('.navigation li').hover(
		// When mouse enters the .navigation element
		function() {
			//Fade in the navigation submenu
			$('ul', this).fadeIn(); // fadeIn will show the sub cat menu
		},
		// When mouse leaves the .navigation element
		function() {
			//Fade out the navigation submenu
			$('ul', this).fadeOut(); // fadeOut will hide the sub cat menu		
		});
	});
</script>