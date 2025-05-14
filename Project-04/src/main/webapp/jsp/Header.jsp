<%@page import="in.co.ryas.proj4.bean.RoleBean"%>
<%@page import="in.co.ryas.proj4.controller.ORSView"%>
<%@page import="in.co.ryas.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% 
   UserBean userbean = (UserBean)session.getAttribute("user");
   
   boolean userloggedin = userbean != null ;
   
   String welcomeMsg = "WELCOME ; " ;
   
   if(userloggedin){
	               String role = (String) session.getAttribute("role");
	               
	               welcomeMsg += userbean.getFirstName() + "(" + role + ")";
	               
   } else {
	      welcomeMsg += "GUEST";
   }
       %>
   
   <table>
           <tr>
                <th> </th>  
                   <td width="90%"><a href="<%=ORSView.WELCOME_CTL%>">WELCOME</b></a> ||
				<%
				if (userloggedin) {
			%> 
			<a href="LoginCtl?operation=logout">LOGOUT</b></a> <%
 	} else {
 %> 
 <a href="<%=ORSView.LOGIN_CTL%>">LOGIN</b></a> <%
 	}
 %>
 </td>
			<td rowspan="2">
				<h1 align="right">
					<img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.jpg" 
					width="175" height="50">
				</h1>
			</td>
		</tr>

		<tr>
			<th></th>
			<td>
				<h3><%=welcomeMsg%></h3>
			</td>
		</tr>

		<%
			if (userloggedin) {
		%>

		<tr>
			<th></th>
			<td colspan="2"><font style="font-size: 18px"> <%
 	if (userbean.getRoleId() == RoleBean.ADMIN) {
 %> <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | <a
					href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | <a
					href="<%=ORSView.USER_CTL%>">Add User</b></a> | <a
					href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | <a
					href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | <a
					href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | <a
					href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | <a
					href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> | <a
					href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
					href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | <a
					href="<%=ORSView.COURSE_CTL%>">Add Course</b></a> | <a
					href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a> | <a
					href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a> | <a
					href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> | <a
					href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a> | <a
					href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a> | <a
					href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</b></a> | <a
					href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a> | <a
					target="blank" href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> <%
 	}
 %>

			</font></td>
		</tr>
		
		<%
			}
		%>
		
	</table>
	<hr>

</body>
</html>