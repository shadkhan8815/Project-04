<%@page import="in.co.ryas.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.ryas.proj4.controller.UserRegistrationCtl"%>
<%@page import="in.co.ryas.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

                   <jsp:useBean id="bean" class="in.co.ryas.proj4.bean.UserBean"
		scope="request"></jsp:useBean>
       
                   <%@ include file="Header.jsp" %>
       
         <form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">
         
           <center>
                   <table>
                           <tr>
                                <th>
                                  First Name<span style="color : blue">*</span> :
                                </th>
                                <td>
                                <input type="text" name="firstName"
                                  placeholder="Enter First Name" size="26" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
                                </td>
                           </tr>  
                             
                             
                           <tr>
                                <th style="padding : 3px"></th>
                                <td></td>
                          </tr>      
                             
                           
                           <tr>
                                <th>
                                  Last Name<span style="color : blue">*</span>
                                </th>
                                <td>
                                <input type="text" name="lastName"
                                   placeholder="Enter Last Name" size="26" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                           
                           <tr>
                                <th>
                                  Login Id<span style="color : blue">*</span>
                                </th>
                                <td>
                                <input type="text" name="login"
                                   placeholder="Enter Valid E-mail" size="26" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                           
                           <tr>
                                <th>
                                  Date Of Birth<span style="color : blue">*</span>
                                </th>
                                <td>
                                <input type="date" name="dob" style="width: 98%" 
                                   placeholder="Enter Dob " size="26" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                           
                           <tr>
                                <th>
                                  Gender<span style="color : blue">*</span>
                                </th>
                                <td>
                                
                                     <% 
                                         HashMap map = new HashMap();
                                     
                                                 map.put("Male" , "Male");
                                                 map.put("Female" , "Female");
                                                 
                     				    String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
                                      %>
                                       <%=htmlList%>
                                 <font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                           
                           <tr>
                                <th>
                                  Mobile No<span style="color : blue">*</span>
                                </th>
                                <td>
                                <input type="text" name="mobileNo"
                                   placeholder="Enter Mobile_No " size="26" maxlength="10" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                           
                           <tr>
                                <th>
                                  Password<span style="color : blue">*</span>
                                </th>
                                <td>
                                <input type="text" name="password"
                                   placeholder="Enter Password " size="26" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                           
                           
                           <tr>
                                <th>
                                  Confirm Password<span style="color : blue">*</span>
                                </th>
                                <td>
                                <input type="text" name="confirmPassword"
                                   placeholder="Enter Confirm Password " size="26" value="">
                                <font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
                                </td>
                           </tr>
                           
                           
                           <tr>
                                 <th style="padding : 3px"></th>
                                 <td></td>
                           </tr>
                           
                           
                              
                           <tr>
                                 <th></th>
                                 <td colspan="2">&nbsp; &emsp; 
                                 <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SING_UP%>">
                                      &nbsp;
                                 <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_RESET%>">
                                 </td>
                          </tr>
                </table>
       </center> 
 </form>                                  
                               <%@ include file="Footer.jsp"%>                 
 </body>
</html>