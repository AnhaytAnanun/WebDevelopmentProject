<%-- 
    Document   : login
    Created on : Apr 17, 2019, 3:30:59 PM
    Author     : anhay
--%>

<%@page import="com.flycode.coolstore.utils.SessionUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    boolean isLoggedin = SessionUtils.isLoggedIn(session);
    boolean isAdmin = SessionUtils.isAdmin(session);
    
    if(!isLoggedin && !isAdmin) {
        response.sendRedirect(response.encodeRedirectURL("./"));
        
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="./styles/main.css" />
        <link rel="stylesheet" href="./styles/auth.css" />
        <title>Sign Up</title>
    </head>
    <body>
        <div class="header">
            <a href="./" class="logo">Auburn Real Estate</a>
            <div class="header-right">
                <a href="./index.jsp">Home</a>                
            </div>
        </div>

        <form class="auth" action="./api/edit" method="post">    
            <h2>Admin Add property</h2>

            <div class="container">
                <c:if test="${isLoginFailed}">
                    <label class="error"><c:out value="${errorMessage}" /></label>
                    <br />
                    <br />
                </c:if>
                
                <label for="uname"><b>Address</b></label>
                <input type="text" placeholder="Enter Address" name="address">

                <label for="uname"><b>City</b></label>
                <input type="text" placeholder="Enter City" name="city">
                
                <label for="uname"><b>State</b></label>
                <input type="text" placeholder="Enter State" name="state">                
                
                <label for="uname"><b>Zip</b></label>
                <input type="text" placeholder="Enter Zip" name="zip">
                
                <label for="uname"><b>Description</b></label>
                <input type="text" placeholder="Enter Description" name="description">
                
                <label for="uname"><b>Image</b></label>
                <input type="text" placeholder="Enter Image" name="image">
                
                <label for="uname"><b>Rent</b></label>
                <input type="text" placeholder="Enter Rent" name="rent">                
                
                <label for="uname"><b>Availability</b></label>
                <input type="text" placeholder="Enter Availability" name="availability">
                
                <label for="uname"><b>Bedrooms</b></label>
                <input type="text" placeholder="Enter Bedrooms" name="bedrooms">     
                
                <label for="uname"><b>Bathrooms Name</b></label>
                <input type="text" placeholder="Enter Bathrooms" name="bathrooms">                              
                <button type="submit">Add</button>
            </div>
        </form>
    </body>
</html>

<c:remove var="errorMessage" scope="session" />
<c:remove var="isLoginFailed" scope="session" />
<c:remove var="failedUsername" scope="session" />
<c:remove var="failedFirstName" scope="session" />
<c:remove var="failedLastName" scope="session" />
<c:remove var="failedPassword" scope="session" />
