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
public class EditorServlet extends HttpServlet {

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
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String description = request.getParameter("description");
            String image = request.getParameter("image");
            String rent = request.getParameter("rent");
            String availability = request.getParameter("availability");
            String bedrooms = request.getParameter("bedrooms");
            String bathrooms = request.getParameter("bathrooms");

            boolean valid = true;

            if (address == null) {
                valid = false;
                errorString.append("address must be at least 4 signs long. ");
            }

            if (city == null) {
                valid = false;
                errorString.append("city must be at least 4 signs long. ");
            }            

            if (state == null) {
                valid = false;
                errorString.append("state Name cannot be empty. ");
            }

            if (zip == null) {
                valid = false;
                errorString.append("zip cannot be empty. ");
            }
            if (description == null) {
                valid = false;
                errorString.append("description cannot be empty. ");
            }            
             if (image == null) {
                valid = false;
                errorString.append("image cannot be empty. ");
            }
            if (rent == null) {
                valid = false;
                errorString.append("rent cannot be empty. ");
            }
            if (availability == null) {
                valid = false;
                errorString.append("availability cannot be empty. ");
            } 
            if (bedrooms == null) {
                valid = false;
                errorString.append("bedrooms cannot be empty. ");
            } 
            if (bathrooms == null) {
                valid = false;
                errorString.append("bathroom cannot be empty. ");
            }            
           if (!valid) {
                SessionUtils.loginFailed(request, response, errorString.toString(), true);
                
                return;
            }
            
            Connection connection = DBUtils.connect();
            PreparedStatement statement = connection.prepareStatement(
                   "INSERT INTO Ei4gDiB26n.Property (Ei4gDiB26n.Property.address, Ei4gDiB26n.Property.city, Ei4gDiB26n.Property.state, Ei4gDiB26n.Property.zip, Ei4gDiB26n.Property.description, Ei4gDiB26n.Property.image, Ei4gDiB26n.Property.rent, Ei4gDiB26n.Property.availability, Ei4gDiB26n.Property.bedrooms, Ei4gDiB26n.Property.bathrooms) VALUES (?,?,?,?,?,?,?,?,?,?)");
                    // "INSERT INTO Ei4gDiB26n.Property (Ei4gDiB26n.Property.address, Ei4gDiB26n.Property.city, Ei4gDiB26n.Property.state, Ei4gDiB26n.Property.zip, Ei4gDiB26n.Property.description, Ei4gDiB26n.Property.image, Ei4gDiB26n.Property.rent, Ei4gDiB26n.Property.availability, Ei4gDiB26n.Property.bedrooms, Ei4gDiB26n.Property.bathrooms) VALUES (?,?,?,?,?,?,?,?,?,0)");
            statement.setString(1, address);
            statement.setString(2, city);
            statement.setString(3, state);
            statement.setString(4, zip);
            statement.setString(5, description);
            statement.setString(6, image);
            statement.setString(7, rent);
            statement.setString(8, availability);
            statement.setString(9, bedrooms);
            statement.setString(10, bathrooms); 
           // statement.executeUpdate();
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                SessionUtils.loginFailed(request, response, "Cannot create this user. Please, try another username!", true);
                
                return;
            }
            
            ResultSet results = statement.getGeneratedKeys();
            
            results.beforeFirst();
            
            if (results.next()) {
                HttpSession session = request.getSession(false);
                //session.setAttribute("userId", results.getInt(1));
                session.setAttribute("address", address);
                session.setAttribute("city", city);
                session.setAttribute("state", state);
                session.setAttribute("zip", zip);
                session.setAttribute("description", description);
                session.setAttribute("image", image);
                session.setAttribute("rent", rent);
                session.setAttribute("availability", availability);
                session.setAttribute("bedrooms", bedrooms);
                session.setAttribute("bathrooms", bathrooms);
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
            Logger.getLogger(BrowseServlet.class.getName()).log(Level.SEVERE, null, ex);
            
            if (ex instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
                SessionUtils.loginFailed(request, response, "This username already exists. Please, try another one!", true);
               
                return;
            }
            
           // Logger.getLogger(TopicsServlet.class.getName()).log(Level.SEVERE, null, ex);
            SessionUtils.loginFailed(request, response, "Something went terribly wrong. Please, come back later!", true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrowseServlet.class.getName()).log(Level.SEVERE, null, ex);
            SessionUtils.loginFailed(request, response, "Something went terribly wrong. Please, come back later2222!", true);
        }
    }
}
