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
    
    if(isLoggedin) {
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
                <a href="./">Home</a>
                <a href="./login.jsp">Sign Up</a>
            </div>
        </div>

        <form class="auth" action="./api/signup" method="post">    
            <h2>Sign Up Form</h2>

            <div class="container">
                <c:if test="${isLoginFailed}">
                    <label class="error"><c:out value="${errorMessage}" /></label>
                    <br />
                    <br />
                </c:if>
                
                <label for="uname"><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="username" value="<c:out value="${failedUsername}" />">
                
                <label for="firstName"><b>First Name</b></label>
                <input type="text" placeholder="Enter Username" name="firstName" value="<c:out value="${failedFirstName}" />">
                
                <label for="lastName"><b>Last Name</b></label>
                <input type="text" placeholder="Enter Username" name="lastName" value="<c:out value="${failedLastName}" />">
                
                <label for="phone"><b>Phone</b></label>
                <input type="text" placeholder="Phone" name="phone" value="<c:out value="${failedPhone}" />">
                
                <label for="email"><b>Email</b></label>
                <input type="text" placeholder="Email" name="email" value="<c:out value="${failedEmail}" />">

                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="password" value="<c:out value="${failedPassword}" />">

                <label for="psw"><b>Confirm Password</b></label>
                <input type="password" placeholder="Enter Password" name="confirm_password">

                <button type="submit">Sign Up</button>
            </div>
        </form>
    </body>
</html>

<c:remove var="errorMessage" scope="session" />
<c:remove var="isLoginFailed" scope="session" />
<c:remove var="failedUsername" scope="session" />
<c:remove var="failedFirstName" scope="session" />
<c:remove var="failedLastName" scope="session" />
<c:remove var="failedPhone" scope="session" />
<c:remove var="failedEmail" scope="session" />
<c:remove var="failedPassword" scope="session" />
