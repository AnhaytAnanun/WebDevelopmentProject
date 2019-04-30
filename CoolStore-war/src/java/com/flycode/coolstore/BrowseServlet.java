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
            String zip = request.getParameter("zip");
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            
            int rentMinimum = getIntParameter(request, "rentMinimum");
            int rentMaximum = getIntParameter(request, "rentMaximum");
            int bedrooms = getIntParameter(request, "beds");
            int bathrooms = getIntParameter(request, "bathrooms");            
            
            boolean zipValid = zip != null && zip.matches("^[0-9]{5}(?:-[0-9]{4})?$");
            boolean stateValid = state != null;
            boolean cityValid = city != null;
            boolean rentValid = rentMinimum > 0 && rentMaximum > rentMinimum;
            boolean bedroomsValid = bedrooms > 0;
            boolean bathroomsValid = bathrooms > 0;
                        
            StringBuilder query = new StringBuilder();
        
            query.append("SELECT * FROM Ei4gDiB26n.Property ");
            
            if (SessionUtils.isAdmin(request.getSession(false))) {
                query.append("WHERE Ei4gDiB26n.Property.availability = 0 OR Ei4gDiB26n.Property.availability = 1 ");
            } else {
                query.append("WHERE Ei4gDiB26n.Property.availability = 1 ");                
            }
                        
            if (zipValid) {
                query.append("AND Ei4gDiB26n.Property.zip = ? ");
            }
            if (stateValid) {
                query.append("AND Ei4gDiB26n.Property.state = ? ");
            }            
            if (cityValid) {
                query.append("AND Ei4gDiB26n.Property.city = ? ");
            }            
            if (rentValid) {
                query.append("AND Ei4gDiB26n.Property.rent => ? AND Ei4gDiB26n.Property.rent <= ? ");
            }
            if (bedroomsValid) {
                query.append("AND Ei4gDiB26n.Property.bedrooms = ? ");
            }            
            if (bathroomsValid) {
                query.append("AND Ei4gDiB26n.Property.bathrooms = ? ");
            }            

            query.append("ORDER BY Ei4gDiB26n.Property.rent ASC");
            
            Connection connection = DBUtils.connect();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            
            int indexCounter = 1;

            if (zipValid) {
                statement.setString(indexCounter, zip);
                indexCounter++;
            }
            if (stateValid) {
                statement.setString(indexCounter, state);
                indexCounter++;
            }
            if (cityValid) {
                statement.setString(indexCounter, city);
                indexCounter++;
            }
            if (rentValid) {
                statement.setInt(indexCounter, rentMinimum);
                indexCounter++;
                statement.setInt(indexCounter, rentMaximum);
                indexCounter++;
            }
            if (bedroomsValid) {
                statement.setInt(indexCounter, bedrooms);
                indexCounter++;
            }
            if (bathroomsValid) {
                statement.setInt(indexCounter, bathrooms);
                indexCounter++;
            }
            
            ResultSet results = statement.executeQuery();
            
            results.beforeFirst();
            
            response.setContentType("text/xml;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.append("<properties>");
            
            while (results.next()) {
                DBUtils.resultsToXml(writer, results);
            }
            
            writer.append("</properties>");
            
            results.close();
            
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AmenitiesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrowseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getIntParameter(HttpServletRequest request, String parameter) {
        if (request.getParameter(parameter) == null) {
            return -1;
        }
        
        try {
            return Integer.parseInt(request.getParameter(parameter));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
