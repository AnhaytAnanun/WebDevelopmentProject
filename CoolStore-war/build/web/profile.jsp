<%-- 
    Document   : reservations
    Created on : Apr 17, 2019, 7:11:03 PM
    Author     : anhay
--%>

<%@page import="com.flycode.coolstore.utils.SessionUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    boolean isLoggedin = SessionUtils.isLoggedIn(session);
    
    if(!isLoggedin) {
        session.setAttribute("errorMessage", "Please, login first to make reservations.");
        response.sendRedirect(response.encodeRedirectURL("./login.jsp"));
        
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="./styles/main.css" />
        <link rel="stylesheet" href="./styles/profile.css" />
        <script src="./js/profile.js"></script>
        <title>Reservations</title>
    </head>
    <body>
        <div class="header">
            <a href="./" class="logo">Awesome Library</a>
            <div class="header-right">
                <a href="./profile.jsp">${firstName} ${lastName}</a>
                <a href="./api/logout">Logout</a>
            </div>
        </div>
                    
        <div class="table_header">
            <c:if test="${hasMessage}">
                <label class="success"><c:out value="${message}" /></label>
                <br />
                <br />
            </c:if>
            <c:if test="${hasErrorMessage}">
                <label class="error"><c:out value="${errorMessage}" /></label>
                <br />
            </c:if>
            <label><b>Your Reservations</b></label>
        </div>
        
        <div style="overflow-x:auto;">
            <table id="reservations" class="responsive">
              <tr>
                <th>Book Title</th>
                <th>Author</th>
                <th>Topic</th>
              </tr>
            </table>
        </div>
    </body>
</html>

<c:remove var="hasMessage" scope="session" />
<c:remove var="message" scope="session" />
<c:remove var="hasErrorMessage" scope="session" />
<c:remove var="errorMessage" scope="session" />
