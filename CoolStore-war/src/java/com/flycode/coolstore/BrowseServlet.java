/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flycode.coolstore;

import com.flycode.coolstore.utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class BrowseServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            
            if (userId == null) {
                userId = 0;
            }
            
            int topic = 0;
            
            try {
                topic = Integer.parseInt(request.getParameter("topic"));                
            } catch (NumberFormatException ex) {
                
            }
            
            StringBuilder query = new StringBuilder();
        
            query.append("SELECT * FROM Ei4gDiB26n.books ")
                    .append("LEFT JOIN Ei4gDiB26n.reservations ON Ei4gDiB26n.reservations.book_id = Ei4gDiB26n.books.book_id ")
                    .append("LEFT JOIN Ei4gDiB26n.authors ON Ei4gDiB26n.books.author_id = Ei4gDiB26n.authors.author_id ")
                    .append("LEFT JOIN Ei4gDiB26n.topics ON Ei4gDiB26n.books.topic_id = Ei4gDiB26n.topics.topic_id ");
                        
            if (topic != 0) {
                query.append("WHERE topics.topic_id = ? ");
            }

            query.append("ORDER BY Ei4gDiB26n.books.book_name");
            
            Connection connection = DBUtils.connect();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            
            if (topic != 0) {
                statement.setInt(1, topic);
            }
            
            ResultSet results = statement.executeQuery();
            
            results.beforeFirst();
            
            response.setContentType("text/xml;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.append("<books>");
            
            while (results.next()) {
                writer.append("<book>");
                writer.append("<book_id>").append(String.valueOf(results.getInt("book_id"))).append("</book_id>");
                writer.append("<book_name>").append(String.valueOf(results.getString("book_name"))).append("</book_name>");
                writer.append("<author_name>").append(String.valueOf(results.getString("author_name"))).append("</author_name>");
                writer.append("<topic_name>").append(String.valueOf(results.getString("topic_name"))).append("</topic_name>");
                writer.append("<is_available>").append(String.valueOf(results.getInt("is_available"))).append("</is_available>");
                
                if (userId.equals(results.getInt("user_id"))) {
                    writer.append("<already_reserved>1</already_reserved>");
                } else {
                    writer.append("<already_reserved>0</already_reserved>");                    
                }
                
                writer.append("</book>");
            }
            
            writer.append("</books>");
            
            results.close();
            
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TopicsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrowseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
