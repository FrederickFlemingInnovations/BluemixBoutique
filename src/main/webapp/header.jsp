	<%@ page import="org.owasp.encoder.Encode" %>
	<%
	String userid=null;
	if(session.getAttribute("userid")!=null){
	userid = session.getAttribute("userid").toString();
	}
	
	 %>
	
	<table class="headerTable">
		<tr>
		
			<td><span class="pageTitle">BluemixBoutique</span></td> 
			<td style="text-align: right">

				<% if(userid==null){%>
				<a onclick="document.getElementById('loginBox').style.display='block';document.getElementById('fade').style.display='block'" href="javascript:void(0)">Login</a>
				<%} else {%>
				Welcome <%= Encode.forHtmlContent(session.getAttribute("userid").toString()) %>
				<!-- <a href="/cart">Cart</a> -->
				&nbsp;|&nbsp;
				<a href="LogoutServlet">Logout</a>
				<%} %>
			</td>
		</tr>
	</table>