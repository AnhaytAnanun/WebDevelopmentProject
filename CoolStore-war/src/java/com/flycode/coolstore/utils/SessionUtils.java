/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flycode.coolstore.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhay
 */
public class SessionUtils {
    public static boolean isLoggedIn(HttpSession session) {        
        Integer userId = (Integer) session.getAttribute("userId");
        return userId != null && userId > 0;
    }
    
    public static boolean isAdmin(HttpSession session) {
        if (!isLoggedIn(session)) {
            return false;
        }
        
        Integer role = (Integer) session.getAttribute("role");
        return role == 1;
    }
    
    public static void loginFailed(HttpServletRequest request, HttpServletResponse response, String errorMessage, boolean isSignUp) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("isLoginFailed", true);
        session.setAttribute("failedUsername", request.getParameter("username"));
        session.setAttribute("failedPassword", request.getParameter("password"));
        session.setAttribute("failedFirstName", request.getParameter("firstName"));
        session.setAttribute("failedLastName", request.getParameter("lastName"));
        session.setAttribute("failedPhone", request.getParameter("phone"));
        session.setAttribute("failedEmail", request.getParameter("email"));
        response.sendRedirect(isSignUp ? "../signup.jsp" : "../login.jsp");
    }
}
