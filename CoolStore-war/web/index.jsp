<%-- 
    Document   : index
    Created on : Apr 10, 2019, 8:33:53 PM
    Author     : anhay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.flycode.coolstore.utils.SessionUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    session.setAttribute("isLoggedIn", SessionUtils.isLoggedIn(session));
    session.setAttribute("isAdmin", SessionUtils.isAdmin(session));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="./styles/main.css" />
        <link rel="stylesheet" href="./styles/browse.css" />
        <script src="./js/index.js"></script>
        <title>Library Home</title>
    </head>
    <body>
        <div class="header">
            <a href="./" class="logo">Awesome Library</a>
            <div class="header-right">
                
                <c:choose>
                    <c:when test="${isAdmin}">
                        <a href="./profile.jsp">${firstName} ${lastName}</a>
                        <a href="./edit.jsp">Add Property</a>
                        <a href="./api/logout">Logout</a>
                    </c:when>
                    <c:when test="${isLoggedIn}">
                        <a href="./profile.jsp">${firstName} ${lastName}</a>
                        <a href="./api/logout">Logout</a>
                    </c:when>
                    <c:otherwise>
                        <a href="./login.jsp">Login</a>
                        <a href="./signup.jsp">Sign Up</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div style="padding-left:20px" class="browse">
            <select id="topics">
                <option value="0">All</option>
            </select>
            <button type="submit" onclick="onBrowseBooks()">Select</button>
        </div>
        
        
        <div style="overflow-x:auto;">
            <table id="books" class="responsive">
              <tr>
                <th>Book Title</th>
                <th>Author</th>
                <th>Topic</th>
                <th>Action</th>
              </tr>
            </table>
        </div>
    </body>
</html>
