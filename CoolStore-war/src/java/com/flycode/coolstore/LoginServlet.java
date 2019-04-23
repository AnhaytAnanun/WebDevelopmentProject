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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhay
 */
public class LoginServlet extends HttpServlet {

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
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || username.length() == 0
                    || password == null || password.length() == 0) {
                
                SessionUtils.loginFailed(request, response, "You must type in both username and password!", false);
                
                return;
            }
            
            Connection connection = DBUtils.connect();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Ei4gDiB26n.users WHERE Ei4gDiB26n.users.username = ? AND Ei4gDiB26n.users.password = ? LIMIT 1;");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            
            results.beforeFirst();

            boolean valid = true;
            
            if (results.next()) {
                HttpSession session = request.getSession(false);
                session.setAttribute("userId", results.getInt("user_id"));
                session.setAttribute("username", results.getString("username"));
                session.setAttribute("firstName", results.getString("fname"));
                session.setAttribute("lastName", results.getString("lname"));
            } else {
                valid = false;
            }
            
            results.close();
            
            connection.close();
            
            if (!valid) {
                SessionUtils.loginFailed(request, response, "This username and password combination does not match our records!", false);
                
                return;
            }
            
            response.sendRedirect("/CoolStore-war/");
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicsServlet.class.getName()).log(Level.SEVERE, null, ex);
            SessionUtils.loginFailed(request, response, "Something went terribly wrong. Please, come back later!", false);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrowseServlet.class.getName()).log(Level.SEVERE, null, ex);
            SessionUtils.loginFailed(request, response, "Something went terribly wrong. Please, come back later!", false);
        }
    }
}
