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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anhay
 */
public class AmenitiesServlet extends HttpServlet {

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
            Connection connection = DBUtils.connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Ei4gDiB26n.Amenities;");
            
            results.beforeFirst();
            
            response.setContentType("text/xml;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.append("<amenities>");
            
            while (results.next()) {
                writer.append("<amenity>");
                writer.append("<amenity_id>").append(String.valueOf(results.getInt("amenityID"))).append("</amenity_id>");
                writer.append("<amenity_name>").append(String.valueOf(results.getString("amenityName"))).append("</amenity_name>");
                writer.append("</amenity>");
            }
            
            writer.append("</amenities>");
            
            results.close();
            
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AmenitiesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AmenitiesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
