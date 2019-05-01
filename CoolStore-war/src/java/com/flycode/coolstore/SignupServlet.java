/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flycode.coolstore;

import com.flycode.coolstore.utils.DBUtils;
import com.flycode.coolstore.utils.SessionUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhay
 */
public class SignupServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StringBuilder errorString = new StringBuilder();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            boolean valid = true;

            if (username == null || username.length() < 4) {
                valid = false;
                errorString.append("Username must be at least 4 signs long. ");
            }

            if (password == null || password.length() < 4) {
                valid = false;
                errorString.append("Password must be at least 4 signs long. ");
            }            

            if (!password.equals(confirmPassword)) {
                valid = false;
                errorString.append("Password and Confrim Password do not match. ");
            }

            if (firstName == null || firstName.length() == 0) {
                valid = false;
                errorString.append("First Name cannot be empty. ");
            }

            if (lastName == null || lastName.length() == 0) {
                valid = false;
                errorString.append("Last Name cannot be empty. ");
            }

            if (email == null || !email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                valid = false;
                errorString.append("Email is invalid. ");
            }

            if (phone == null || !phone.matches("^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")) {
                valid = false;
                errorString.append("Phone number is invalid. ");
            }
            
            if (!valid) {
                SessionUtils.loginFailed(request, response, errorString.toString(), true);
                
                return;
            }
            
            Connection connection = DBUtils.connect();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Ei4gDiB26n.Users (Ei4gDiB26n.Users.firstName, Ei4gDiB26n.Users.lastName, Ei4gDiB26n.Users.username, Ei4gDiB26n.Users.password, Ei4gDiB26n.Users.phone, Ei4gDiB26n.Users.email, Ei4gDiB26n.Users.role) VALUES (?, ?, ?, ?, ?, ?, 0)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, phone);
            statement.setString(6, email);
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                SessionUtils.loginFailed(request, response, "Cannot create this user. Please, try another username!", true);
                
                return;
            }
            
            ResultSet results = statement.getGeneratedKeys();
            
            results.beforeFirst();
            
            if (results.next()) {
                HttpSession session = request.getSession(false);
                session.setAttribute("userId", results.getInt(1));
                session.setAttribute("username", username);
                session.setAttribute("firstName", firstName);
                session.setAttribute("lastName", lastName);
                session.setAttribute("phone", lastName);
                session.setAttribute("email", lastName);
            } else {
                valid = false;
            }
            
            results.close();
            
            connection.close();
            
            if (!valid) {
                SessionUtils.loginFailed(request, response, "Cannot create this user. Please, try another username!", true);
                
                return;
            }
            
            response.sendRedirect("/CoolStore-war/");
            
        } catch (SQLException ex) {
            if (ex instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
                SessionUtils.loginFailed(request, response, "This username already exists. Please, try another one!", true);
                
                return;
            }
            
            Logger.getLogger(TopicsServlet.class.getName()).log(Level.SEVERE, null, ex);
            SessionUtils.loginFailed(request, response, "Something went terribly wrong. Please, come back later!", true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrowseServlet.class.getName()).log(Level.SEVERE, null, ex);
            SessionUtils.loginFailed(request, response, "Something went terribly wrong. Please, come back later!", true);
        }
    }
}
